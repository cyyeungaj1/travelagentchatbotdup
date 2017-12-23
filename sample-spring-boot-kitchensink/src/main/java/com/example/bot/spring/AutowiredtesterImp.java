package com.example.bot.spring;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class AutowiredtesterImp implements Autowiredtester{
  public String test(){
    return "autowired test successful";
  }
}
