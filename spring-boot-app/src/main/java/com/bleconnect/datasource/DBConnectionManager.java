package com.bleconnect.datasource;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Statement;

public final class DBConnectionManager {

	public Connection conn;
    private Statement statement;
    public static DBConnectionManager db;
    
	Properties props = new Properties();
	Connection con = null;

	String CLASS_NAME = this.getClass().getName();
	Logger logger = Logger.getLogger(CLASS_NAME);

	public Connection getConnection() {
		try {
	        Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db","root","root");
			if (con != null) {
				logger.info("Connection created Successfully !");
				System.out.println("Connection created Successfully !");
			} 
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return con;
	}
	
	
    private DBConnectionManager() {
        String url= "jdbc:mysql://localhost:3306/";
        String dbName = "db";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "root";
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection)DriverManager.getConnection(url+dbName,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }
    /**
     *
     * @return MysqlConnect Database connection object
     */
    public static synchronized DBConnectionManager getDbCon() {
        if ( db == null ) {
            db = new DBConnectionManager();
        }
        return db;
 
    }
}