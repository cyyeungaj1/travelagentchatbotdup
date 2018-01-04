package com.example.bot.spring.userinterface;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import lombok.extern.slf4j.Slf4j;

import com.example.bot.spring.scheduler.ScheduledAnnouncementTask;

@Slf4j
public class AnnouncementInterface extends UserInterface{

  private Date date = null;
  private String content = null;
  public void setDate(Date d){date = d;}
  public void setContent(String str){content = str;}

  public AnnouncementInterface(String id){
    super(id);
    push("Announcement Section");
    push("Please Enter DateTime (yyyy/MM/dd hh:mm:ss)");
    state = new ParseDateTime(this);
  }

  public void scheduleAnnouncement(){
    if(date == null || content == null){
      push("Imcomplete!!! error occur");
      setInterface(new AnnouncementInterface(getUserId()));
      return;
    }
    Runnable task = new ScheduledAnnouncementTask(content);
    controller.schedulePushMsg(task, date);
  }

}


@Slf4j
class ParseDateTime extends State{
  private final String flag = "ParseDateTime";
  private Date date = new Date();
  private DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

  public Date checkValidateDate(String str){
    String error = null;
    Date d = null;
    try{
      d = df.parse(str);
    }catch(Exception e){
      error = e.toString();
      return null;
    }
    return d;
  }

  public boolean checkAfterCurr(Date d){
    Date date = new Date();
    if(d.after(date))
      return true;
    return false;
  }

  public ParseDateTime(UserInterface ui){
    super(ui);
  }

  public void process(String text){
    // ui.push("process date");
    // ui.setInterface(new MenuInterface(ui.getUserId()));
    AnnouncementInterface aUi = (AnnouncementInterface)ui;
    Date date = checkValidateDate(text);
    if(date == null){
      aUi.push("Invalid format");
      return;
    }

    // Date date = df.parse(text);
    if(!checkAfterCurr(date))
    {
      aUi.push("Please enter AFTER current date");
      return;
    }
    aUi.push("Valid date: " + df.format(date));
    aUi.setDate(date);
    aUi.setState(new EnterContent(aUi));
  }

  public String getFlag(){
    return flag;
  }
}


@Slf4j
class EnterContent extends State{
  private final String flag = "EnterContent";


  public EnterContent(UserInterface ui){
    super(ui);
    ui.push("Please Enter the content");
  }

  public void process(String text){
    AnnouncementInterface aUi = (AnnouncementInterface)ui;
    aUi.setContent(text);
    aUi.push("Scheduled a announcement");
    ui.setInterface(new MenuInterface(ui.getUserId()));
  }

  public String getFlag(){
    return flag;
  }
}

// class AskYear implements State{
//   private final String flag = "year";
//   public AskYear(){
//
//   }
//   @Override
//   public String process(String text){
//     return text;
//   }
//   @Override
//   public String getFlag(){
//     return flag;
//   }
// }
//
// class AskMonth implements State{
//   private final String flag = "month";
//   @Override
//   public String process(String text){
//     return text;
//   }
//   @Override
//   public String getFlag(){
//     return flag;
//   }
// }
// class AskDay implements State{
//   private final String flag = "day";
//   @Override
//   public String process(String text){
//     return text;
//   }
//   @Override
//   public String getFlag(){
//     return flag;
//   }
// }
//
// class AskHour implements State{
//   private final String flag = "hour";
//   @Override
//   public String process(String text){
//     return text;
//   }
//   @Override
//   public String getFlag(){
//     return flag;
//   }
// }
//
// class AskMin implements State{
//   private final String flag = "min";
//   @Override
//   public String process(String text){
//     return text;
//   }
//   @Override
//   public String getFlag(){
//     return flag;
//   }
// }
