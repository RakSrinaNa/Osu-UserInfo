package fr.mrcraftcod.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import org.json.JSONObject;
import fr.mrcraftcod.Main;
import fr.mrcraftcod.interfaces.Interface;
import fr.mrcraftcod.interfaces.InterfaceAbout;
import fr.mrcraftcod.interfaces.InterfaceSettings;
import fr.mrcraftcod.interfaces.InterfaceStartup;
import fr.mrcraftcod.objects.Stats;
import fr.mrcraftcod.objects.SystemTrayOsuStats;
import fr.mrcraftcod.objects.User;

public class Utils
{
	public enum Mods
	{
		None(0), NoFail(1), Easy(2), NoVideo(4), Hidden(8), HardRock(16), SuddenDeath(32), DoubleTime(64), Relax(128), HalfTime(256), Nightcore(512), Flashlight(1024), Autoplay(2048), SpunOut(4096), Relax2(8192), Perfect(16384), Key4(32768), Key5(5536), Key6(131072), Key7(262144), Key8(524288), keyMod(Key4.getKey() | Key5.getKey() | Key6.getKey() | Key7.getKey() | Key8.getKey()), FadeIn(1048576), Random(2097152), LastMod(4194304), FreeModAllowed(NoFail.getKey() | Easy.getKey() | Hidden.getKey() | HardRock.getKey() | SuddenDeath.getKey() | Flashlight.getKey() | FadeIn.getKey() | Relax.getKey() | Relax2.getKey() | SpunOut.getKey() | keyMod.getKey());
		private long key;

		Mods(long key)
		{
			this.key = key;
		}

		private long getKey()
		{
			return this.key;
		}
	}

	private final static String logFileName = "log.log";
	private static ServerSocket socket;
	private static TaskUpdater threadUpdater;
	public static String API_KEY = "";
	public static int numberTrackedStatsToKeep;
	public static Configuration config;
	public static ArrayList<Image> icons;
	public static InterfaceStartup startup;
	public static Interface mainFrame;
	public static ResourceBundle resourceBundle;
	public static Logger logger;
	public static Color backColor, searchBarColor, noticeColor, noticeBorderColor;
	public static Border noticeBorder;
	public static Font fontMain;
	public static Date lastPost = new Date(0);
	public static User lastUser = new User();
	public static Stats lastStats = new Stats();
	public static InterfaceAbout aboutFrame;
	public static InterfaceSettings configFrame;

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
		{
			e.printStackTrace();
		}
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

	public static int getLevel(double level)
	{
		return (int) level;
	}

	public static String getLineCodeFromLink(final String link, final String... gets) throws Exception
	{
		final String[] lines = getHTMLCode(link);
		for(final String get : gets)
			for(final String tempLine : lines)
				if(tempLine.contains(get))
					return tempLine;
		throw new Exception("Cannot get code from link");
	}

	private static Locale getLocaleByName(String string)
	{
		if(string == null)
			return Locale.getDefault();
		switch(string)
		{
			case "fr":
				return Locale.FRENCH;
			case "it":
				return Locale.ITALIAN;
			case "en":
				return Locale.ENGLISH;
			default:
				return Locale.getDefault();
		}
	}

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

	public static double getProgressLevel(double level)
	{
		return level - (int) level;
	}

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

	public static ServerSocket getSocket()
	{
		return socket;
	}

	public static ArrayList<String> getTrackedUsers()
	{
		ArrayList<String> trackedList = new ArrayList<String>();
		String tracked = Utils.config.getString("tracked_users", "");
		for(String user : tracked.split(","))
			trackedList.add(user);
		return trackedList;
	}

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
		logger.log(Level.INFO, "Opening resource bundle...");
		resourceBundle = ResourceBundle.getBundle("resources/lang/lang", getLocaleByName(config.getString("locale", null)));
		if(!isModeSet(args, "test"))
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
		icons = new ArrayList<Image>();
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon16.png")));
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon32.png")));
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon64.png")));
		fontMain = new Font("Arial", Font.PLAIN, 12); // TODO font
		setLookAndFeel();
		int currentStep = 0;
		startup = new InterfaceStartup(4);
		config.writeVar("last_version", Main.VERSION);
		startup.setStartupText(currentStep++, resourceBundle.getString("startup_fecth_updates"));
		int result = isModeSet(args, "ignoreupdate") ? Updater.NOUPDATE : Updater.update(startup.getFrame());
		if(result != Updater.UPDATEDDEV && result != Updater.UPDATEDPUBLIC)
			try
		{
				startup.setStartupText(currentStep++, resourceBundle.getString("startup_getting_api_key"));
				String tempApiKey = config.getString("api_key", "");
				if(tempApiKey.equals(""))
					tempApiKey = JOptionPane.showInputDialog(null, resourceBundle.getString("startup_ask_api_key"), resourceBundle.getString("startup_ask_api_key_title"), JOptionPane.INFORMATION_MESSAGE);
				logger.log(Level.INFO, "Verifying API key...");
				startup.setStartupText(currentStep++, resourceBundle.getString("startup_verify_api_key"));
				if(!isModeSet(args, "ignoreverifapi") && !verifyApiKey(tempApiKey))
				{
					logger.log(Level.WARNING, "Wrong API key!");
					JOptionPane.showMessageDialog(null, resourceBundle.getString("startup_wrong_api_key"), resourceBundle.getString("startup_wrong_api_key_title"), JOptionPane.ERROR_MESSAGE);
					config.deleteVar("api_key");
					System.exit(0);
				}
				config.writeVar("api_key", tempApiKey);
				API_KEY = tempApiKey;
				SystemTrayOsuStats.init();
				numberTrackedStatsToKeep = config.getInt("statsToKeep", 10);
				logger.log(Level.INFO, "Launching interface...");
				startup.setStartupText(currentStep++, resourceBundle.getString("startup_construct_frame"));
				backColor = new Color(240, 236, 250);
				searchBarColor = Color.WHITE;
				noticeColor = Color.WHITE;
				noticeBorderColor = new Color(221, 221, 221);
				noticeBorder = BorderFactory.createLineBorder(noticeBorderColor);
				mainFrame = new Interface();
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		startup.exit();
	}

	private static boolean isModeSet(String[] args, String mode)
	{
		for(String s : args)
			if(s.equalsIgnoreCase(mode))
				return true;
		return false;
	}

	public static boolean isUserTracked(String user)
	{
		return getTrackedUsers().contains(user);
	}

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

	public static double round(double value, int places)
	{
		if(places < 0)
			throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

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

	public static void setSocket(ServerSocket socket)
	{
		Utils.socket = socket;
	}

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

	public static void setTrackedUser(ArrayList<String> users)
	{
		StringBuilder sb = new StringBuilder();
		for(String user : users)
			if(!user.equals(""))
				sb.append(user).append(",");
		sb.deleteCharAt(sb.length() - 1);
		config.writeVar("tracked_users", sb.toString());
	}

	/**
	 * Function to know if the given api key is valid or not.
	 *
	 * @param apiKey The api key to test.
	 * @return A boolean representing the validity of the key.
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
