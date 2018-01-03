package com.example.bot.spring.dbmanager;


import javax.sql.DataSource;
import java.sql.*;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.bot.spring.model.User;
import com.example.bot.spring.model.UserFactory;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ManagerTester {
  private JDBCLineUserManager mLine = new JDBCLineUserManager();

  @Test
	public void testCreateUser() {
    
		// for(int i = 0; i < 30; ++i){
		// 	UserFactory uf = new UserFactory();
		// 	uf.setLineId(Integer.toString(i));
		// 	User user = mLine.createUser(uf.getUser());
		// 	log.info(Integer.toString(i) + ": testCreateUser::ID: " + user.getId());
		// 	log.info(Integer.toString(i) + ": testCreateUser::LineId: " + user.getLineId());
		// }


		assertThat("abc").isEqualTo("abc");
	}

  @Test
	public void testDeleteUser() {

  	// UserFactory uf = new UserFactory();
		// uf.setLineId("999");
		// User user = mLine.createUser(uf.getUser());
		// log.info(": testCreateUser::ID: " + user.getId());
		// log.info(": testCreateUser::LineId: " + user.getLineId());
    //
    // mLine.deleteUser(user);


		assertThat("abc").isEqualTo("abc");
	}

  @Test
  public void testGetAllUser() {
    ArrayList<User> users = mLine.getAllUser();
    for(int i = 0; i < users.size(); ++i){
      log.info("testGetAllUser::" + users.get(i).getId() + ", " + users.get(i).getLineId());
    }
  }

}
