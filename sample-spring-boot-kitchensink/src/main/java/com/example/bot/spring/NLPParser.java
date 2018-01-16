package com.example.bot.spring;

import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.Iterator;


import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIContext;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Entity;
import ai.api.model.Status;
import ai.api.model.Metadata;
import ai.api.model.Result;
import ai.api.model.AIOutputContext;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParseException;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class NLPParser{
  private AIResponse response = null;
  private Result result = null;
  public NLPParser(AIResponse r){
    response = r;
    if (response.getStatus().getCode() == 200) {
      result = response.getResult();
    } else {
      log.info(response.getStatus().getErrorDetails());
    }
  }

  public String getAction(){
    return result.getAction();
  }

  public String getReply(){
    return response.getResult().getFulfillment().getSpeech();
  }


  public void getAllContextInfo(){
    List<AIOutputContext> contexts = result.getContexts();
    for(int i = 0; i < contexts.size(); ++i){
      String name = contexts.get(i).getName();
      log.info("context name: " + name);
      log.info("lifespan: " + getLifespan(name));
    }
  }

  public Map<String, JsonElement> getParameter(String str){
    AIOutputContext context = result.getContext(str);
    if(context == null)
    {
      log.info("context is null");
      return null;
    }

    return context.getParameters();
  }

  public int getLifespan(String str){
    AIOutputContext context = result.getContext(str);
    if(context == null)
    {
      log.info("context is null");
      return -1;
    }

    return context.getLifespan();
  }
}
