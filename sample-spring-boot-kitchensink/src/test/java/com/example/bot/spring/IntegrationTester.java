package com.example.bot.spring;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;



import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.Import;

import org.springframework.web.bind.annotation.RestController;

// import com.example.bot.spring.scheduler.ThreadPoolTaskSchedulerConfig;
import com.example.bot.spring.scheduler.ThreadPoolTaskSchedulerExamples;
import com.example.bot.spring.IntegrationTester.MyController;
// @RunWith(SpringRunner.class)
// @SpringBootTest(classes = { IntegrationTester.class, MyController.class })
// @Import(ThreadPoolTaskSchedulerConfig.class)
@SpringBootApplication
@EnableScheduling

public class IntegrationTester{


  @RestController
    @Slf4j
    public static class MyController {

    }


  @Test
  public void schedulerTest() throws Exception {
      // ThreadPoolTaskSchedulerExamples tptse = new ThreadPoolTaskSchedulerExamples();
  }
}
