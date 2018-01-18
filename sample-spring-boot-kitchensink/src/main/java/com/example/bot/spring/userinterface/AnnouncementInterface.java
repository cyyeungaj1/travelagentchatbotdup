package com.example.bot.spring.userinterface;
import java.util.Date;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;
import lombok.extern.slf4j.Slf4j;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParseException;

import com.example.bot.spring.scheduler.ScheduledAnnouncementTask;
import com.example.bot.spring.NLPParser;
import com.example.bot.spring.model.NLPChatRoom;
@Slf4j
public class AnnouncementInterface extends UserInterface{
  public static final String ANNOUNCE_SECTION = "announce.announce";

  private Date date = null;
  private String content = null;
  // public NLPChatRoom nlpChatRoom = null;

  public void setDate(Date d){date = d;}
  public void setContent(String str){content = str;}

  public AnnouncementInterface(String id){
    super(id);
    push("Announcement Section");
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
    end();
    setInterface(new MenuInterface(getUserId()));
  }


}


@Slf4j
class ParseDateTime extends State{
  private final String flag = "ParseDateTime";
  private final String PREV = "announce-followup";
  private final String CURR = "announce-ask-datetime-followup";
  private final String ACTION = "announce.announce-save-datetime";
  private final String DATE = "date.original";
  private final String TIME = "time.original";
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

    // if(n.getAction().equals(ACTION)){
    //   Map<String, JsonElement> parameters = n.getParameter(CURR);
    //   String date1 = parameters.get(DATE).getAsString();
    //   String time = parameters.get(TIME).getAsString();
    //   log.info("test::" + date1 + " " + time + "00");
    //
    // }
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

    String result = HKDate.dateFormat.format(date);
    NLPParser p = ui.nlpChatRoom.query(result);

    aUi.push(p.getReply());

    if(p.getLifespan(PREV) == -1){
      aUi.push("expire!!! something went wrong");
      aUi.expire();
    }

    if(p.getAction().equals(ACTION)){
      aUi.setDate(date);
      aUi.setState(new EnterContent(aUi));
    }else{
      aUi.push("something went wrong...");
    }
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
    NLPParser p = ui.nlpChatRoom.query(text);
    aUi.setContent(text);
    ui.push(p.getReply());
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
