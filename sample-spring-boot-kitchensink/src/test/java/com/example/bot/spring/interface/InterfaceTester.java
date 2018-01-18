package com.example.bot.spring.userinterface;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParseException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import com.linecorp.bot.model.event.message.TextMessageContent;
import com.example.bot.spring.ChatBotController;
import com.example.bot.spring.NLPParser;
import com.example.bot.spring.model.NLPChatRoom;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIContext;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Entity;
import ai.api.model.Status;
import ai.api.model.Metadata;
import ai.api.model.Result;
@Slf4j
public class InterfaceTester{
  ChatBotController controller = new ChatBotController();
  // private final static Gson GSON = GsonFactory.getDefaultFactory().getGson();
  private final Gson gson = new GsonBuilder().create();
  @Test
  public void testMenu() {

    // String msg =

    // controller.processInput("replyToken", "Ufc6e2d7119a764a7c5ebeca95a0d2914", new TextMessageContent("id", "hi"));
    // controller.processInput("replyToken", "Ufc6e2d7119a764a7c5ebeca95a0d2914", new TextMessageContent("id", "-1"));
    // controller.processInput("replyToken", "Ufc6e2d7119a764a7c5ebeca95a0d2914", new TextMessageContent("id", "1"));
    // controller.processInput("replyToken", "Ufc6e2d7119a764a7c5ebeca95a0d2914", new TextMessageContent("id", "1"));
    // controller.processInput("replyToken", "Ufc6e2d7119a764a7c5ebeca95a0d2914", new TextMessageContent("id", "-11"));
    // controller.processInput("replyToken", "Ufc6e2d7119a764a7c5ebeca95a0d2914", new TextMessageContent("id", "2017/01/01 11:11:11"));
    // controller.processInput("replyToken", "Ufc6e2d7119a764a7c5ebeca95a0d2914", new TextMessageContent("id", "2017/01/04 23:11:11"));
    // controller.processInput("replyToken", "Ufc6e2d7119a764a7c5ebeca95a0d2914", new TextMessageContent("id", "2018/01/15 23:11:11"));
    // controller.processInput("replyToken", "Ufc6e2d7119a764a7c5ebeca95a0d2914", new TextMessageContent("id", "Hello World!!!"));
    // controller.processInput("replyToken", "Ufc6e2d7119a764a7c5ebeca95a0d2914", new TextMessageContent("id", "1"));
  }
  //
  // @Test
  // public void testNLP() {
  //   String error = null;
  //   try {
  //     AIConfiguration configuration = new AIConfiguration("25537dd6c15f44148cd489334c15293d");
  //     AIDataService dataService = new AIDataService(configuration);
  //     AIRequest request = new AIRequest("i want to announce");
  //     AIResponse response = dataService.request(request);
  //     if (response.getStatus().getCode() == 200) {
  //       log.info(response.getResult().getFulfillment().getSpeech());
  //     } else {
  //       log.info(response.getStatus().getErrorDetails());
  //     }
  //   } catch (Exception ex) {
  //     error = ex.toString();
  //   }
  //
  //   if(error != null)
  //     log.info("testNLP::error::" + error);
  // }
  //

  // @Test
  // public void testNLP2() {
  //   String error = null;
  //   try {
  //     AIConfiguration configuration = new AIConfiguration("25537dd6c15f44148cd489334c15293d");
  //     AIDataService dataService = new AIDataService(configuration);
  //
  //     query(dataService, "i want to announce");
  //     query(dataService, "hi");
  //     query(dataService, "2017/01/01 11:11:11");
  //     query(dataService, "sure");
  //
  //
  //
  //   } catch (Exception ex) {
  //     error = ex.toString();
  //   }
  //
  //   if(error != null)
  //     log.info("testNLP::error::" + error);
  // }

  //
  // @Test
  // public void testNLP3() {
  //   String error = null;
  //   try {
  //     AIConfiguration configuration = new AIConfiguration("25537dd6c15f44148cd489334c15293d");
  //     AIDataService dataService = new AIDataService(configuration);
  //
  //     query(dataService, "i want to announce");
  //     query(dataService, "hi");
  //     query(dataService, "2017/01/01 11:11:11");
  //     query(dataService, "CONCONCON");
  //     NLPParser p = query(dataService, "sure");
  //     p.resetAll();
  //     query(dataService, "sure");
  //
  //   } catch (Exception ex) {
  //     error = ex.toString();
  //   }
  //
  //   if(error != null)
  //     log.info("testNLP::error::" + error);
  // }


