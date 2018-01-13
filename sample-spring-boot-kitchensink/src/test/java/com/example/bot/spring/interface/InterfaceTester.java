package com.example.bot.spring.userinterface;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import com.linecorp.bot.model.event.message.TextMessageContent;
import com.example.bot.spring.ChatBotController;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

@Slf4j
public class InterfaceTester{
  ChatBotController controller = new ChatBotController();
  @Test
  public void testMenu() {

    // String msg =
    controller.processInput("replyToken", "Ufc6e2d7119a764a7c5ebeca95a0d2914", new TextMessageContent("id", "hi"));
    controller.processInput("replyToken", "Ufc6e2d7119a764a7c5ebeca95a0d2914", new TextMessageContent("id", "1"));
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

  @Test
  public void testNLP() {
    String error = null;
    try {
      AIConfiguration configuration = new AIConfiguration("25537dd6c15f44148cd489334c15293d");
      AIDataService dataService = new AIDataService(configuration);
      AIRequest request = new AIRequest("i want to announce");
      AIResponse response = dataService.request(request);
      if (response.getStatus().getCode() == 200) {
        log.info(response.getResult().getFulfillment().getSpeech());
      } else {
        log.info(response.getStatus().getErrorDetails());
      }
    } catch (Exception ex) {
      error = ex.toString();
    }

    if(error != null)
      log.info("testNLP::error::" + error);
  }
}
