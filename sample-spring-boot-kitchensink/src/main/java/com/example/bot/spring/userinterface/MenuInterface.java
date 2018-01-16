package com.example.bot.spring.userinterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuInterface extends UserInterface{
  public static final int INIT = 1;


  public MenuInterface(String id, int i){
    userId = id;
    state = new InitState(this);
    push("Welcome~\nMenu:\n1.\tAnnouncement");
  }
  public MenuInterface(String id){
    super(id);
    state = new ChooseSection(this);
    push("Menu:\n1.\tAnnouncement");
  }
}


@Slf4j
class InitState extends State{
  private final String flag = "InitState";

  public InitState(UserInterface ui){
    super(ui);
  }
  public void process(String text){
    ui.setState(new ChooseSection(ui));
  }

  public String getFlag(){
    return flag;
  }
}

@Slf4j
class ChooseSection extends State{
  private final String flag = "ChooseSection";

  public ChooseSection(UserInterface ui){
    super(ui);
  }
  public void process(String text){
    int choice = UserInterface.convertStringToInt(text);
    switch(choice){
      case 1:{
        ui.push("Annoucement");
        ui.setInterface(new AnnouncementInterface(ui.getUserId()));
        break;
      }
      case -1:{
        ui.push("Invalid input, try again");
        break;
      }
      default:{
        ui.push("Invalid input, try again2");
        break;
      }
    }
  }

  public String getFlag(){
    return flag;
  }
}