  // @Test
  // public void testNLP4() {
  //   NLPChatRoom nlpChatRoom = new NLPChatRoom("111");
  //   nlpChatRoom.resetAll();
  //   //1. i want to announce
  //   final String action1 = "announce.announce";
  //   final String context1 = "announce-followup";
  //   NLPParser n1 = nlpChatRoom.query("i want to announce");
  //   log.info("1. i want to announce");
  //   if(n1.getAction().equals(action1)){
  //     log.info("action: " + action1);
  //   }else{
  //     log.info("session 1 fail");
  //   }
  //   log.info("ans. " + n1.getReply());
  //
  //   //2. 2018-01-06 10:10:10
  //   final String action2 = "announce.announce-save-datetime";
  //   final String context2 = "announce-ask-datetime-followup";
  //   NLPParser n2 = nlpChatRoom.query("2018-01-06 10:10:10");
  //   log.info("2. 2018-01-06 10:10:10");
  //   if(n2.getAction().equals(action2)){
  //     final String para1 = "date.original";
  //     final String para2 = "time.original";
  //     Map<String, JsonElement> parameters = n2.getParameter(context2);
  //     String date = parameters.get(para1).getAsString();
  //     String time = parameters.get(para2).getAsString();
  //     log.info("para1: " + date);
  //     log.info("para2: " + time);
  //   }else{
  //     log.info("session 2 fails");
  //     log.info("n2: " + n2.getAction());
  //     log.info("this: " + action2);
  //   }
  //   log.info("ans. " + n2.getReply());
  //
  //   //3. content
  //   final String action3 = "announce.announce-save-content";
  //   final String context3 = "announce-ask-content-followup";
  //   NLPParser n3 = nlpChatRoom.query("content is content");
  //   log.info("3. content");
  //   if(n3.getAction().equals(action3)){
  //     final String para1 = "content.original";
  //     Map<String, JsonElement> parameters = n3.getParameter(context3);
  //     String content = parameters.get(para1).getAsString();
  //     log.info("para1: " + content);
  //   }else{
  //     log.info("session 3 fails");
  //   }
  //   log.info("ans. " + n3.getReply());
  //
  //
  // }

  @Test
  public void testNLPMenu() {
    controller.processInput("replyToken", "Ufc6e2d7119a764a7c5ebeca95a0d2914", new TextMessageContent("id", "hi"));
    controller.processInput("replyToken", "Ufc6e2d7119a764a7c5ebeca95a0d2914", new TextMessageContent("id", "i want to announce"));
    controller.processInput("replyToken", "Ufc6e2d7119a764a7c5ebeca95a0d2914", new TextMessageContent("id", "2018/01/20 18:00:00"));
    controller.processInput("replyToken", "Ufc6e2d7119a764a7c5ebeca95a0d2914", new TextMessageContent("id", "this is the content kiki"));
    controller.processInput("replyToken", "Ufc6e2d7119a764a7c5ebeca95a0d2914", new TextMessageContent("id", "wtf"));
    // NLPChatRoom cr = new NLPChatRoom("111");
    // NLPParser p1 = cr.query("hi");
    // p1.getAllContextInfo();
    // log.info("1. " + p1.getReply());
    //
    // NLPParser p2 = cr.query("i want to announce");
    // p2.getAllContextInfo();
    // log.info("2. " + p2.getReply());
    //
    // cr.resetAll();
    //
    // NLPParser p3 = cr.query("hi");
    // p3.getAllContextInfo();
    // log.info("3. " + p3.getReply());
  }


  public void showMetadata(Result result){
    final Metadata metadata = result.getMetadata();
    if(metadata != null){
      // log.info(metadata.toString());
      log.info("get intent id: " + metadata.getIntentId());
      log.info("get intent name: " + metadata.getIntentName());
    }else{
      log.info("metadata is null");
    }
  }

  public NLPParser query(AIDataService dataService, String str){
    String error = null;
    NLPParser p = null;
    log.info("\n\n");
    try{
      AIRequest request = new AIRequest(str);
      request.setSessionId("111");
      AIResponse response = dataService.request(request);
      p = new NLPParser(response);
      log.info("action: " + p.getAction());
      log.info("reply: " + p.getReply());
      p.getAllContextInfo();
      // log.info("request::" + gson.toJson(request));
      // log.info("response::" + gson.toJson(response));
      // if (response.getStatus().getCode() == 200) {
      //   log.info(response.getResult().getFulfillment().getSpeech());
      // } else {
      //   log.info(response.getStatus().getErrorDetails());
      // }
      // showMetadata(response.getResult());
    }catch(Exception e){
      error = e.toString();
    }
    if(error != null)
      log.info("query::" + error);
    log.info("\n\n");

    return p;
  }
}
