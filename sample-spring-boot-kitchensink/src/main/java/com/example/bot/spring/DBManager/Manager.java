package com.example.bot.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
// import com.example.bot.spring.DBManager.ConnectionManager;
import java.net.URISyntaxException;

public interface Manager{
  public static ConnectionManager connectionManager = new ConnectionManager();
}
