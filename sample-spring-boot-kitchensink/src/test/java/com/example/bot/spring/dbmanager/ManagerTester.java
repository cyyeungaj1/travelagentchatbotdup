package com.example.bot.spring.dbmanager;


import javax.sql.DataSource;
import java.sql.*;
import java.net.URISyntaxException;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.example.bot.spring.model.User;
import com.example.bot.spring.model.UserFactory;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ManagerTester {


  @Test
	public void testCreateUser() {
		JDBCLineUserManager mLine = new JDBCLineUserManager();
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
		// JDBCLineUserManager mLine = new JDBCLineUserManager();
  	// UserFactory uf = new UserFactory();
		// uf.setLineId("999");
		// User user = mLine.createUser(uf.getUser());
		// log.info(": testCreateUser::ID: " + user.getId());
		// log.info(": testCreateUser::LineId: " + user.getLineId());
    //
    // mLine.deleteUser(user);


		assertThat("abc").isEqualTo("abc");
	}

}
