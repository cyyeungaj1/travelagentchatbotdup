package com.example.bot.spring;


import javax.sql.DataSource;
import java.sql.*;
import java.net.URISyntaxException;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.google.common.io.ByteStreams;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.LineBotMessages;
import com.example.bot.spring.scheduler.ThreadPoolTaskSchedulerExamples;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

// import com.example.bot.spring.dbmanager.ConnectionManager;
// import com.example.bot.spring.dbmanager.JDBCLineUserManager;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { KitchenSinkTester.class}) // ConnectionManager.class,
// @SpringBootApplication
@EnableScheduling
public class KitchenSinkTester {

	@Test
  public void schedulerTest() throws Exception {
      // ThreadPoolTaskSchedulerExamples tptse = new ThreadPoolTaskSchedulerExamples();
			// if(taskScheduler == null)
			// 	log.info("taskscheduler == null");
			Date date = new Date();
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			// ThreadPoolTaskScheduler threadPoolTaskScheduler = getTPTS();
			log.info("curr datetime::" + df.format(date));
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.SECOND, 30);
			date = cal.getTime();
			log.info("Scheduled datetime::" + df.format(date));
  }
	@Test
	public void pingTest() throws Exception {

		String error = null;
		try{
			URL url = new URL("http://travelagentchatbot.herokuapp.com/");
			// URL url = new URL("https://google.com.hk");
			HttpURLConnection con= (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			// con.setConnectTimeout(5000);
			con.connect();

			int code = con.getResponseCode();
			log.info("ping code = " + Integer.toString(code));
		}catch(Exception e){
			error = e.toString();
		}
		if(error != null)
			log.info("ping::error:" + error);

	}
  //
	// @Test
	// public void chatbotConTrollerTest(){
	// 	ChatBotController cbc = new ChatBotController();
	// 	cbc.schedulePushMsg(null, null);
	// }
}
