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
    insertUser(user.getLineId());
    return findUserByLineId(user.getLineId());
  }
  public User findUserByLineId(String line_id){
    String error = null;
    String sql2 = "SELECT * FROM line_user WHERE line_id = '" + line_id + "'";
    User result = null;
    try{
      ResultSet rs = selection(sql2);
      if(rs.next())
        result = getRecord(rs);
    }catch(Exception e){
      error = e.toString();
    }
    if(error != null)
      log.info("findUserByLineId::error::" + error);
    if(result == null)
      log.info("findUserByLineId::error::result is null");

    return result;
  }
  public void insertUser(String line_id){

    String sql = "INSERT INTO line_user (line_id) VALUES ('" + line_id + "')";
    int result = insert(sql);
  }
  public void deleteUser(User user){
    String sql = "DELETE FROM line_user WHERE id = " + user.getId();
    delete(sql);
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
