package fr.mrcraftcod.utils;

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
import fr.mrcraftcod.Main;

/**
 * Configuration object, used to store and get variables.
 * 
 * @author MrCraftCod
 */
public class Configuration
{
	public static File appData;
	private File configFile;
	private List<String> lastConfigFile;

	/**
	 * Constructor.
	 * Will create a new file in %appdata%/APPNAME/vars.ors.
	 */
	public Configuration()
	{
		this("vars.ors");
	}

	/**
	 * Constructor.
	 * Will create a new file in %appdata%/[APPNAME]/[filename].
	 * 
	 * @param fileName The name of the config file.
	 */
	public Configuration(String fileName)
	{
		appData = new File(System.getenv("APPDATA"), Main.APPNAME);
		if(!appData.exists())
			appData.mkdir();
		configFile = new File(appData, fileName);
		Main.logger.log(Level.INFO, "Opening config file " + configFile.getAbsolutePath());
		try
		{
			lastConfigFile = readSmallTextFile(configFile);
		}
		catch(IOException e)
		{}
	}

	/**
	 * Used to get the string associated with the given key in the config file.
	 * 
	 * @param key The key of what to get.
	 * @return The String associated with the given key, null if the key doesn't exist.
	 */
	public String getVar(String key)
	{
		if(configFile.exists())
			try
			{
				for(String string : lastConfigFile)
					if(string.startsWith(key + ":"))
					{
						if(key.equals("api_key"))
							Main.logger.log(Level.FINE, "Found key " + key);
						else
							Main.logger.log(Level.FINE, "Found key " + key + " in " + string);
						return string.substring((key + ":").length());
					}
			}
			catch(final Exception exception)
			{}
		return null;
	}

	/**
	 * Used to get the String associated with the given key in the config file.
	 * 
	 * @param key The key of what to get.
	 * @param defaultValue The value to return if the key wasn't found.
	 * @return The String associated with the given key, defaultValue if the key doesn't exist.
	 */
	public String getString(String key, String defaultValue)
	{
		String value = getVar(key);
		return value == null ? defaultValue : value;
	}

	/**
	 * Used to get the boolean associated with the given key in the config file.
	 * 
	 * @param key The key of what to get.
	 * @param defaultValue The value to return if the key wasn't found.
	 * @return The boolean associated with the given key, defaultValue if the key doesn't exist.
	 */
	public boolean getBoolean(String key, boolean defaultValue)
	{
		try
		{
			String s = getVar(key);
			if(s != null)
				return Boolean.parseBoolean(s);
		}
		catch(Exception e)
		{
			Main.logger.log(Level.WARNING, "Failed to parse to boolean!", e);
		}
		return defaultValue;
	}

	/**
	 * Used to write an object to the config file.
	 * 
	 * @param key The key to store the value.
	 * @param obj The object to save (as string).
	 * @return A boolean showing if the operation has been done or not.
	 */
	public synchronized boolean writeVar(String key, Object obj)
	{
		Main.logger.log(Level.FINE, "Writting var " + key);
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
			Main.logger.log(Level.WARNING, "Failed to write config file!", exception);
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
			Main.logger.log(Level.WARNING, "Failed to read config file!", e);
		}
		Main.logger.log(Level.FINE, "Config file wrote");
		return true;
	}

	/**
	 * Used to delete a key in the config file.
	 * 
	 * @param key The key to delete.
	 * @return A boolean showing if the action has been done or not.
	 */
	public synchronized boolean deleteVar(String key)
	{
		Main.logger.log(Level.FINE, "Deletting var " + key);
		List<String> oldConfiguration = null;
		oldConfiguration = lastConfigFile;
		FileWriter fileWriter;
		try
		{
			fileWriter = new FileWriter(configFile, false);
		}
		catch(final IOException exception)
		{
			Main.logger.log(Level.WARNING, "Failed to write config file!", exception);
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
			Main.logger.log(Level.WARNING, "Failed to read config file!", e);
		}
		Main.logger.log(Level.FINE, "Config file wrote");
		return true;
	}

	/**
	 * Used to read the config file.
	 * 
	 * @param configFile The File object pointing to the config file.
	 * @return A list representing the read file.
	 * @throws IOException If the file cannot be read or is not found.
	 */
	public List<String> readSmallTextFile(final File configFile) throws IOException
	{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(configFile));
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

	/**
	 * Used to get the double associated with the given key in the config file.
	 * 
	 * @param key The key of what to get.
	 * @param defaultValue The value to return if the key wasn't found.
	 * @return The double associated with the given key, defaultValue if the key doesn't exist.
	 */
	public double getDouble(String key, double defaultValue)
	{
		try
		{
			return Double.parseDouble(getVar(key));
		}
		catch(Exception e)
		{
			Main.logger.log(Level.WARNING, "Failed to parse to double!", e);
		}
		return defaultValue;
	}

	/**
	 * Used to get the long associated with the given key in the config file.
	 * 
	 * @param key The key of what to get.
	 * @param defaultValue The value to return if the key wasn't found.
	 * @return The long associated with the given key, defaultValue if the key doesn't exist.
	 */
	public long getLong(String key, long defaultValue)
	{
		try
		{
			return Long.parseLong(getVar(key));
		}
		catch(Exception e)
		{
			Main.logger.log(Level.WARNING, "Failed to parse to long!", e);
		}
		return defaultValue;
	}

	/**
	 * Used to get the integer associated with the given key in the config file.
	 * 
	 * @param key The key of what to get.
	 * @param defaultValue The value to return if the key wasn't found.
	 * @return The integer associated with the given key, defaultValue if the key doesn't exist.
	 */
	public int getInt(String key, int defaultValue)
	{
		try
		{
			return Integer.parseInt(getVar(key));
		}
		catch(Exception e)
		{
			Main.logger.log(Level.WARNING, "Failed to parse to integer!", e);
		}
		return defaultValue;
	}

	/**
	 * Used to get config file.
	 * 
	 * @return The config file currently used by this object.
	 */
	public File getConfigFile()
	{
		return configFile;
	}
}
