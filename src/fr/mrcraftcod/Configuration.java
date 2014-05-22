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
import java.util.logging.Level;

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
		{
			Main.logger.log(Level.SEVERE, "Failed to read configuration file", e);
		}
		Main.logger.log(Level.FINE, "Configuration initialized with config file " + configFile.getAbsolutePath());
	}

	public String getVar(String key)
	{
		if(configFile.exists())
			try
			{
				Main.logger.log(Level.FINER, "Getting configuration with key " + key);
				for(String string : lastConfigFile)
					if(string.startsWith(key + ":"))
						return string.substring((key + ":").length());
			}
			catch(final Exception exception)
			{
				Main.logger.log(Level.WARNING, "Error when reading configuration!");
			}
		else
			Main.logger.log(Level.INFO, "No configuration was detected!");
		return "";
	}

	public boolean getBoolean(String key, boolean defaultValue)
	{
		try
		{
			return Boolean.parseBoolean(getVar(key));
		}
		catch(Exception e)
		{
			Main.logger.log(Level.WARNING, "Error parsing boolean type in configuration (" + key + ")!", e);
		}
		return defaultValue;
	}

	public synchronized boolean writeVar(String key, Object obj)
	{
		String value = obj == null ? "" : obj.toString();
		Main.logger.log(Level.INFO, "Writting config file with key " + key + " (" + value + ")");
		List<String> oldConfiguration = null;
		oldConfiguration = lastConfigFile;
		FileWriter fileWriter;
		try
		{
			fileWriter = new FileWriter(configFile, false);
		}
		catch(final IOException exception)
		{
			Main.logger.log(Level.WARNING, "Fail when opening config file!", exception);
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
		Main.logger.log(Level.FINER, "Writting config file done!");
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
		Main.logger.log(Level.WARNING, "Deletting config file with key " + key);
		List<String> oldConfiguration = null;
		oldConfiguration = lastConfigFile;
		FileWriter fileWriter;
		try
		{
			fileWriter = new FileWriter(configFile, false);
		}
		catch(final IOException exception)
		{
			Main.logger.log(Level.WARNING, "Fail when opening config file!", exception);
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
		Main.logger.log(Level.FINER, "Writting config file done!");
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
		Main.logger.log(Level.INFO, "Writting to a file (" + path.getAbsolutePath() + ")...");
		FileWriter fileWriter;
		try
		{
			fileWriter = new FileWriter(path, true);
		}
		catch(final IOException exception)
		{
			Main.logger.log(Level.WARNING, "Fail when opening config file!", exception);
			return false;
		}
		final PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter));
		printWriter.println(message);
		printWriter.close();
		Main.logger.log(Level.FINER, "Writting config file done!");
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
		{
			Main.logger.log(Level.SEVERE, "Failed to read configuration file", exception);
		}
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
		{
			Main.logger.log(Level.WARNING, "Error parsing double type in configuration (" + key + ")!", e);
		}
		return defaultValue;
	}

	public long getLong(String key, long defaultValue)
	{
		try
		{
			return Long.parseLong(getVar(key));
		}
		catch(Exception e)
		{
			Main.logger.log(Level.WARNING, "Error parsing long type in configuration (" + key + ")!", e);
		}
		return defaultValue;
	}

	public int getInt(String key, int defaultValue)
	{
		try
		{
			return Integer.parseInt(getVar(key));
		}
		catch(Exception e)
		{
			Main.logger.log(Level.WARNING, "Error parsing integer type in configuration (" + key + ")!", e);
		}
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
