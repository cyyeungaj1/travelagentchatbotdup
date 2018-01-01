package com.example.bot.spring.scheduler;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@ComponentScan(basePackages = "com.example.bot.spring.scheduler", basePackageClasses = {ThreadPoolTaskSchedulerExamples.class})
public class ThreadPoolTaskSchedulerConfig{
  @Bean
  public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
    ThreadPoolTaskScheduler tpts = new ThreadPoolTaskScheduler();
    tpts.setPoolSize(5);
    tpts.setThreadNamePrefix("Thread Pool Task S");
    return tpts;
  }
}
