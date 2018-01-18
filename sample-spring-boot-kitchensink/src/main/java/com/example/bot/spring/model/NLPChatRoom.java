package com.example.bot.spring.model;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIContext;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Entity;
import ai.api.model.Status;
import ai.api.model.Metadata;
import ai.api.model.Result;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import com.example.bot.spring.NLPParser;

@Slf4j
public class NLPChatRoom{
  AIConfiguration configuration = null;
  AIDataService dataService = null;
  private String userId = null;
  public NLPChatRoom(String id){
    userId = id;
    String error = null;
    try {
      configuration = new AIConfiguration("25537dd6c15f44148cd489334c15293d");
      dataService = new AIDataService(configuration);
      resetAll();
    } catch (Exception ex) {
      error = ex.toString();
    }

    if(error != null)
      log.info("testNLP::error::" + error);
  }

  public NLPParser query(String str){
    String error = null;
    NLPParser p = null;
    log.info("\n\n");
    try{
      AIRequest request = new AIRequest(str);
      request.setSessionId(userId);
      log.info("NLP testing:" + userId + "::"+str);
      AIResponse response = dataService.request(request);
      p = new NLPParser(response);
      // log.info("action: " + p.getAction());
      // log.info("reply: " + p.getReply());
      // p.getAllContextInfo();
    }catch(Exception e){
      error = e.toString();
    }
    if(error != null)
      log.info("query::" + error);
    log.info("\n\n");

    return p;
  }

  public void resetAll(){
    configuration = new AIConfiguration("25537dd6c15f44148cd489334c15293d");
    dataService = new AIDataService(configuration);
    try{
      AIRequest request = new AIRequest();
      request.setSessionId(userId);
      request.setResetContexts(true);
      dataService.request(request);
    }catch(Exception e){

    }
  }
}
