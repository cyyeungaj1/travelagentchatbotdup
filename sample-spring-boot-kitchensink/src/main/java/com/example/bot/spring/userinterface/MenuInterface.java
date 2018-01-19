package com.example.bot.spring.userinterface;
import lombok.extern.slf4j.Slf4j;

import com.example.bot.spring.NLPParser;
import com.example.bot.spring.model.NLPChatRoom;

@Slf4j
public class MenuInterface extends UserInterface{
  public static final int INIT = 1;


  public MenuInterface(String id, int i){
    super(id);
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
    ui.nlpChatRoom.resetAll();
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
    // int choice = UserInterface.convertStringToInt(text);
    NLPParser p = ui.nlpChatRoom.query(text);
    ui.push(p.getReply());
    if(p.getAction().equals(AnnouncementInterface.ANNOUNCE_SECTION)){
      ui.setInterface(new AnnouncementInterface(ui.getUserId()));
    }
    // switch(choice){
    //   case 1:{
    //     ui.push("Annoucement");
    //
    //
    //     break;
    //   }
    //   case -1:{
    //     ui.push("Invalid input, try again");
    //     break;
    //   }
    //   default:{
    //     ui.push("Invalid input, try again2");
    //     break;
    //   }
    // }
  }

  public String getFlag(){
    return flag;
  }
}
