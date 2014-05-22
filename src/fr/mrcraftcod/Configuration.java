package fr.mrcraftcod;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Configuration // TODO Javadoc
{
	public static File appData;
	private File configFile;
	private String normalConfigName = "vars.or";
	private List<String> lastConfigFile;

	public Configuration()
	{
		appData = new File(System.getenv("APPDATA"), Main.APPNAME);
		if(!appData.exists())
			appData.mkdir();
		configFile = new File(appData, normalConfigName);
		try
		{
			lastConfigFile = readSmallTextFile(configFile);
		}
		catch(IOException e)
		{}
	}

	public String getVar(String key)
	{
		if(configFile.exists())
			try
			{
				for(String string : lastConfigFile)
					if(string.startsWith(key + ":"))
						return string.substring((key + ":").length());
			}
			catch(final Exception exception)
			{}
		return "";
	}

	public boolean getBoolean(String key, boolean defaultValue)
	{
		try
		{
			return Boolean.parseBoolean(getVar(key));
		}
		catch(Exception e)
		{}
		return defaultValue;
	}

	public synchronized boolean writeVar(String key, Object obj)
	{
		String value = obj == null ? "" : obj.toString();
		List<String> oldConfiguration = null;
		oldConfiguration = lastConfigFile;
		FileWriter fileWriter;
		try
		{
			fileWriter = new FileWriter(configFile, false);
		}
		catch(final IOException exception)
		{
			return false;
		}
		final PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));
		boolean writted = false;
		if(oldConfiguration != null)
			for(String string : oldConfiguration)
				if(string.startsWith(key + ":"))
				{
					writted = true;
					printWriter.println(key + ":" + (value == null ? "" : value));
				}
				else
					printWriter.println(string);
		if(!writted)
			printWriter.println(key + ":" + (value == null ? "" : value));
		printWriter.close();
		try
		{
			lastConfigFile = readSmallTextFile(configFile);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return true;
	}

	public synchronized boolean deleteVar(String key)
	{
		List<String> oldConfiguration = null;
		oldConfiguration = lastConfigFile;
		FileWriter fileWriter;
		try
		{
			fileWriter = new FileWriter(configFile, false);
		}
		catch(final IOException exception)
		{
			return false;
		}
		final PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));
		if(oldConfiguration != null)
			for(String string : oldConfiguration)
			{
				if(string.startsWith(key + ":"))
					continue;
				printWriter.println(string);
			}
		printWriter.close();
		try
		{
			lastConfigFile = readSmallTextFile(configFile);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return true;
	}

	public synchronized boolean writeInFile(final File path, final String message)
	{
		if(path.isDirectory())
			return false;
		FileWriter fileWriter;
		try
		{
			fileWriter = new FileWriter(path, true);
		}
		catch(final IOException exception)
		{
			return false;
		}
		final PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));
		printWriter.println(message);
		printWriter.close();
		return true;
	}

	public List<String> readSmallTextFile(final File config) throws IOException
	{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(config));
		List<String> fileLines = null;
		try
		{
			String line = bufferedReader.readLine();
			fileLines = new ArrayList<String>();
			while(line != null)
			{
				fileLines.add(line);
				line = bufferedReader.readLine();
			}
		}
		catch(IOException exception)
		{}
		finally
		{
			bufferedReader.close();
		}
		lastConfigFile = fileLines;
		return fileLines;
	}

	public double getDouble(String key, double defaultValue)
	{
		try
		{
			return Double.parseDouble(getVar(key));
		}
		catch(Exception e)
		{}
		return defaultValue;
	}

	public long getLong(String key, long defaultValue)
	{
		try
		{
			return Long.parseLong(getVar(key));
		}
		catch(Exception e)
		{}
		return defaultValue;
	}

	public int getInt(String key, int defaultValue)
	{
		try
		{
			return Integer.parseInt(getVar(key));
		}
		catch(Exception e)
		{}
		return defaultValue;
	}

	public File getConfigFile()
	{
		return configFile;
	}

	public void setConfigFile(File configFile)
	{
		this.configFile = configFile;
	}
}
