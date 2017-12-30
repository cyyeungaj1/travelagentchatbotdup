package com.example.bot.spring;

public class UserFactory{
  private String line_id = null;
  private long id = -1;
  public void setLineId(String str){line_id = str;}
  public void setId(long i){id = i;}
  public User getUser(){
    User user = new User();
    if(line_id != null)
      user.setLineId(line_id);
    if(id != -1)
      user.setId(id);
    return user;
  }
}
