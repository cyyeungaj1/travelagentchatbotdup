
package com.example.bot.spring.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import com.example.bot.spring.scheduler.ThreadPoolTaskSchedulerExamples;
import java.net.HttpURLConnection;
import java.net.URL;
@Slf4j
@Component
public class KeepAwakeTask {
  boolean test = false;
  /*Every 20 min ping heroku server
  */
  @Scheduled(fixedRate = 1200000)
  public void awake() {
      log.info("Heroku, awake!!!");

      String error = null;
      try{
        URL url = new URL("http://travelagentchatbot.herokuapp.com/");
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

  public void ping(){

  }
}
