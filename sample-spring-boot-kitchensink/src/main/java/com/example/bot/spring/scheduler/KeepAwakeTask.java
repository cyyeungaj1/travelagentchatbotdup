
package com.example.bot.spring.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import com.example.bot.spring.scheduler.ThreadPoolTaskSchedulerExamples;

@Slf4j
@Component
public class KeepAwakeTask {
  boolean test = false;
  @Scheduled(fixedRate = 60000)
  public void awake() {
      log.info("Heroku, awake!!!");
      if(!test){
        ThreadPoolTaskSchedulerExamples tptse = new ThreadPoolTaskSchedulerExamples();
      }
  }

}
