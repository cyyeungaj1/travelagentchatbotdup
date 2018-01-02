package com.example.bot.spring.scheduler;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
// import com.example.bot.spring.ThreadPoolTaskScheduler;
@Slf4j
public class ThreadPoolTaskSchedulerExamples {
  @Bean
  public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
    ThreadPoolTaskScheduler tpts = new ThreadPoolTaskScheduler();
    tpts.setPoolSize(5);
    tpts.setThreadNamePrefix("Thread Pool Task S");
    return tpts;
  }
  @Autowired
  private ThreadPoolTaskScheduler taskScheduler;


  public ThreadPoolTaskSchedulerExamples(){
    Date date = new Date();
    DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    log.info("Schedule a task::" + df.format(date));
    if(taskScheduler == null)
      log.info("taskScheduler is null");
    taskScheduler.schedule(new RunnableTask("testing, run after 30 sec"), transferStringToDate("2018/01/03 00:15:00"));
  }


  public Date transferStringToDate(String str){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String parseError = null;
    Date date = null;
    try{
      date = sdf.parse(str.toString());
    }catch(Exception e){
      parseError = e.toString();
    }
    if(parseError != null)
      log.info("transferStringToDate::" + parseError);
    return date;
  }
}

@Slf4j
class RunnableTask implements Runnable{
  private String message = null;
  public RunnableTask(String str){
    message = str;
  }
  @Override
  public void run(){
    log.info("runable task::" + message);
  }
}
