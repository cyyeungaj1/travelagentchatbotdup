package com.example.bot.spring.userinterface;

public abstract class State{
  protected UserInterface ui = null;

  public State(UserInterface ui){
    this.ui = ui;
  }

  public abstract void process(String text);
  public abstract String getFlag();

}
