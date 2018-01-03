package com.example.bot.spring.userinterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuInterface extends UserInterface{

  public MenuInterface(String id){
    userId = id;
    state = new InitChoice(this);
  }

}


@Slf4j
class InitChoice extends State{
  private final String flag = "InitChoice";

  public InitChoice(UserInterface ui){
    super(ui);
    ui.setMessage("Menu:\n1.\tAnnouncement");
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
    // String error = null;
    int choice = UserInterface.convertStringToInt(text);

    switch(choice){
      case 1:{
        ui.push("Annoucement");
        ui.setState(new InitChoice(ui));
        break;
      }
      case -1:{
        ui.push("Invalid input, try again");
        break;
      }
    }
    // ui.setMessage(text);
    // ui.setState(new InitChoice(ui));
  }

  public String getFlag(){
    return flag;
  }
}
