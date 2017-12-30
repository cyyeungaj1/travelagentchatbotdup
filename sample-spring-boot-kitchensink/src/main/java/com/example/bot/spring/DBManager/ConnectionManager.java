package com.example.bot.spring.dbmanager;

import lombok.extern.slf4j.Slf4j;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.net.URISyntaxException;
import java.net.URI;
import org.springframework.stereotype.Component;


@Slf4j
@Component("connectionManager")
public class ConnectionManager{
  Connection connection = null;
  public ConnectionManager(){
    log.info("ConnectionManager Constructor");
  }
  public Connection getConnection() throws URISyntaxException, SQLException {
		if(connection == null){
  		URI dbUri = new URI(System.getenv("DATABASE_URL"));

  		String username = dbUri.getUserInfo().split(":")[0];
  		String password = dbUri.getUserInfo().split(":")[1];
  		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() +  "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

  		log.info("Username: {} Password: {}", username, password);
  		log.info ("dbUrl: {}", dbUrl);

  		connection = DriverManager.getConnection(dbUrl, username, password);
    }

		return connection;
  }

  public void closeConnection(){
    if(connection == null)
      return;
    String error = null;
    try{
      connection.close();
      connection = null;
    }catch(Exception e){
      error = e.toString();
    }
    if(error != null)
      log.info("closeConnection::" + error);

  }

  public void test(){log.info("connection manager test()");}
}
