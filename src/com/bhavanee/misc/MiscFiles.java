package com.bhavanee.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.bhavanee.adaptermain.IOCLMain;

public class MiscFiles 
{
	private static Logger logger = Logger.getLogger(MiscFiles.class);
	public static Properties adapterProperties=null;
	
	public MiscFiles(Properties adapterProperties)
	{
		MiscFiles.adapterProperties=adapterProperties;
	}
	
	
	public static Connection MSAccessConnectionObject()
	{
		Connection con=null;
		try
		{
			logger.info("Inside conn method");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String dbURL = adapterProperties.getProperty("URL");
			logger.info("ConnectionURL"+dbURL);
			con = DriverManager.getConnection(dbURL); 
			logger.info("Connection::::::::::"+con);
		} 
		catch (Exception exception) 
		{
			logger.info("Exception caused in the method MSAccessConnectionObject:::::"+exception);
		}
		return con;
	}
	
	public static Connection MYSQLConnectionObject()
	{
		Connection con=null;
		try
		{
			logger.info("Inside conn method");
			Class.forName("com.mysql.jdbc.Driver");
			String dbURL = adapterProperties.getProperty("URL");
			String dbUserName = adapterProperties.getProperty("DBUser");
			String dbPassword = adapterProperties.getProperty("DBPassword");
			logger.info("ConnectionURL"+dbURL);
			con = DriverManager.getConnection(dbURL,dbUserName,dbPassword); 
			logger.info("Connection::::::::::"+con);
		} 
		catch (Exception exception) 
		{
			logger.info("Exception caused in the method MYSQLConnectionObject:::::"+exception);
		}
		return con;
	}
	
	public static void initialiseLogger()
	{
		try
		{
			String log4jConfigFile = System.getProperty("user.dir") + File.separator + "log4j.properties";
			PropertyConfigurator.configure(log4jConfigFile);
		}
		catch(Exception exception)
		{
			logger.info("Exception caused in the method InitialiseLogger:::::"+exception);
		}
	}

	public static Properties initialiseRequiredProperties()
	{
		String adapterConfigFile = System.getProperty("user.dir") + File.separator + "adapter.properties";
		Properties adapterProperties=new Properties();
		InputStream inputStream = null;
		try 
		{
			inputStream = new FileInputStream(adapterConfigFile);
			adapterProperties.load(inputStream);
		}
		catch(Exception exception)
		{
			logger.info("Exception caused in the method InitialiseRequiredProperties:::::"+exception);
		}
		return adapterProperties;
	}

	public static int GetQueryRowCount(ResultSet rs) throws SQLException
	{
		int totalRows = 0;
		try {
			rs.last();
			totalRows = rs.getRow();
			rs.beforeFirst();
		} 
		catch(Exception ex) 
		{
			logger.info("Inside GetRowCountMethod:::::::::"+ex);
			return 0;
		}
		return totalRows ; 
	}

}
