package fr.mrcraftcod.osuuserinfo.utils;

import fr.mrcraftcod.osuuserinfo.Main;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Configuration object, used to store and get variables.
 *
 * @author MrCraftCod
 */
public class Configuration
{
	public static final String FONT = "font", FAVOURITEUSER = "favouriteUser", LASTMODE = "lastmode", SHOWNOTIFICATION = "showNotification", KEEPDATE = "keepDate", LOADINGSCREEN = "loadingScreen", LOCALE = "locale", REDUCETRAY = "reduceTray", APIKEY = "api_key", DEVMODE = "devMode", LASTVERSION = "last_version", AUTOCOMPLETION = "autoCompletion", TRACKEDUSERS = "tracked_users", STATSTOKEEP = "statsToKeep";
	private final File configFile;
	public static File appData;
	private List<String> currentConfig;

	/**
	 * Constructor.
	 * Will create a new file in %appdata%/[APPNAME]/vars.ors.
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
	private Configuration(String fileName)
	{
		appData = new File(System.getenv("APPDATA"), Main.APPNAME);
		if(!appData.exists())
			appData.mkdir();
		this.configFile = new File(appData, fileName);
		Utils.logger.log(Level.INFO, "Opening config file " + this.configFile.getAbsolutePath());
		this.currentConfig = readSmallTextFile(this.configFile);
	}

	/**
	 * Used to delete a key in the config file.
	 *
	 * @param key The key to delete.
	 * @return A boolean showing if the action has been done or not.
	 */
	public synchronized boolean deleteVar(String key)
	{
		Utils.logger.log(Level.INFO, "Deletting var " + key);
		List<String> oldConfiguration;
		oldConfiguration = this.currentConfig;
		FileWriter fileWriter;
		try
		{
			fileWriter = new FileWriter(this.configFile, false);
		}
		catch(final IOException exception)
		{
			Utils.logger.log(Level.WARNING, "Failed to write config file!", exception);
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
		this.currentConfig = readSmallTextFile(this.configFile);
		Utils.logger.log(Level.INFO, "Config file wrote");
		return true;
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
			Utils.logger.log(Level.WARNING, "Failed to parse to boolean!", e);
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
			Utils.logger.log(Level.WARNING, "Failed to parse to integer!", e);
		}
		return defaultValue;
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
	 * Used to get the string associated with the given key in the config file.
	 *
	 * @param key The key of what to get.
	 * @return The String associated with the given key, null if the key doesn't exist.
	 */
	private String getVar(String key)
	{
		if(this.configFile.exists())
			try
			{
				for(String string : this.currentConfig)
					if(string.startsWith(key + ":"))
					{
						if(key.equals(Configuration.APIKEY))
							Utils.logger.log(Level.INFO, "Found key " + key);
						else
							Utils.logger.log(Level.INFO, "Found key " + key + " in " + string);
						return string.substring((key + ":").length());
					}
			}
			catch(final Exception ignored)
			{
			}
		return null;
	}

	/**
	 * Used to read the config file.
	 *
	 * @param configFile The File object pointing to the config file.
	 * @return A list representing the read file.
	 */
	private List<String> readSmallTextFile(final File configFile)
	{
		List<String> fileLines = null;
		try(BufferedReader bufferedReader = new BufferedReader(new FileReader(configFile)))
		{
			String line = bufferedReader.readLine();
			fileLines = new ArrayList<>();
			while(line != null)
			{
				fileLines.add(line);
				line = bufferedReader.readLine();
			}
		}
		catch(IOException ignored)
		{
		}
		this.currentConfig = fileLines;
		return fileLines;
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
		Utils.logger.log(Level.INFO, "Writting var " + key);
		String value = obj == null ? "" : obj.toString();
		List<String> oldConfiguration;
		oldConfiguration = this.currentConfig;
		FileWriter fileWriter;
		try
		{
			fileWriter = new FileWriter(this.configFile, false);
		}
		catch(final IOException exception)
		{
			Utils.logger.log(Level.WARNING, "Failed to write config file!", exception);
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
		this.currentConfig = readSmallTextFile(this.configFile);
		Utils.logger.log(Level.INFO, "Config file wrote");
		return true;
	}
}
