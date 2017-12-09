package com.bhavanee.adaptermain;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.bhavanee.adapterconstants.SQLConstants;
import com.bhavanee.misc.MiscFiles;
import com.bhavanee.services.IOCLServiceIntegrations;


public class IOCLMain 
{
	private static Logger logger = Logger.getLogger(IOCLMain.class);
	private static Properties adapterProperties;
	public static void main(String[] args)
	{
		//Initialising the logger object
		MiscFiles.initialiseLogger();

		//Initialising the properties object which is required to read the adapter properties.
		adapterProperties=MiscFiles.initialiseRequiredProperties();
		
		MiscFiles miscFiles=new MiscFiles(adapterProperties);

		logger.info("====================JOB STARTED=======================");
		
		if(null!=args && (args.length<2 || args.length>2))
		{
			logger.info("Please provide the command line arguments date and time");
			System.exit(0);
		}
		
		//Creating the Connection to database
		Connection connection = null;
		Statement sqlStmt=null;
		try
		{
			//connection=MiscFiles.MSAccessConnectionObject();
			connection=MiscFiles.MYSQLConnectionObject();
			if(connection!=null)
				sqlStmt=connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			/*String currDate=new SimpleDateFormat("yyyyMMdd").format(new Date());
			logger.info("CurrentDate:::::::::::::::::"+currDate);*/
			//String currDate=new SimpleDateFormat("MM/dd/yyyy").format(new Date());
			String currDate=args[0];
			logger.info("CurrentDate:::::::::::::::::"+currDate);
			String baseQuery=SQLConstants.Fetch_Details;
			//String sqlQuery= baseQuery+" where Date="+"'"+currDate+"'"+" and Time="+"'"+adapterProperties.getProperty("Time")+"'";
			//String sqlQuery= baseQuery+" where DT="+"#"+currDate+"#"+" and TM="+"#"+adapterProperties.getProperty("Time")+"#";
			//String sqlQuery= baseQuery+" where DT="+"#"+currDate+"#"+" and TM="+"#"+args[1]+"#";
			String sqlQuery= baseQuery+" where DT="+"'"+currDate+"'"+" and TM="+"'"+args[1]+"'";
			logger.info("SQL Query:::::::::"+sqlQuery);
			ResultSet resultSet=sqlStmt.executeQuery(sqlQuery);
			logger.info("Result Set::::::::::::"+resultSet);
			ResultSet tempResultSet=resultSet;
			int rowCount=MiscFiles.GetQueryRowCount(tempResultSet);
			logger.info("Total Row Count:::::::::"+rowCount);
			
			if(tempResultSet!=null && rowCount!=0)
			{
				IOCLServiceIntegrations ioclServiceIntegrations=new IOCLServiceIntegrations(resultSet,Integer.parseInt(adapterProperties.getProperty("RetryCount")),adapterProperties,connection);
				ioclServiceIntegrations.deleteRecordsFromSecondaryTable();
				ioclServiceIntegrations.createAndSendAPIRequest(args[0],args[1]);
			}
			else
			{
				logger.info("No Records Found in dataBase for the date::"+currDate+" and time:::"+args[1]+"!!!");
			}
		}
		catch(Exception exception)
		{
			logger.info("Exception caused in main method:::::"+exception);
		}
		finally
		{
			logger.info("Finally block Closing Connections");
			try 
			{
				if(sqlStmt!=null && !sqlStmt.isClosed())
				{
					sqlStmt.close();
				}
				if(connection!=null && !connection.isClosed())
				{
					connection.close();
				}
			}
			catch (SQLException exception) 
			{
				logger.info("Finally block:::::"+exception);
				exception.printStackTrace();
			}

		}
		logger.info("====================JOB Completed======================");
	}	
}