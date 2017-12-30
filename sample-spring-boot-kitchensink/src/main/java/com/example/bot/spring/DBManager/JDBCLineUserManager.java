package com.example.bot.spring.dbmanager;
import com.example.bot.spring.model.User;
import com.example.bot.spring.model.UserFactory;
// import com.example.bot.spring.SQLDatabaseEngine;
import lombok.extern.slf4j.Slf4j;
import javax.sql.DataSource;
import java.sql.*;
@Slf4j
public class JDBCLineUserManager extends SQLDatabaseEngine{
  private final String USER_ID = "id";
  private final String LINE_ID = "line_id";

  public User createUser(User user){
    String error = null;
    String line_id = user.getLineId();
    String sql = "INSERT INTO line_user (line_id) VALUES ('" + line_id + "')";
    int result = insert(sql);

    String sql2 = "SELECT id FROM line_user WHERE line_id = '" + line_id + "'";
    long id = -1;
    try{
      ResultSet rs = selection(sql2);
      if(rs.next())
        id = rs.getInt(USER_ID);
      log.info("createUser::" + Long.toString(id));
    }catch(Exception e){
      error = e.toString();
    }
    if(error != null)
      log.info("createUser::error::" + error);

    user.setId(id);
    return user;
  }

  public User getRecord(ResultSet rs){
    String error = null;
    UserFactory uf = new UserFactory();
    try{

      long id = rs.getInt(USER_ID);
      String line_id = rs.getString(LINE_ID);

      uf.setId(id);
      uf.setLineId(line_id);
    }catch(Exception e){
      error = e.toString();
    }

    if(error != null)
      log.info("getRecord::error::" + error);

    return uf.getUser();
  }

}
