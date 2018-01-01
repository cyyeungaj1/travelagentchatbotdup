
package com.example.bot.spring.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class KeepAwakeTask {

  @Scheduled(fixedRate = 60000)
  public void awake() {
      log.info("Heroku, awake!!!");
  }

}
