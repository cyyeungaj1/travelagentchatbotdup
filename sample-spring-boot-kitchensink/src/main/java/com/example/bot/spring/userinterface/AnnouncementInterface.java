package com.example.bot.spring.userinterface;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;
import lombok.extern.slf4j.Slf4j;

import com.example.bot.spring.scheduler.ScheduledAnnouncementTask;

@Slf4j
public class AnnouncementInterface extends UserInterface{

  private Date date = null;
  private String content = null;
  private DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
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
    Date herokuTime = HKDate.convertToHerokuTime(date);
    push("Scheduled Announcement: \n\tTime: " + df.format(date) + "\n\tContent: " + content);
    Runnable task = new ScheduledAnnouncementTask(content);

    controller.schedulePushMsg(task, herokuTime);

    setInterface(new MenuInterface(getUserId()));
  }

}


@Slf4j
class ParseDateTime extends State{
  private final String flag = "ParseDateTime";
  // private Date date = new Date();
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
    Date date = HKDate.getCurrentTime();
    if(d.after(date))
      return true;
    return false;
  }

  public ParseDateTime(UserInterface ui){
    super(ui);
  }

  public void process(String text){
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
    log.info("Content" + text);
    aUi.setContent(text);
    // aUi.push("Scheduled a announcement");
    // ui.setInterface(new MenuInterface(ui.getUserId()));
    aUi.scheduleAnnouncement();
  }

  public String getFlag(){
    return flag;
  }
}


@Slf4j
class HKDate{
  /*heroku server system time + 8 = hong kong local time*/
  private static final int DELAY = 8;
  // private static Calendar calendar = Calendar.getInstance();
  public static DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  public static Date getCurrentTime(){
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.HOUR, DELAY);
    Date result = calendar.getTime();
    log.info("HKDate::current: " + df.format(result));
    return result;
  }

  public static Date convertToHerokuTime(Date d){
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(d);
    calendar.add(Calendar.HOUR, -DELAY);
    Date result = calendar.getTime();
    log.info("HKDate::convertToHerokuTime: " + df.format(result));
    return calendar.getTime();
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
