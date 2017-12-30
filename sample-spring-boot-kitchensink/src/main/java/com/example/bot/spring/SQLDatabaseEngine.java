package com.example.bot.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;


import com.example.bot.spring.dbmanager.ConnectionManager;
import java.net.URISyntaxException;
// import java.net.URI;

@Slf4j
public class SQLDatabaseEngine extends DatabaseEngine {
	// @Autowired
	// @Qualifier("connectionManager")
	// ConnectionManager connectionManager;


	public static ConnectionManager connectionManager = new ConnectionManager();
	@Override
	String search(String text) throws Exception {
		//Write your code here
		String result = "null";
		String error = null;
		try{
			Connection connection = getConnection();
			PreparedStatement stmt = connection.prepareStatement("SELECT response FROM keywordlist WHERE keyword = ?");
			stmt.setString(1, text);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				result = rs.getString("response");
			rs.close();
			stmt.close();
			// connection.close();
		}catch(Exception e){
			// log.info(e);
			error = e.toString();
			result = "null exception";
		}
		if(error != null)
			log.info("sql error::" + error);
		return result;
	}


	public int insert(String sql){
		String error = null;
		int affectedRows = -1;
		try{
			Connection connection = getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			affectedRows = stmt.executeUpdate();
			// stmt.close();
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

		}catch(Exception e){
			error = e.toString();
		}
		if(error != null)
			log.info("Selection::error::" + error);
		if(rs == null)
			log.info("Selection::not found");


		return rs;
	}
	public void createUser(String line_id){
		/*0 == success*/
		/*-1 == fail*/
		String error = null;
		String inner_error = null;
		long id = -1;
		try{
			Connection connection = getConnection();
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO line_user (line_id) VALUES (?)");
			stmt.setString(1, line_id);
			int affectedRows = stmt.executeUpdate();

			if(affectedRows == 0)
				log.info("Create user failed, no row affected");

			try{
				PreparedStatement stmt2 = connection.prepareStatement("SELECT id FROM line_user WHERE line_id = ?");
				stmt2.setString(1, line_id);

				ResultSet rs = stmt2.executeQuery();
				if(rs.next())
					id = rs.getLong(1);

			}catch(Exception e){
				inner_error = e.toString();
			}
		}catch(Exception e){
			error = e.toString();
		}


		if(error != null)
			log.info("createUser::" + error);
		if(inner_error != null)
			log.info("createUser::inner::" + inner_error);

		log.info("createUser::getId::" + Long.toString(id));
	}


	private Connection getConnection() throws URISyntaxException, SQLException {

		// Connection connection;
		// URI dbUri = new URI(System.getenv("DATABASE_URL"));
    //
		// String username = dbUri.getUserInfo().split(":")[0];
		// String password = dbUri.getUserInfo().split(":")[1];
		// String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() +  "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
    //
		// log.info("Username: {} Password: {}", username, password);
		// log.info ("dbUrl: {}", dbUrl);
    //
		// connection = DriverManager.getConnection(dbUrl, username, password);

		return connectionManager.getConnection();
	}


	// String error = null;
	// Connection connection;
	// try{
	// return (new ConnectionManager()).getConnection();
	// }catch(Exception e){
	// 	error = e.toString();
	// }
	// if(error != null)
	// 	log.info("SQLDatabaseEngine::getConnection::" + error);
	//
	// return connection;
}
