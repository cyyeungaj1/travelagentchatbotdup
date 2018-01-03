package com.example.bot.spring.scheduler;

import java.util.ArrayList;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import com.example.bot.spring.model.User;
import com.example.bot.spring.model.UserFactory;
import com.example.bot.spring.dbmanager.JDBCLineUserManager;
import lombok.extern.slf4j.Slf4j;

import retrofit2.Response;

@Slf4j
public class ScheduledAnnouncementTask implements Runnable{
  private JDBCLineUserManager mLine = new JDBCLineUserManager();
  private String message = null;
  public ScheduledAnnouncementTask(String str){
    message = str;
  }
  @Override
  public void run(){
    String managerError = null;
    ArrayList<User> users = null;
    try{
      mLine.getAllUser();
    }catch(Exception e){
      managerError = e.toString();
      users = new ArrayList<>();
    }
    if(managerError != null)
      log.info("managerError::" + managerError);

    TextMessage textMessage = new TextMessage(message);
    for(int i = 0; i < users.size(); ++i){
      PushMessage pushMessage = new PushMessage(
          users.get(i).getLineId(),
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
}
