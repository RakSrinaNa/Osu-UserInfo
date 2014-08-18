package fr.mrcraftcod.utils;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.JSONObject;
import fr.mrcraftcod.Main;
import fr.mrcraftcod.enums.Fonts;
import fr.mrcraftcod.enums.Language;
import fr.mrcraftcod.frames.AboutFrame;
import fr.mrcraftcod.frames.ChangelogFrame;
import fr.mrcraftcod.frames.ChartFrame;
import fr.mrcraftcod.frames.MainFrame;
import fr.mrcraftcod.frames.StartupFrame;
import fr.mrcraftcod.objects.Stats;
import fr.mrcraftcod.objects.SystemTrayOsuStats;
import fr.mrcraftcod.objects.User;

/**
 * Class used to do a lot of things.
 *
 * @author MrCraftCod
 */
public class Utils
{
	public final static String[] UNITS = {"", "K", "M", "G", "T", "P"};
	private final static String logFileName = "log.log", MYSQLUSER = "osuuserinfo", MYSQLPASS = "osuuserinfopass";
	private static ServerSocket socket;
	private static TaskUpdater threadUpdater;
	public static String API_KEY = "", UUID = "";
	public static int numberTrackedStatsToKeep;
	public static Configuration config;
	public static ArrayList<Image> icons;
	public static StartupFrame startup;
	public static MainFrame mainFrame;
	public static AboutFrame aboutFrame;
	public static ChartFrame chartFrame;
	public static ResourceBundle resourceBundle;
	public static Logger logger;
	public static Color backColor, searchBarColor, noticeColor, noticeBorderColor;
	public static Border noticeBorder;
	public static Font fontMain;
	public static Date lastPost = new Date(0);
	public static User lastUser = new User();
	public static Stats lastStats = new Stats();
	public static BufferedImage avatarDefaultImage;
	public static Locale locale;
	public static Icon iconChangelogAdd, iconChangelogRemove, iconChangelogModify;
	public static SQLManager sql;

	/**
	 * Used to cute String objects.
	 *
	 * @param string The string to cut.
	 * @param deleteDelimiters True if delete delimiters, false if not.
	 * @param ending The ending of the string.
	 * @param begining The beginnings of the string.
	 * @return The cutted String.
	 *
	 * @throws Exception The the String can't be cutted.
	 */
	public static String cutLine(final String string, final boolean deleteDelimiters, final String ending, final String... begining) throws Exception
	{
		if(!string.contains(ending))
			throw new Exception();
		boolean exists = false;
		for(final String temp : begining)
			if(string.contains(temp))
				exists = true;
		if(!exists)
			throw new Exception();
		int beginingIndex = 0;
		for(final String temp : begining)
			if((beginingIndex = string.indexOf(temp)) > -1)
				break;
		String result = string.substring(beginingIndex, string.indexOf(ending) + ending.length());
		if(deleteDelimiters)
		{
			result = result.replace(ending, "");
			for(final String temp : begining)
				result = result.replace(temp, "");
		}
		return result;
	}

	/**
	 * Used to cut a string.
	 *
	 * @param string The string to cut.
	 * @param deleteDelimiters Delete delimiters or not.
	 * @param ending The ending of the string to cut.
	 * @param begining The beginnings of the string to cut.
	 * @return The cut string.
	 *
	 * @throws Exception If the string cannot be cut.
	 */
	public static String cutString(final String string, final boolean deleteDelimiters, final String ending, final String... begining) throws Exception
	{
		if(!string.contains(ending))
			throw new Exception();
		boolean exists = false;
		for(final String temp : begining)
			if(string.contains(temp))
				exists = true;
		if(!exists)
			throw new Exception();
		int beginingIndex = 0;
		for(final String temp : begining)
			if((beginingIndex = string.indexOf(temp)) > -1)
				break;
		String result = string.substring(beginingIndex, string.indexOf(ending) + ending.length());
		if(deleteDelimiters)
		{
			result = result.replace(ending, "");
			for(final String temp : begining)
				result = result.replace(temp, "");
		}
		return result;
	}

