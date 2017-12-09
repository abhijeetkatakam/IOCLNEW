package com.bhavanee.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class Logging 
{
	private static Logger logger = Logger.getLogger(Logging.class);
	String logFilePath;
	String logFileName;

	public Logging(String filePath)
	{
		this.logFilePath=filePath;
		createLogFile();
	}

	private void createLogFile() 
	{
		logger.info("Inside createLogFile method");
		try 
		{
			String simpleDateFormat=new SimpleDateFormat("dd-M-yyyy_hh_mm_ss").format(new Date());
			logFileName=logFilePath+"\\"+"IOCL_"+simpleDateFormat;
			logger.info("logFileName is::::::::"+logFileName);
			File thisFile = new File(logFileName);
			boolean created=thisFile.createNewFile();
			logger.info("The custom log file name is "+logFileName+" has been created "+created);
		}
		catch(Exception exception) 
		{	
			logger.info("Exception occured while creating Custom Log file ",exception);
		}
	}

	public void performRequestLogging(Object request)  
	{
		try 
		{
			logger.debug("Inside performResponseLogging request object is "+request);
			StringBuilder loggingString = new StringBuilder(500);
			loggingString.append("=============REQUEST DATA STARTS =========================\n");
			if(request!=null) 
			{
				loggingString.append(request.toString()+"\n");
			}
			loggingString.append("=============REQUEST DATA ENDS ===========================\n");
			logMessage(loggingString.toString());
		}
		catch(Exception exception)
		{
			logger.info("Exception thrown while createing Custom Logger file inside preformRequestLogging ",exception);
		}
	}

	public void performResponseLogging(Object response)  
	{
		try 
		{
			logger.debug("Inside performResponseLogging response object is "+response);
			StringBuilder loggingString = new StringBuilder(500);
			loggingString.append("=============RESPONSE DATA STARTS =========================\n");
			if(response!=null) 
			{
				loggingString.append(response.toString()+"\n");
			}
			loggingString.append("=============RESPONSE DATA ENDS ===========================\n");
			logMessage(loggingString.toString());
		}catch(Exception exception)
		{
			logger.info("Exception thrown while createing Custom Logger file inside preformResponseLogging ",exception);
		}
	}

	public void logMessage(String jsonString) 
	{
		try
		{
			FileWriter fwriter = new FileWriter(new File(logFileName),true);
			BufferedWriter requestStringWriter = new BufferedWriter(fwriter);
			requestStringWriter.newLine();
			requestStringWriter.write(jsonString, 0, jsonString.length());
			requestStringWriter.newLine();
			requestStringWriter.close();
			fwriter.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
