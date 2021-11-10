package com.log.event.logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

import com.log.event.logger.entity.Event;
import com.log.event.logger.exception.EventException;

public class EventsRepo {
	private static final Logger logger = Logger.getLogger(EventsRepo.class.getName());
	private static final String INSERT_INTO_EVENTS = "INSERT INTO EVENTS (ID,DURATION,HOST,TYPE,ALERT) VALUES (?, ?, ?, ?, ?)";
	private static final String DATABASE_DRIVER = "db.driver";
	private static final String DATABASE_SERVER = "db.server";
	private static final String DATABASE_USER_NAME = "db.user.name";
	private static final String DATABASE_USER_PASSWORD = "db.user.password";
	private static final String CHAR_SET = "char.set";
	private static String connectionString = "";
	private static String DRIVER = "";
	private static String USERNAME = "";
	private static String PASSWORD = "";
	private static String CHARSET = "";

	static {
		try (InputStream input = new FileInputStream("db"+File.separator+"database_config.properties")) {
			loadDatabaseProperties(input);
			Class.forName(DRIVER);
			createTables();
		} catch (ClassNotFoundException cnfe) {
			logger.info(cnfe.getMessage());
			throw new EventException("Failed to load hsqld JDBC driver");
		} catch (FileNotFoundException fnfe) {
			logger.info(fnfe.getMessage());
			throw new EventException("Unable to load database properties");
		} catch (IOException ioe) {
			logger.info(ioe.getMessage());
			throw new EventException("Unable to read database properties");
		}
	}
	private static void loadDatabaseProperties(InputStream input) throws IOException {
		Properties prop = new Properties();
		prop.load(input);
		connectionString = prop.getProperty(DATABASE_SERVER);
		DRIVER = prop.getProperty(DATABASE_DRIVER);
		USERNAME = prop.getProperty(DATABASE_USER_NAME);
		PASSWORD = prop.getProperty(DATABASE_USER_PASSWORD);
		CHARSET = prop.getProperty(CHAR_SET);
	}
	private static String readToString(String filePath) throws IOException {
		return FileUtils.readFileToString(new File(filePath), CHARSET);
	}

	private static void createTables() {
		Connection con = null;
		try {
			String createTable = readToString("sql"+File.separator+"createscript.sql");
			con = DriverManager.getConnection(connectionString,USERNAME.toLowerCase(),PASSWORD);
			con.createStatement().executeUpdate(createTable);
		} catch (IOException ioe) {
			logger.info(ioe.getMessage());
			throw new EventException("Failed while reading creat database tables script.");
		} catch (SQLException sqle) {
			logger.info(sqle.getMessage());
			throw new EventException("Failed while creating database tables.");
		}finally {
			closeConnection(con);
		}

	}

	public static void save(List<Event> alertEvents) {
		Connection con = null;
		try {
			logger.info("Inserting records in EVENTS TABLE ");
			con = DriverManager.getConnection(connectionString,USERNAME,PASSWORD);
			PreparedStatement prstInsert = con.prepareStatement(INSERT_INTO_EVENTS);
			for (Event event : alertEvents) {
				prstInsert.setString(1, event.getId());
				prstInsert.setInt(2, event.getTimeDifference());
				prstInsert.setString(3,event.getHost());
				prstInsert.setString(4,event.getType());
				prstInsert.setBoolean(5, event.isAlert());
				prstInsert.executeUpdate();
			}
		}  catch (SQLException sqle) {
			logger.info(sqle.getMessage());
			throw new EventException("Failed while inserting events.");
		} finally {
			closeConnection(con);
		}

	}
	private static void closeConnection(Connection con) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException sqle) {
				logger.info(sqle.getMessage());
				throw new EventException("Failed while closing database connection.");

			}
		}
	}
	public static int eventCount() {
		Connection con=null;
		try {
		con = DriverManager.getConnection(connectionString,USERNAME,PASSWORD);
		String countRecords =  "SELECT COUNT(*) FROM EVENTS;";
		ResultSet result = con.prepareStatement(countRecords).executeQuery();
		result.next();
		int count = result.getInt(1);
		logger.info("Rows in EVENTS TABLE "+count);
		return count;
		}catch(SQLException sqle){
			logger.info(sqle.getMessage());
			throw new EventException("Failed while executing select count query on event records.");
		}finally {
			closeConnection(con);
		}
		
	}
}
