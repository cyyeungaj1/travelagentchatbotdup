package com.example.bot.spring.dbmanager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;


import java.net.URISyntaxException;
// import java.net.URI;

@Slf4j
public class SQLDatabaseEngine{
	// @Autowired
	// @Qualifier("connectionManager")
	// private ConnectionManager connectionManager;


	private static ConnectionManager connectionManager = new ConnectionManager();

	public int insert(String sql){
		/*
		0: normal
		-1: error
		-2: insertion fail
		*/
		String error = null;
		int affectedRows = -1;
		try{
			Connection connection = getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			affectedRows = stmt.executeUpdate();
			stmt.close();
		}catch(Exception e){
			error = e.toString();
		}

		if(affectedRows == 0){
			log.info("Insert-2::insert failed, no row affected");
			return -2;
		}
		if(error != null){
			log.info("createUser-1::" + error);
			return -1;
		}
		return 0;
	}

	public ResultSet selection(String sql){
		//Write your code here
		ResultSet rs = null;
		String error = null;
		try{
			Connection connection = getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			// stmt.close();
			return rs;
		}catch(Exception e){
			error = e.toString();
		}
		if(error != null)
			log.info("Selection::error::" + error);
		if(rs == null)
			log.info("Selection::not found");


		return rs;
	}

	private Connection getConnection() throws URISyntaxException, SQLException {
		return connectionManager.getConnection();
	}

}
