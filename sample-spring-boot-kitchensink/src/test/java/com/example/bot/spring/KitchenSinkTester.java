package com.example.bot.spring;


import javax.sql.DataSource;
import java.sql.*;
import java.net.URISyntaxException;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.io.ByteStreams;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.LineBotMessages;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

// import com.example.bot.spring.DatabaseEngine;
// import com.example.bot.spring.dbmanager.ConnectionManager;
// import com.example.bot.spring.dbmanager.JDBCLineUserManager;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { KitchenSinkTester.class, DatabaseEngine.class, SQLDatabaseEngine.class }) // ConnectionManager.class,

public class KitchenSinkTester {
	@Autowired
	private DatabaseEngine databaseEngine;
	@Autowired
	private SQLDatabaseEngine sqlDatabaseEngine;

	@Test
	public void testNotFound() throws Exception {
		boolean thrown = false;
		try {
			this.databaseEngine.search("no");
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(thrown).isEqualTo(true);
	}

	@Test
	public void testFound() throws Exception {
		boolean thrown = false;
		String result = null;
		try {
			result = this.databaseEngine.search("abc");
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(!thrown).isEqualTo(true);
		assertThat(result).isEqualTo("def");
	}

	@Test
	public void testHiFound() throws Exception {
		boolean thrown = false;
		String result = null;
		try {
			result = this.databaseEngine.search("Hi");
		} catch (Exception e) {
			thrown = true;
		}
		assertThat(!thrown).isEqualTo(true);
		assertThat(result).isEqualTo("Hey, how things going?");
	}


	// @Test
	// public void testAutoWired() {
  //
	// 	String result = connectionManager.test();
	// 	log.info("autowired::" + result);
	// 	assertThat(result).isEqualTo("autowired test successful");
	// }

	@Test
	public void testSQL() {

		try{

			String result = sqlDatabaseEngine.search("matt");
			assertThat(result).isEqualTo("is handsome");
			log.info("sql::" + result);
			result = sqlDatabaseEngine.search("handsome");
			assertThat(result).isEqualTo("is matt");
			log.info("sql::" + result);
			result = sqlDatabaseEngine.search("mattt");
			// assertThat(result).isEqualTo("is handsome");
			log.info("sql::" + result);

		}catch(Exception e){

		}
	}


	@Test
	public void testConnection() {
		for(int i = 0; i < 50; ++i){
			String error = null;
			try{
				SQLDatabaseEngine dbe = new SQLDatabaseEngine();
				String result = dbe.search("matt");
				log.info(Integer.toString(i) + ", " + result);
			}catch(Exception e){
				error = e.toString();
			}
			if(error != null)
				log.info(Integer.toString(i) + ", " + error);
		}
	}

	// @Test
	// public void testCreateUser() {
	// 	sqlDatabaseEngine.createUser("abc");
	// 	assertThat("abc").isEqualTo("abc");
	// }


}
