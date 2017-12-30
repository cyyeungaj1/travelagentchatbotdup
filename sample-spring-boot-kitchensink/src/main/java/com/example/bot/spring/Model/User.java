package com.example.bot.spring.model;


public class User{
  private long id = -1;
  private String line_id = null;

  public long getId(){return id;}
  public void setId(long i){id = i;}

  public String getLineId(){return line_id;}
  public void setLineId(String str){line_id = str;}
}
