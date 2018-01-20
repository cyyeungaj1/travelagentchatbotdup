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

  public void setDate(Date d){date = d;}
  public void setContent(String str){content = str;}

  public AnnouncementInterface(String id){
    super(id);
    state = new ParseDateTime(this);
  }

  public void scheduleAnnouncement(){
    if(date == null || content == null){
      push("Imcomplete!!! error occur");
      end();
      setInterface(new AnnouncementInterface(getUserId()));
      return;
    }
    Date herokuTime = DateConverter.convertToHerokuTime(date);
    push("Scheduled Announcement: \n\t\tTime: " + DateConverter.dateFormat.format(date) + "\n\t\tContent: " + content);
    Runnable task = new ScheduledAnnouncementTask(content);
    end();
    controller.schedulePushMsg(task, herokuTime);
  }
}


@Slf4j
class ParseDateTime extends State{
  public static final String FLAG = "ParseDateTime";
  private final String PREV = "announce-followup";
  private final String CURR = "announce-ask-datetime-followup";
  private final String ACTION = "announce.announce-save-datetime";
  private final String DATE = "date.original";
  private final String TIME = "time.original";

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

    if(!checkAfterCurr(date))
    {
      aUi.push("Please enter AFTER current date");
      return;
    }

    String result = DateConverter.dateFormat.format(date);
    NLPParser p = ui.nlpChatRoom.query(result);

    aUi.push(p.getReply());

    if(p.getLifespan(PREV) == -1 && p.getLifespan(CURR) == -1){
      aUi.push("expire!!! something went wrong");
      aUi.end();
      return;
    }

    if(p.getAction().equals(ACTION)){
      aUi.setDate(date);
      aUi.setState(new EnterContent(aUi));
    }else{
      aUi.push("something went wrong...");
    }
  }

  public String getFlag(){
    return FLAG;
  }


  private Date checkValidateDate(String str){
    String error = null;
    Date d = null;
    try{
      d = DateConverter.dateFormat.parse(str);
    }catch(Exception e){
      error = e.toString();
      return null;
    }
    return d;
  }

  private boolean checkAfterCurr(Date d){
    Date date = DateConverter.getCurrentTime();
    if(d.after(date))
      return true;
    return false;
  }
}


@Slf4j
class EnterContent extends State{
  public static final String FLAG = "EnterContent";
  private final String CURR = "announce-ask-content-followup";
  public EnterContent(UserInterface ui){
    super(ui);
  }

  public void process(String text){
    AnnouncementInterface aUi = (AnnouncementInterface)ui;
    NLPParser p = ui.nlpChatRoom.query(text);
    aUi.setContent(text);
    ui.push(p.getReply());
    aUi.setState(new ConfirmState(aUi));
  }

  public String getFlag(){
    return FLAG;
  }
}

@Slf4j
class ConfirmState extends State{
  public static final String FLAG = "ConfirmState";
  private final String CANCEL = "announce.announce-cancel";
  private final String CONFIRMED = "announce.announce-confirmed";
  private final String PREV = "announce-ask-content-followup";
  public ConfirmState(UserInterface ui){
    super(ui);
  }

  public void process(String text){
    AnnouncementInterface aUi = (AnnouncementInterface)ui;
    NLPParser p = ui.nlpChatRoom.query(text);
    aUi.push(p.getReply());

    if(p.getLifespan(PREV) == -1){
      aUi.push("expire!!! the announcement is canceled");
      aUi.end();
      return;
    }

    if(p.getAction().equals(CONFIRMED)){
      aUi.scheduleAnnouncement();
    }else if(p.getAction().equals(CANCEL)){
      aUi.end();
    }
  }

  public String getFlag(){
    return FLAG;
  }
}


@Slf4j
class DateConverter{
  /*heroku server system time + 8 = hong kong local time*/
  private static final int DELAY = 8;
  public static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  public static Date getCurrentTime(){
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.HOUR, DELAY);
    Date result = calendar.getTime();
    return result;
  }

  public static Date convertToHerokuTime(Date d){
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(d);
    calendar.add(Calendar.HOUR, -DELAY);
    Date result = calendar.getTime();
    return calendar.getTime();
  }

}