	/**
	 * Called to exit the program.
	 *
	 * @param close True if exit at the end of the function.
	 */
	public static void exit(boolean close)
	{
		Utils.logger.log(Level.INFO, "Exiting main frame...");
		Utils.setThreadUpdater(false);
		try
		{
			if(mainFrame != null)
				mainFrame.dispose();
		}
		catch(Exception e)
		{}
		try
		{
			socket.close();
		}
		catch(Exception e)
		{}
		try
		{
			Thread.sleep(250);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		socket = null;
		try
		{
			Thread.sleep(250);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		if(close)
			System.exit(0);
	}

	/**
	 * Used to create a new changelog frame.
	 *
	 * @param parent The parent frame.
	 */
	public static void getAllChangelogFrame(JFrame parent)
	{
		new ChangelogFrame(parent, Changelog.getAllChangelog(isCurrentVersionBeta() || config.getBoolean(Configuration.DEVMODE, false)));
	}

	/**
	 * Used to get the avatar of a user.
	 *
	 * @param userID The UserID of the user.
	 * @return The avatar.
	 *
	 * @throws Exception If the avatar isn't found.
	 */
	public synchronized static BufferedImage getAvatar(String userID) throws Exception
	{
		try
		{
			return ImageIO.read(new URL("https:" + Utils.cutString(Utils.getLineCodeFromLink("https://osu.ppy.sh/u/" + userID, "<div class=\"avatar-holder\">"), true, "\" alt=\"User avatar\"", "<div class=\"avatar-holder\"><img src=\"")));
		}
		catch(Exception e)
		{
			Utils.logger.log(Level.WARNING, "Error getting avatar for " + userID, e);
		}
		return avatarDefaultImage;
	}

	/**
	 * Used to create a new changelog frame for this version.
	 *
	 * @param parent The parent frame.
	 */
	public static void getChangelogFrame(JFrame parent)
	{
		new ChangelogFrame(parent, Main.VERSION, Changelog.getChangelogForVersion(Main.VERSION));
	}

	/**
	 * Used to get the country flag for a country.
	 *
	 * @param country The country to get the flag.
	 * @return The flag.
	 *
	 * @throws Exception If the flag isn't found.
	 */
	public synchronized static BufferedImage getCountryFlag(String country) throws Exception
	{
		try
		{
			return ImageIO.read(new URL("http://s.ppy.sh/images/flags/" + country.toLowerCase() + ".gif"));
		}
		catch(Exception e)
		{}
		return avatarDefaultImage;
	}

	/**
	 * Used to get a text representing a file size.
	 *
	 * @param size The size to get the text.
	 * @return The text size.
	 */
	public static String getDownloadSizeText(double size)
	{
		NumberFormat format = NumberFormat.getInstance(locale);
		format.setMaximumFractionDigits(2);
		int unit = 0;
		while(size > 1024)
		{
			unit++;
			size /= 1024;
		}
		String result = format.format(size) + " " + UNITS[unit] + "B";
		return result;
	}

	/**
	 * Used to get the HTML source code from a link.
	 *
	 * @param link The link where to get the code.
	 * @return The HTML source code.
	 *
	 * @throws IOException If the source code cannot be get.
	 */
	public static String[] getHTMLCode(String link) throws IOException
	{
		final URL url = new URL(link);
		StringBuilder page = new StringBuilder();
		String str = null;
		final URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0");
		connection.setRequestProperty("Accept-Language", "fr-FR");
		connection.setConnectTimeout(30000);
		connection.setReadTimeout(30000);
		final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
		while((str = in.readLine()) != null)
			page.append(str + "\n");
		in.close();
		return page.toString().split("\n");
	}

	/**
	 * Used to get infos for a user.
	 *
	 * @param showerror Show or not the red bar if error is thrown.
	 */
	public static void getInfos(boolean showerror)
	{
		getInfos(mainFrame.getUsernameSearched(), showerror, false);
	}

	/**
	 * Used to get infos for a user.
	 *
	 * @param user The user to get the stats.
	 * @param showerror Show or not the red bar if error is thrown.
	 * @param forceDisplay Force the function to update stats on screen even if they are the same.
	 */
	public static void getInfos(String user, boolean showerror, boolean forceDisplay)
	{
		getInfos(user, showerror, forceDisplay, false);
	}

	/**
	 * Used to get infos for a user.
	 *
	 * @param user The user to get the stats.
	 * @param showerror Show or not the red bar if error is thrown.
	 * @param forceDisplay Force the function to update stats on screen even if they are the same.
	 * @param forceFetch Force the program to fetch datas even if the cooldown time isn't finished.
	 */
	public static void getInfos(String user, boolean showerror, boolean forceDisplay, boolean forceFetch)
	{
		LoadingWorker load = new LoadingWorker(mainFrame, user, showerror, Utils.config.getBoolean(Configuration.LOADINGSCREEN, true), forceDisplay, forceFetch);
		load.execute();
	}

	/**
	 * Used to get infos for a user from the API.
	 *
	 * @param user The user to get the infos.
	 * @param showerror Show or not the red bar if error is thrown.
	 * @param forceDisplay Force the function to update stats on screen even if they are the same.
	 * @param forceFetch Force the program to fetch datas even if the cooldown time isn't finished.
	 * @return True if the stats have changed, false if the stats are the same or cannot be found.
	 */
	public static boolean getInfosServer(String user, boolean showerror, boolean forceDisplay, boolean forceFetch)
	{
		if(!forceFetch && !isValidTime() || !isValidUser(user))
			return false;
		Utils.logger.log(Level.INFO, "Getting user infos " + user);
		Utils.lastPost = new Date();
		mainFrame.usernameField.setBackground(null);
		mainFrame.userNameFieldTextComponent.setBackground(null);
		try
		{
			User currentUser = new User();
			Stats currentStats = new Stats();
			currentStats.setDate(new Date().getTime());
			final JSONObject jsonResponse = new JSONObject(Utils.sendPost("get_user", Utils.API_KEY, user, mainFrame.getSelectedMode()));
			mainFrame.username.setBackground(Utils.noticeColor);
			mainFrame.username.setBorder(Utils.noticeBorder);
			boolean tracked = Utils.isUserTracked(jsonResponse.getString("username")) && false;
			boolean newUser = !Utils.lastUser.getUsername().equalsIgnoreCase(user);
			if(newUser)
			{
				if(tracked)
					try
					{
						currentUser = User.deserialize(new File(Configuration.appData, jsonResponse.getString("username")));
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				currentUser.addMYSQLStats(mainFrame.getSelectedMode(), sql.getUserStats(jsonResponse.getInt("user_id"), mainFrame.getSelectedMode()));
			}
			else if(!newUser)
				currentUser = Utils.lastUser;
			Stats previousStats = currentUser.getLastStats(mainFrame.getSelectedMode());
			mainFrame.track.setEnabled(true);
			mainFrame.track.setSelected(tracked);
			mainFrame.autoUpdateCheck.setEnabled(tracked);
			currentUser.setUsername(jsonResponse.getString("username"));
			currentUser.setUserID(jsonResponse.getInt("user_id"));
			currentUser.setCountry(jsonResponse.getString("country"));
			currentStats.setRank(jsonResponse.getDouble("pp_rank"));
			currentStats.setPlaycount(jsonResponse.getInt("playcount"));
			currentStats.setRankedScore(jsonResponse.getLong("ranked_score"));
			currentStats.setTotalScore(jsonResponse.getLong("total_score"));
			currentStats.setAccuracy(jsonResponse.getDouble("accuracy"));
			currentStats.setPp(jsonResponse.getDouble("pp_raw"));
			currentStats.setLevel(jsonResponse.getDouble("level"));
			currentStats.setCountSS(jsonResponse.getInt("count_rank_ss"));
			currentStats.setCountS(jsonResponse.getInt("count_rank_s"));
			currentStats.setCountA(jsonResponse.getInt("count_rank_a"));
			currentStats.setCount300(jsonResponse.getLong("count300"));
			currentStats.setCount100(jsonResponse.getLong("count100"));
			currentStats.setCount50(jsonResponse.getLong("count50"));
			currentStats.updateTotalHits();
			currentStats.setMode(mainFrame.getSelectedMode());
			try
			{
				String[] pageProfile = getHTMLCode("https://osu.ppy.sh/pages/include/profile-general.php?u=" + currentUser.getUserID() + "&m=" + mainFrame.getSelectedMode());
				try
				{
					currentStats.setCountryRank(Double.parseDouble(Utils.getNextLineCodeFromLink(pageProfile, 2, "<img class='flag' title='' src=").get(0).replace("#", "").replace(",", "")));
				}
				catch(Exception e)
				{
					logger.log(Level.WARNING, "Can't find country rank for user!", e);
				}
				try
				{
					currentStats.setMaximumCombo(Integer.parseInt(Utils.cutLine(Utils.getLineCodeFromLink(pageProfile, "<div class='profileStatLine' title='Highest combo achieved (hits in a row without a miss).'><b>Maximum Combo</b>"), true, "</div><br/>", "<b>Maximum Combo</b>: ").replace(",", "")));
				}
				catch(Exception e)
				{
					logger.log(Level.WARNING, "Can't find maximum combo for user!", e);
				}
			}
			catch(Exception e)
			{
				logger.log(Level.INFO, "Can't get additional informations for user!", e);
			}
			if(sql != null)
				sql.sendUser(currentUser, currentStats, UUID);
			if(!forceDisplay && currentStats.equals(Utils.lastStats))
				return false;
			mainFrame.username.setForeground(getRandomColor());
			mainFrame.updateStatsDates(currentUser);
			mainFrame.displayStats(currentUser, currentStats);
			mainFrame.updateTrackedInfos(currentUser, currentStats, previousStats, true);
			mainFrame.setValidButonIcon("R");
			if(forceDisplay || !currentUser.isSameUser(Utils.lastUser))
				mainFrame.setFlagAndAvatar(currentUser);
			if(currentStats.equals(Utils.lastStats))
				return false;
			mainFrame.setTextUser(currentUser.getUsername());
			currentUser.setStats(!showerror, currentStats, mainFrame.getSelectedMode());
			if(tracked)
				currentUser.serialize(new File(Configuration.appData, currentUser.getUsername()));
			Utils.lastStats = currentStats;
			Utils.lastUser = currentUser;
		}
		catch(Exception e)
		{
			if(showerror)
			{
				Utils.logger.log(Level.SEVERE, "Error reading infos!", e);
				mainFrame.usernameField.setBackground(Color.RED);
				mainFrame.userNameFieldTextComponent.setBackground(Color.RED);
			}
			return false;
		}
		return true;
	}

	/**
	 * Used to get the level.
	 *
	 * @param level The level where to get the level.
	 * @return The level.
	 */
	public static int getLevel(double level)
	{
		return (int) level;
	}

	/**
	 * Used to find a line from the HTML source code of a link.
	 *
	 * @param link The link where to get the HTML source code.
	 * @param gets The parts to identify the wanted line.
	 * @return The wanted line containing the text.
	 *
	 * @throws Exception If the line cannot be found.
	 */
	public static String getLineCodeFromLink(final String link, final String... gets) throws Exception
	{
		return getLineCodeFromLink(getHTMLCode(link), gets);
	}

	/**
	 * Used to find a line from the HTML source code of a link.
	 *
	 * @param lines The HTML lines.
	 * @param gets The parts to identify the wanted line.
	 * @return The wanted line containing the text.
	 *
	 * @throws Exception If the line cannot be found.
	 */
	public static String getLineCodeFromLink(final String[] lines, final String... gets) throws Exception
	{
		for(final String get : gets)
			for(final String tempLine : lines)
				if(tempLine.contains(get))
					return tempLine;
		throw new Exception("Cannot get code from link");
	}

	/**
	 * Used to get the name of a mode.
	 *
	 * @param mode The mode.
	 * @return The mod's name.
	 */
	public static String getModeName(int mode)
	{
		switch(mode)
		{
			case 1:
				return "Taiko";
			case 2:
				return "Catch The Beat";
			case 3:
				return "osu!mania";
		}
		return "osu!";
	}

	/**
	 * Used to get a new file object from the user.
	 *
	 * @param lastFile Where to open the frame.
	 * @param mode The mode of the selection.
	 * @param filter The filter to set.
	 * @return The selected file.
	 */
	public static File getNewFilePatch(File lastFile, int mode, FileNameExtensionFilter filter)
	{
		File file = null;
		try
		{
			File repertoireCourant = new File(System.getProperty("user.home")).getCanonicalFile();
			if(lastFile != null)
				repertoireCourant = lastFile.getCanonicalFile();
			Utils.logger.log(Level.FINE, "Previous folder: " + repertoireCourant.getAbsolutePath());
			final JFileChooser dialogue = new JFileChooser(repertoireCourant);
			dialogue.setDialogTitle(resourceBundle.getString("get_folder_avatar_save_title"));
			dialogue.setLocale(locale);
			dialogue.setFileFilter(filter);
			dialogue.setFileSelectionMode(mode);
			if(dialogue.showSaveDialog(null) == JFileChooser.CANCEL_OPTION)
				return null;
			file = dialogue.getSelectedFile();
		}
		catch(final Exception e)
		{}
		return file;
	}

	/**
	 * Used to get lines from an HTML page with an offset from a "tag".
	 *
	 * @param link The link where to get the HTML source code.
	 * @param offsetLine The offset between the "tag" and the lines to save.
	 * @param gets The "tags"
	 * @return An array of the found lines.
	 *
	 * @throws Exception If no lines were found.
	 */
	public static ArrayList<String> getNextLineCodeFromLink(final String link, final int offsetLine, final String... gets) throws Exception
	{
		return getNextLineCodeFromLink(getHTMLCode(link), offsetLine, gets);
	}

	/**
	 * Used to get lines from an HTML page with an offset from a "tag".
	 *
	 * @param lines The HTML lines.
	 * @param offsetLine The offset between the "tag" and the lines to save.
	 * @param gets The "tags"
	 * @return An array of the found lines.
	 *
	 * @throws Exception If no lines were found.
	 */
	public static ArrayList<String> getNextLineCodeFromLink(final String[] lines, final int offsetLine, final String... gets) throws Exception
	{
		ArrayList<String> allLines = new ArrayList<String>();
		for(final String get : gets)
			for(int i = 0; i < lines.length; i++)
				if(lines[i].contains(get))
					try
					{
						allLines.add(lines[i + offsetLine]);
					}
					catch(Exception e)
					{
						Utils.logger.log(Level.WARNING, "", e);
					}
		if(allLines.size() > 0)
			return allLines;
		throw new Exception("Cannot get code from link");
	}

	/**
	 * Used to get the progress for a level.
	 *
	 * @param level The level.
	 * @return The progress for the level.
	 */
	public static double getProgressLevel(double level)
	{
		return level - (int) level;
	}

	/**
	 * Used to get the needed for to up to the next level.
	 *
	 * @param currentLevel The current level.
	 * @param currentScore The current score.
	 * @return The score needed to up to the next level.
	 */
	public static double getScoreToNextLevel(int currentLevel, double currentScore)
	{
		currentLevel++;
		double result = -1;
		// 5,000 / 3 * (4n^3 - 3n^2 - n) + 1.25 * 1.8^(n - 60), where n <= 100
		// 26,931,190,829 + 100,000,000,000 * (n - 100), where n >= 101
		if(currentLevel <= 100)
		{
			if(currentLevel >= 2)
			{
				double temp = 4 * round(Math.pow(currentLevel, 3), 0) - 3 * round(Math.pow(currentLevel, 2), 0) - currentLevel;
				result = 5000D / 3D * temp + 1.25 * round(Math.pow(1.8, currentLevel - 60), 0);
			}
			else
				result = 0;
		}
		else if(currentLevel >= 101)
		{
			int temp = currentLevel - 100;
			result = 26931190829D + 100000000000D * temp;
		}
		return round(result - currentScore, 0);
	}

	/**
	 * Used to get the tracked users.
	 *
	 * @return The tracked users.
	 */
	public static ArrayList<String> getTrackedUsers()
	{
		ArrayList<String> trackedList = new ArrayList<String>();
		String tracked = Utils.config.getString(Configuration.TRACKEDUSERS, "");
		for(String user : tracked.split(","))
			trackedList.add(user);
		return trackedList;
	}

	/**
	 * Used to launch the program.
	 *
	 * @param args {@link Main#main(String[])}
	 * @throws IOException If there were an error during startup.
	 *
	 * @see Main#main(String[])
	 */
	public static void init(String[] args) throws IOException
	{
		logger = Logger.getLogger(Main.APPNAME);
		logger.setLevel(Level.INFO);
		boolean resetedLog = false;
		File confFolder = new File(System.getenv("APPDATA"), Main.APPNAME);
		File logFile = new File(confFolder, logFileName);
		if(!confFolder.exists())
			confFolder.mkdirs();
		if(logFile.length() > 2500000)
		{
			resetedLog = true;
			logFile.delete();
		}
		FileHandler fileTxt = new FileHandler(logFile.getAbsolutePath(), true);
		fileTxt.setFormatter(new LogFormatter());
		fileTxt.setEncoding("UTF-8");
		logger.addHandler(fileTxt);
		logger.log(Level.INFO, "\n\n---------- Starting program ----------\n\nRunning version " + Main.VERSION + "\n");
		if(resetedLog)
			logger.log(Level.INFO, "\nLog file reseted, previous was over 2.5MB\n");
		config = new Configuration();
		locale = Language.getLanguageByID(config.getString(Configuration.LOCALE, Language.DEFAULT.getID())).getLocale();
		logger.log(Level.INFO, "Opening resource bundle...");
		resourceBundle = ResourceBundle.getBundle("resources/lang/lang", locale);
		if(!isModeSet(args, "nosocket"))
			try
			{
				setSocket(new ServerSocket(10854, 0, InetAddress.getByAddress(new byte[] {127, 0, 0, 1})));
			}
			catch(BindException e)
			{
				JOptionPane.showMessageDialog(null, resourceBundle.getString("startup_already_running"), resourceBundle.getString("startup_already_running_title"), JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
			catch(IOException e)
			{
				logger.log(Level.SEVERE, "Unexpected error", e);
				System.exit(2);
			}
		logger.log(Level.INFO, "Loading icons...");
		int iconSize = 10;
		icons = new ArrayList<Image>();
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon16.png")));
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon32.png")));
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon64.png")));
		avatarDefaultImage = ImageIO.read(Main.class.getClassLoader().getResource("resources/images/avatar.png"));
		iconChangelogAdd = new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/chanhelogAdd.png")), iconSize, iconSize));
		iconChangelogRemove = new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/chanhelogRemove.png")), iconSize, iconSize));
		iconChangelogModify = new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/chanhelogModify.png")), iconSize, iconSize));
		reloadFont();
		setLookAndFeel();
		int currentStep = 0;
		boolean openChangelog = false;
		startup = new StartupFrame(5);
		startup.setStartupText(currentStep++, resourceBundle.getString("startup_fecth_updates"));
		int result = isModeSet(args, "noupdate") ? Updater.NOUPDATE : Updater.update(startup);
		if(result != Updater.UPDATEDDEV && result != Updater.UPDATEDPUBLIC)
			try
			{
				startup.setStartupText(currentStep++, resourceBundle.getString("startup_getting_api_key"));
				String tempApiKey = config.getString(Configuration.APIKEY, "");
				if(tempApiKey.equals(""))
				{
					final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
					if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
						try
						{
							desktop.browse(new URL("https://osu.ppy.sh/p/api").toURI());
						}
						catch(final Exception e)
						{
							e.printStackTrace();
						}
					tempApiKey = JOptionPane.showInputDialog(null, resourceBundle.getString("startup_ask_api_key"), resourceBundle.getString("startup_ask_api_key_title"), JOptionPane.INFORMATION_MESSAGE);
				}
				logger.log(Level.INFO, "Verifying API key...");
				startup.setStartupText(currentStep++, resourceBundle.getString("startup_verify_api_key"));
				if(!isModeSet(args, "noapi") && !verifyApiKey(tempApiKey))
				{
					logger.log(Level.WARNING, "Wrong API key!");
					JOptionPane.showMessageDialog(null, resourceBundle.getString("startup_wrong_api_key"), resourceBundle.getString("startup_wrong_api_key_title"), JOptionPane.ERROR_MESSAGE);
					config.deleteVar(Configuration.APIKEY);
					System.exit(0);
				}
				config.writeVar(Configuration.APIKEY, tempApiKey);
				API_KEY = tempApiKey;
				byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
				for(int k = 0; k < mac.length; k++)
					UUID += String.format("%02X%s", mac[k], k < mac.length - 1 ? "-" : "");
				SystemTrayOsuStats.init();
				startup.setStartupText(currentStep++, resourceBundle.getString("startup_database_connect"));
				initSQL();
				reloadLanguagesNames();
				numberTrackedStatsToKeep = config.getInt(Configuration.STATSTOKEEP, 0);
				logger.log(Level.INFO, "Launching interface...");
				startup.setStartupText(currentStep++, resourceBundle.getString("startup_construct_frame"));
				backColor = new Color(240, 236, 250);
				searchBarColor = Color.WHITE;
				noticeColor = Color.WHITE;
				noticeBorderColor = new Color(221, 221, 221);
				noticeBorder = BorderFactory.createLineBorder(noticeBorderColor);
				mainFrame = new MainFrame(config.getInt(Configuration.LASTMODE, 0));
				if(isNewVersion(config.getString(Configuration.LASTVERSION, Main.VERSION)))
					openChangelog = true;
				config.writeVar(Configuration.LASTVERSION, Main.VERSION);
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		startup.exit();
		if(openChangelog)
			getChangelogFrame(mainFrame);
	}

	public static void initSQL() throws SQLException
	{
		sql = new SQLManager("osuuserinfo.csox69ljfp6h.eu-west-1.rds.amazonaws.com", 3306, "OsuUserInfo", MYSQLUSER, MYSQLPASS);
	}

	/**
	 * Used to know if the current version is a beta.
	 *
	 * @return True if this is a beta, false if not.
	 */
	public static boolean isCurrentVersionBeta()
	{
		return Main.VERSION.contains("b");
	}

	/**
	 * Used to know is a user is tracked.
	 *
	 * @param user The user to know if he's tracked.
	 * @return True if the user is tracked, false if not.
	 */
	public static boolean isUserTracked(String user)
	{
		return getTrackedUsers().contains(user);
	}

	/**
	 * Used to create a new frame.
	 *
	 * @throws IOException If there were an error when creating the frame.
	 */
	public static void newFrame() throws IOException
	{
		mainFrame.dispose();
		mainFrame = new MainFrame();
	}

	/**
	 * Used to create a new frame.
	 *
	 * @param user The user to show in the frame.
	 * @throws IOException If there were an error when creating the frame.
	 */
	public static void newFrame(String user) throws IOException
	{
		mainFrame.dispose();
		mainFrame = new MainFrame(user);
	}

	/**
	 * Used to create a new frame.
	 *
	 * @param user The user to show in the frame.
	 * @param parent The point where to open the frame.
	 * @throws IOException If there were an error when creating the frame.
	 */
	public static void newFrame(String user, Point parent) throws IOException
	{
		mainFrame.dispose();
		mainFrame = new MainFrame(user, parent);
	}

	/**
	 * Used to create a new frame.
	 *
	 * @param user The user to show in the frame.
	 * @param parent The point where to open the frame.
	 * @param defaultMode The selected mode.
	 * @throws IOException If there were an error when creating the frame.
	 */
	public static void newFrame(String user, Point parent, int defaultMode) throws IOException
	{
		mainFrame.dispose();
		mainFrame = new MainFrame(user, parent, defaultMode);
	}

	/**
	 * Used to open the user profile page.
	 *
	 * @param user The user.
	 */
	public static void openUserProfile(User user)
	{
		if(user == null)
			return;
		final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
			try
			{
				if(user.getUsername().equalsIgnoreCase(""))
					return;
				desktop.browse(new URL("https://osu.ppy.sh/u/" + user.getUserID()).toURI());
			}
			catch(final Exception e)
			{
				e.printStackTrace();
			}
	}

	/**
	 * Used to register a font.
	 *
	 * @param name The name of the font. "src/resources/fonts/<name>"
	 * @param size The size of the font.
	 * @param type The type of the font.
	 * @return The registered font.
	 *
	 * @throws FontFormatException If the font cannot be registered.
	 * @throws IOException If the file isn't found.
	 */
	public static Font registerFont(String name, int size, int type) throws FontFormatException, IOException
	{
		if(name == null || name.equals(Fonts.DEFAULT.getName()))
			return new Font("Arial", Font.PLAIN, 12);
		Font font = Font.createFont(Font.TRUETYPE_FONT, Utils.class.getClassLoader().getResource("resources/fonts/" + name).openStream()).deriveFont(type, size);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		return font;
	}

	/**
	 * Used to reload the resource bundle with a new locale.
	 *
	 * @param language The locale to set.
	 * @throws IOException
	 */
	public static void reloadResourceBundleWithLocale(Language language) throws IOException
	{
		resourceBundle.clearCache();
		locale = language.getLocale();
		resourceBundle = ResourceBundle.getBundle("resources/lang/lang", locale);
		reloadLanguagesNames();
		reloadFont();
	}

	/**
	 * Used to resize an image.
	 *
	 * @param image The image to resize.
	 * @param size The dimension of the image.
	 * @return The resized image.
	 */
	public static BufferedImage resizeBufferedImage(BufferedImage image, Dimension size)
	{
		return resizeBufferedImage(image, (float) size.getWidth(), (float) size.getHeight());
	}

	/**
	 * Used to resize an image.
	 *
	 * @param image The image to resize.
	 * @param width The maximum width.
	 * @param height The maximum height.
	 * @return The resized image.
	 */
	public static BufferedImage resizeBufferedImage(BufferedImage image, float width, float height)
	{
		if(image == null)
			return image;
		int baseWidth = image.getWidth(), baseHeight = image.getHeight();
		float ratio = baseWidth > baseHeight ? width / baseWidth : height / baseHeight;
		Image tmp = image.getScaledInstance((int) (ratio * baseWidth), (int) (ratio * baseHeight), BufferedImage.SCALE_SMOOTH);
		BufferedImage buffered = new BufferedImage((int) (ratio * baseWidth), (int) (ratio * baseHeight), BufferedImage.TYPE_INT_ARGB);
		buffered.getGraphics().drawImage(tmp, 0, 0, null);
		return buffered;
	}

	/**
	 * Used to round a number.
	 *
	 * @param value The value to round.
	 * @param places The number of digits.
	 * @return The rounded number.
	 */
	public static double round(double value, int places)
	{
		if(places < 0)
			throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	/**
	 * Used to save a buffered image to a file.
	 *
	 * @param image the image to save.
	 * @param file The file to create.
	 * @throws IOException
	 */
	public static void saveImage(BufferedImage image, File file) throws IOException
	{
		if(image == null)
			return;
		if(file.exists())
		{
			JOptionPane.showMessageDialog(mainFrame, MessageFormat.format(resourceBundle.getString("save_error"), file.getName()), resourceBundle.getString("save_error_title"), JOptionPane.ERROR_MESSAGE);
			return;
		}
		file.mkdirs();
		ImageIO.write(image, "png", file);
	}

	/**
	 * Used to send a POST request.
	 *
	 * @param type The type of the request.
	 * @param key The API key.
	 * @param user The user to get the stats.
	 * @param selectedMode The mode to get the stats.
	 * @return The JSON response.
	 *
	 * @throws Exception If something went wrong.
	 */
	public synchronized static String sendPost(String type, String key, String user, int selectedMode) throws Exception
	{
		Utils.logger.log(Level.INFO, "Sending post request...");
		String urlParameters = "k=" + key + "&u=" + user + "&m=" + selectedMode + "&type=string&event_days=1";
		URL url = new URL("https://osu.ppy.sh/api/" + type + "?" + urlParameters);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		connection.setRequestProperty("User-Agent", "Mozilla/5.0");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("charset", "utf-8");
		connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
		BufferedReader inputSream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while((inputLine = inputSream.readLine()) != null)
			response.append(inputLine);
		inputSream.close();
		response.deleteCharAt(0);
		response.deleteCharAt(response.length() - 1);
		return response.toString();
	}

	/**
	 * Used to set the socket.
	 *
	 * @param socket The socket to set.
	 */
	public static void setSocket(ServerSocket socket)
	{
		Utils.socket = socket;
	}

	/**
	 * Used to update the status of the thread updater.
	 *
	 * @param state The status of the thread.
	 */
	public static void setThreadUpdater(boolean state)
	{
		if(state)
		{
			if(threadUpdater == null)
				threadUpdater = new TaskUpdater();
		}
		else if(threadUpdater != null)
		{
			threadUpdater.stop();
			threadUpdater = null;
		}
	}

	/**
	 * Used to set the tracked users.
	 *
	 * @param users The tracked users.
	 */
	public static void setTrackedUser(ArrayList<String> users)
	{
		StringBuilder sb = new StringBuilder();
		for(String user : users)
			if(!user.equals(""))
				sb.append(user).append(",");
		sb.deleteCharAt(sb.length() - 1);
		config.writeVar(Configuration.TRACKEDUSERS, sb.toString());
	}

	/**
	 * Used to track a new user.
	 *
	 * @param user The user to track.
	 *
	 * @throws IOException If there were an error serializing the user.
	 */
	public static void trackNewUser(User user) throws IOException
	{
		Utils.logger.log(Level.INFO, "Trcking user " + user.getUsername());
		ArrayList<String> users = Utils.getTrackedUsers();
		users.add(user.getUsername());
		user.serialize(new File(Configuration.appData, user.getUsername()));
		mainFrame.addTrackedUser(user);
		setTrackedUser(users);
	}

	/**
	 * Used to untrack a user.
	 *
	 * @param user The user to untrack.
	 */
	public static void unTrackUser(User user)
	{
		Utils.logger.log(Level.INFO, "Untrcking user " + user.getUsername());
		ArrayList<String> users = Utils.getTrackedUsers();
		users.remove(user.getUsername());
		new File(Configuration.appData, user.getUsername()).delete();
		mainFrame.removeTrackedUser(user);
		Utils.setTrackedUser(users);
	}

	/**
	 * Used to get a random colour.
	 *
	 * @return A random colour.
	 */
	private static Color getRandomColor()
	{
		Color[] colors = new Color[] {Color.BLACK, Color.BLUE, Color.GRAY, Color.RED, Color.DARK_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK};
		return colors[new Random().nextInt(colors.length)];
	}

	/**
	 * Used to know is an argument is set when launching the program.
	 *
	 * @param args The arguments sets.
	 * @param mode The mode to verify if is activated.
	 * @return True is the mode is activated, false if not.
	 */
	private static boolean isModeSet(String[] args, String mode)
	{
		for(String s : args)
			if(s.equalsIgnoreCase(mode))
				return true;
		return false;
	}

	/**
	 * Used to know if this is a new version.
	 *
	 * @param lastVersion The last version.
	 * @return True if it's a new version, false if not.
	 */
	private static boolean isNewVersion(String lastVersion)
	{
		return !Main.VERSION.equals(lastVersion);
	}

	/**
	 * Used to know if there is enough time between each POST requests (1s).
	 *
	 * @return True if it's good, false if not.
	 */
	private static boolean isValidTime()
	{
		return new Date().getTime() - lastPost.getTime() > 1000;
	}

	/**
	 * Used to know if the username is valid.
	 *
	 * @param username The username to verify.
	 * @return True if it's valid, false if not.
	 */
	private static boolean isValidUser(String username)
	{
		return username.length() > 1;
	}

	private static void reloadFont() throws IOException
	{
		try
		{
			Fonts f = Fonts.getFontsByName(Utils.config.getString(Configuration.FONT, Fonts.DEFAULT.getName()));
			fontMain = registerFont(f.getFileName(), f.getSize(), Font.PLAIN);
		}
		catch(FontFormatException e)
		{
			fontMain = new Font("Arial", Font.PLAIN, 12);
		}// TODO font
	}

	private static void reloadLanguagesNames()
	{
		Language.DEFAULT.setName(Utils.resourceBundle.getString("system_language"));
		Language.ENGLISH.setName(Utils.resourceBundle.getString("english"));
		Language.FRENCH.setName(Utils.resourceBundle.getString("french"));
		Language.ITALIAN.setName(Utils.resourceBundle.getString("italian"));
		Language.BULGARIAN.setName(Utils.resourceBundle.getString("bulgarian"));
	}

	/**
	 * Used to set a better look and feel to the frames.
	 */
	private static void setLookAndFeel()
	{
		logger.log(Level.INFO, "Setting look and feel...");
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
				if("Nimbus".equals(info.getName()))
				{
					logger.log(Level.INFO, "Nimbus found, using it...");
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			UIManager.put("nimbusOrange", new Color(255, 200, 0));
			UIManager.put("nimbusOrange", new Color(255, 200, 0));
		}
		catch(final Exception exception)
		{
			logger.log(Level.WARNING, "Error loading look and feel", exception);
		}
	}

	/**
	 * Function to know if the given api key is valid or not.
	 *
	 * @param apiKey The api key to test.
	 * @return True if the key is valid, false if not.
	 */
	private static boolean verifyApiKey(String apiKey)
	{
		try
		{
			new JSONObject(sendPost("get_user", apiKey, "peppy", 0));
		}
		catch(IOException exception)
		{
			logger.log(Level.SEVERE, "Connexion error?", exception);
			JOptionPane.showMessageDialog(null, "Couldn't connect to osu.ppy.sh!", "Internet problem", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		catch(Exception exception)
		{
			logger.log(Level.SEVERE, "Error verifyng API Key", exception);
			JOptionPane.showMessageDialog(null, exception.getStackTrace(), "Internet problem", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		logger.log(Level.INFO, "API Key valid");
		return true;
	}
}
