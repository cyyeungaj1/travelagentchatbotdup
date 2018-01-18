package com.example.bot.spring.userinterface;

import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import com.example.bot.spring.ChatBotController;
import com.example.bot.spring.NLPParser;
import com.example.bot.spring.model.NLPChatRoom;
import lombok.extern.slf4j.Slf4j;

import retrofit2.Response;


@Slf4j
public class UserInterface{
  protected String userId = null;
  protected State state = null;
  protected String message = null;
  protected ChatBotController controller = null;
  public NLPChatRoom nlpChatRoom = null;

  public UserInterface(){}
  // public UserInterface(ChatBotController c){
  //   controller = c;
  // }
  public UserInterface(String id){
    userId = id;
    nlpChatRoom = new NLPChatRoom(id);
  }



  public void process(String text){
    log.info("input::" + text);
    state.process(text);
  }

  public State getState(){return state;}
  public void setState(State s){state = s;}

  public String getUserId(){return userId;}
  public void setUserId(String id){
    userId = id;
    nlpChatRoom = new NLPChatRoom(id);
  }

  public void setController(ChatBotController c){controller = c;}

  public void setInterface(UserInterface userInterface){
    end();
    userInterface.setUserId(userId);
    controller.setInterface(userInterface);
  }

  public void expire(){
    setInterface(new MenuInterface(getUserId()));
  }
  private void end(){
    nlpChatRoom.resetAll();
  }
  // public void push(String msg){
  //   log.info("push::" + msg);
  // }
  public void push(String msg){
    log.info("push::" + msg);
    if(userId == null)
      return;
    TextMessage textMessage = new TextMessage(msg);
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

  public static int convertStringToInt(String str){
    int choice = 0;
    String error = null;
    try{
      choice = Integer.parseInt(str);
    }catch(Exception e){
      error = e.toString();
      choice = -1;
    }
    if(error != null)
      log.info("parseInt::error::" + error);

    return choice;
  }
}
