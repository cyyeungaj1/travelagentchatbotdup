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
    push("Scheduled Announcement: \n\tTime: " + HKDate.dateFormat.format(date) + "\n\tContent: " + content);
    Runnable task = new ScheduledAnnouncementTask(content);
    controller.schedulePushMsg(task, herokuTime);
    setInterface(new MenuInterface(getUserId()));
  }

}


@Slf4j
class ParseDateTime extends State{
  private final String flag = "ParseDateTime";

  public ParseDateTime(UserInterface ui){
    super(ui);
  }

  public Date checkValidateDate(String str){
    String error = null;
    Date d = null;
    try{
      d = HKDate.dateFormat.parse(str);
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

  public void process(String text){
    AnnouncementInterface aUi = (AnnouncementInterface)ui;
    Date date = checkValidateDate(text);
    if(date == null){
      aUi.push("Invalid format");
      return;
    }

    if(!checkAfterCurr(date))
    {
      aUi.push("Please enter AFTER current date");
      return;
    }
    aUi.push("Valid date: " + HKDate.dateFormat.format(date));
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
  public static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  public static Date getCurrentTime(){
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.HOUR, DELAY);
    Date result = calendar.getTime();
    log.info("HKDate::current: " + dateFormat.format(result));
    return result;
  }

  public static Date convertToHerokuTime(Date d){
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(d);
    calendar.add(Calendar.HOUR, -DELAY);
    Date result = calendar.getTime();
    log.info("HKDate::convertToHerokuTime: " + dateFormat.format(result));
    return calendar.getTime();
  }

}
