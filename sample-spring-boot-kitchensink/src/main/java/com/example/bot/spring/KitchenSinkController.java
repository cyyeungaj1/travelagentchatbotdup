/*
 * Copyright 2016 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.example.bot.spring;

// import org.springframework.scheduling;


import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import com.linecorp.bot.model.profile.UserProfileResponse;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;


import com.google.common.io.ByteStreams;

import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.MessageContentResponse;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.BeaconEvent;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.JoinEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.UnfollowEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.ImagemapMessage;
import com.linecorp.bot.model.message.LocationMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.imagemap.ImagemapArea;
import com.linecorp.bot.model.message.imagemap.ImagemapBaseSize;
import com.linecorp.bot.model.message.imagemap.MessageImagemapAction;
import com.linecorp.bot.model.message.imagemap.URIImagemapAction;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.message.template.ConfirmTemplate;
import com.linecorp.bot.model.response.BotApiResponse;


import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;


import com.example.bot.spring.dbmanager.JDBCLineUserManager;
import com.example.bot.spring.model.User;
import com.example.bot.spring.model.UserFactory;
import com.example.bot.spring.scheduler.ScheduledAnnouncementTask;

import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import retrofit2.Response;

import java.net.URI;

@Slf4j
@LineMessageHandler
public class KitchenSinkController {

	@Autowired
	private ChatBotController cb;
	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	@Autowired
	private LineMessagingClient lineMessagingClient;


	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
		log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		log.info("This is your entry point:");
		log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		TextMessageContent message = event.getMessage();
		handleTextContent(event.getReplyToken(), event, message);
	}

	@EventMapping
	public void handleStickerMessageEvent(MessageEvent<StickerMessageContent> event) {
		handleSticker(event.getReplyToken(), event.getMessage());
	}

	@EventMapping
	public void handleLocationMessageEvent(MessageEvent<LocationMessageContent> event) {
		LocationMessageContent locationMessage = event.getMessage();
		reply(event.getReplyToken(), new LocationMessage(locationMessage.getTitle(), locationMessage.getAddress(),
				locationMessage.getLatitude(), locationMessage.getLongitude()));
	}

	@EventMapping
	public void handleImageMessageEvent(MessageEvent<ImageMessageContent> event) throws IOException {
		final MessageContentResponse response;
		String replyToken = event.getReplyToken();
		String messageId = event.getMessage().getId();
		try {
			response = lineMessagingClient.getMessageContent(messageId).get();
		} catch (InterruptedException | ExecutionException e) {
			reply(replyToken, new TextMessage("Cannot get image: " + e.getMessage()));
			throw new RuntimeException(e);
		}
		DownloadedContent jpg = saveContent("jpg", response);
		reply(((MessageEvent) event).getReplyToken(), new ImageMessage(jpg.getUri(), jpg.getUri()));

	}

	@EventMapping
	public void handleAudioMessageEvent(MessageEvent<AudioMessageContent> event) throws IOException {
		final MessageContentResponse response;
		String replyToken = event.getReplyToken();
		String messageId = event.getMessage().getId();
		try {
			response = lineMessagingClient.getMessageContent(messageId).get();
		} catch (InterruptedException | ExecutionException e) {
			reply(replyToken, new TextMessage("Cannot get image: " + e.getMessage()));
			throw new RuntimeException(e);
		}
		DownloadedContent mp4 = saveContent("mp4", response);
		reply(event.getReplyToken(), new AudioMessage(mp4.getUri(), 100));
	}

	@EventMapping
	public void handleUnfollowEvent(UnfollowEvent event) {
		log.info("unfollowed this bot: {}", event);
		JDBCLineUserManager mLine = new JDBCLineUserManager();
		String userId = event.getSource().getUserId();
		User user = mLine.findUserByLineId(userId);
		mLine.deleteUser(user);
		cb.removeChatRoom(userId);
	}

	@EventMapping
	public void handleFollowEvent(FollowEvent event) {
		String replyToken = event.getReplyToken();
		this.replyText(replyToken, "Got followed event");
		JDBCLineUserManager mLine = new JDBCLineUserManager();
		String userId = event.getSource().getUserId();
		UserFactory uf = new UserFactory();
		uf.setLineId(userId);
		User user = mLine.createUser(uf.getUser());
		log.info("CreateUser::ID: " + user.getId());
		log.info("CreateUser::LineId: " + user.getLineId());



		if(threadPoolTaskScheduler == null)
			log.info("threadPoolTaskScheduler in controller is null");
		else{
			log.info("threadPoolTaskScheduler in controller is not null");
			scheduleTest(userId);
		}
	}

	@EventMapping
	public void handleJoinEvent(JoinEvent event) {
		String replyToken = event.getReplyToken();
		this.replyText(replyToken, "Joined " + event.getSource());
	}

	@EventMapping
	public void handlePostbackEvent(PostbackEvent event) {
		String replyToken = event.getReplyToken();
		this.replyText(replyToken, "Got postback " + event.getPostbackContent().getData());
	}

	@EventMapping
	public void handleBeaconEvent(BeaconEvent event) {
		String replyToken = event.getReplyToken();
		this.replyText(replyToken, "Got beacon message " + event.getBeacon().getHwid());
	}

	@EventMapping
	public void handleOtherEvent(Event event) {
		log.info("Received message(Ignored): {}", event);
	}

	private void reply(@NonNull String replyToken, @NonNull Message message) {
		reply(replyToken, Collections.singletonList(message));
	}

	private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
		try {
			BotApiResponse apiResponse = lineMessagingClient.replyMessage(new ReplyMessage(replyToken, messages)).get();
			log.info("Sent messages: {}", apiResponse);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	private void replyText(@NonNull String replyToken, @NonNull String message) {
		if (replyToken.isEmpty()) {
			throw new IllegalArgumentException("replyToken must not be empty");
		}
		if (message.length() > 1000) {
			message = message.substring(0, 1000 - 2) + "..";
		}
		this.reply(replyToken, new TextMessage(message));
	}


	private void handleSticker(String replyToken, StickerMessageContent content) {
		reply(replyToken, new StickerMessage(content.getPackageId(), content.getStickerId()));
	}
	// private ChatBotController cb = new ChatBotController();
	private void handleTextContent(String replyToken, Event event, TextMessageContent content)
            throws Exception {
        // String text = content.getText();
				// String reply =
				cb.processInput(replyToken, event.getSource().getUserId(), content);
				// this.replyText(replyToken, reply);
        // log.info("Got text message from {}: {}", replyToken, text);
        // switch (text) {
				// 		case "announce":{
				// 			ChatBotController cb = new ChatBotController();
				// 			break;
				// 		}
        //    case "profile": {
        //         String userId = event.getSource().getUserId();
        //         if (userId != null) {
        //             lineMessagingClient
        //                     .getProfile(userId)
        //                     .whenComplete(new ProfileGetter (this, replyToken));
        //         } else {
        //             this.replyText(replyToken, "Bot can't use profile API without user ID");
        //         }
        //         break;
        //     }
        //     case "confirm": {
        //         ConfirmTemplate confirmTemplate = new ConfirmTemplate(
        //                 "Do it?",
        //                 new MessageAction("Yes", "Yes!"),
        //                 new MessageAction("No", "No!")
        //         );
        //         TemplateMessage templateMessage = new TemplateMessage("Confirm alt text", confirmTemplate);
        //         this.reply(replyToken, templateMessage);
        //         break;
        //     }
        //     case "carousel": {
        //         String imageUrl = createUri("/static/buttons/1040.jpg");
        //         CarouselTemplate carouselTemplate = new CarouselTemplate(
        //                 Arrays.asList(
        //                         new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
        //                                 new URIAction("Go to line.me",
        //                                               "https://line.me"),
        //                                 new PostbackAction("Say hello1",
        //                                                    "hello ã�“ã‚“ã�«ã�¡ã�¯")
        //                         )),
        //                         new CarouselColumn(imageUrl, "hoge", "fuga", Arrays.asList(
        //                                 new PostbackAction("è¨€ hello2",
        //                                                    "hello ã�“ã‚“ã�«ã�¡ã�¯",
        //                                                    "hello ã�“ã‚“ã�«ã�¡ã�¯"),
        //                                 new MessageAction("Say message",
        //                                                   "Rice=ç±³")
        //                         ))
        //                 ));
        //         TemplateMessage templateMessage = new TemplateMessage("Carousel alt text", carouselTemplate);
        //         this.reply(replyToken, templateMessage);
        //         break;
        //     }
        //
        //     default:
        //     	String reply = null;
        //     	try {
        //     		reply = database.search(text);
        //     	} catch (Exception e) {
        //     		reply = text;
        //     	}
        //         log.info("Returns echo message {}: {}", replyToken, reply);
        //         this.replyText(
        //                 replyToken,
        //                 itscLOGIN + " says " + reply
        //         );
        //         break;
        // }
    }

	static String createUri(String path) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(path).build().toUriString();
	}

	private void system(String... args) {
		ProcessBuilder processBuilder = new ProcessBuilder(args);
		try {
			Process start = processBuilder.start();
			int i = start.waitFor();
			log.info("result: {} =>  {}", Arrays.toString(args), i);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} catch (InterruptedException e) {
			log.info("Interrupted", e);
			Thread.currentThread().interrupt();
		}
	}

	private static DownloadedContent saveContent(String ext, MessageContentResponse responseBody) {
		log.info("Got content-type: {}", responseBody);

		DownloadedContent tempFile = createTempFile(ext);
		try (OutputStream outputStream = Files.newOutputStream(tempFile.path)) {
			ByteStreams.copy(responseBody.getStream(), outputStream);
			log.info("Saved {}: {}", ext, tempFile);
			return tempFile;
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private static DownloadedContent createTempFile(String ext) {
		String fileName = LocalDateTime.now().toString() + '-' + UUID.randomUUID().toString() + '.' + ext;
		Path tempFile = KitchenSinkApplication.downloadedContentDir.resolve(fileName);
		tempFile.toFile().deleteOnExit();
		return new DownloadedContent(tempFile, createUri("/downloaded/" + tempFile.getFileName()));
	}





	public KitchenSinkController() {
		itscLOGIN = System.getenv("ITSC_LOGIN");
	}

	private String itscLOGIN;


	//The annontation @Value is from the package lombok.Value
	//Basically what it does is to generate constructor and getter for the class below
	//See https://projectlombok.org/features/Value
	@Value
	public static class DownloadedContent {
		Path path;
		String uri;
	}


	//an inner class that gets the user profile and status message
	class ProfileGetter implements BiConsumer<UserProfileResponse, Throwable> {
		private KitchenSinkController ksc;
		private String replyToken;

		public ProfileGetter(KitchenSinkController ksc, String replyToken) {
			this.ksc = ksc;
			this.replyToken = replyToken;
		}
		@Override
    	public void accept(UserProfileResponse profile, Throwable throwable) {
    		if (throwable != null) {
            	ksc.replyText(replyToken, throwable.getMessage());
            	return;
        	}
        	ksc.reply(
                	replyToken,
                	Arrays.asList(new TextMessage(
                		"Display name: " + profile.getDisplayName()),
                              	new TextMessage("Status message: "
                            		  + profile.getStatusMessage()))
        	);
    	}
    }

		public void scheduleTest(String id){
			Date date = new Date();
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			// ThreadPoolTaskScheduler threadPoolTaskScheduler = getTPTS();
			log.info("Schedule a task::" + df.format(date));
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.SECOND, 5);
			if(threadPoolTaskScheduler == null)
				log.info("threadPoolTaskScheduler is null");
			threadPoolTaskScheduler.schedule(new ScheduledAnnouncementTask("Thank you for using our chatbot"), cal.getTime());

		}
}


@Slf4j
class RunnableTask implements Runnable{
  private String message = null;
	private String userId = null;
  public RunnableTask(String str, String id){
    message = str;
		userId = id;
  }
  @Override
  public void run(){

		TextMessage textMessage = new TextMessage("Runnable test");
		PushMessage pushMessage = new PushMessage(
        userId,
        textMessage);
		String responseError = null;
		try{
			Response<BotApiResponse> response =
	    	LineMessagingServiceBuilder
	            .create("GknCtoyZkwyQjuLdv0blW1PN+mo92OQUU4lbSKXkt0vlioR/f/Z6GS0XjCWYGqpnfvhHhXLJ6t8c5pyvEWkTgZGI4dFKpCjkZXxhdVwQActmCqU+rI1tGsodnYBlRfP9s940G04I4bbR74YcbGgbTwdB04t89/1O/w1cDnyilFU=")
	            .build()
	            .pushMessage(pushMessage)
	            .execute();
			log.info(response.code() + " " + response.message());
		}catch(Exception e){
			responseError = e.toString();
		}
		if(responseError != null)
			log.info("PushMesage::Response::error:" + responseError);
  }
}
