package com.example.bot.spring.userinterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuInterface extends UserInterface{

  public MenuInterface(String id){
    super(id);

    state = new ChooseSection(this);
    push("Menu:\n1.\tAnnouncement");
    log.info("MenuInterface");
  }
  public MenuInterface(String id, int i){
    userId = id;
    state = new ChooseSection(this);
    log.info("MenuInterface2");
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

//
// @Slf4j
// class InitChoice extends State{
//   private final String flag = "InitChoice";
//
//   public InitChoice(UserInterface ui){
//     super(ui);
//
//   }
//   public void process(String text){
//     ui.setState(new ChooseSection(ui));
//   }
//
//   public String getFlag(){
//     return flag;
//   }
// }
