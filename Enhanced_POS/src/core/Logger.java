package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import conf.GlobalConfiguration;


/**
 * this class is to log the customers' info
 * 
 * @author John Chung
 * 
 */
public class Logger
{
	private PrintWriter logger;
	private static Logger instance = new Logger();

	private Logger()
	{
		try
		{
			String configPath = GlobalConfiguration.getInstance().getConfPath();
			FileWriter fileWriter = new FileWriter(new File(configPath
					+ "salesRecord.txt"), true);
			logger = new PrintWriter(fileWriter, true);
		} catch (FileNotFoundException e)
		{
			System.err.println("Log file not found");

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static Logger getInstance()
	{
		return instance;
	}

	public synchronized void log(String msg)
	{	
		logger.println(msg);
	}

}
