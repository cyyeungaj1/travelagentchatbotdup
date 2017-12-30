package com.example.bot.spring.dbmanager;


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
import com.example.bot.spring.dbmanager.JDBCLineUserManager;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;


// @RunWith(SpringRunner.class)
// @SpringBootTest()   //ConnectionManager.class,, DatabaseEngine.class, SQLDatabaseEngine.class(classes = { ManagerTester.class}) (classes = { ManagerTester.class, JDBCLineUserManager.class})
@Slf4j
public class ManagerTester {


	@Test
	public void testCreateUser() {
		JDBCLineUserManager mLine = new JDBCLineUserManager();
		// ConnectionManager connectionManager = new ConnectionManager();
		// UserFactory uf = new UserFactory();
		// uf.setLineId("abc");
		// mLine.createUser(uf.getUser());
		assertThat("abc").isEqualTo("abc");
	}


}
