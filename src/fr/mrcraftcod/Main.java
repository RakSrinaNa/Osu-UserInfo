package fr.mrcraftcod;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
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
import fr.mrcraftcod.interfaces.Interface;
import fr.mrcraftcod.interfaces.InterfaceStartup;
import fr.mrcraftcod.objects.SystemTrayOsuStats;
import fr.mrcraftcod.utils.Configuration;
import fr.mrcraftcod.utils.LogFormatter;
import fr.mrcraftcod.utils.ThreadUpdater;
import fr.mrcraftcod.utils.Updater;

/**
 * <h1>Osu!UserInfo</h1>
 * <p>
 * <a href="https://osu.ppy.sh/forum/p/3094583">Osu!UserInfo</a> is a simple program that will give you information (and track them) about a Osu! user!
 * </p>
 * <h2>Informations given/tracked</h2>
 * <p>
 * <ul>
 * <li>
 * Play count</li>
 * <li>
 * Scores</li>
 * <li>
 * PP</li>
 * <li>
 * Accuracy</li>
 * <li>
 * Country</li>
 * <li>
 * Rank</li>
 * <li>
 * Total hits</li>
 * <li>
 * Number of 300s, 100s, 50s</li>
 * <li>
 * Number of SS, S, A</li>
 * </ul>
 * </p>
 * <h2>How to help that project?</h2>
 * <p>
 * If you find any bugs, please <a href="https://bitbucket.org/MrCraftCod/osuuserinfo/issues">report them here</a> <br>
 * If you want, you can translate this project. <a href="https://bitbucket.org/MrCraftCod/osuuserinfo/fork">Fork the repository</a> and add your language file in src/resources/lang/
 * </p>
 * 
 * @author MrCraftCod
 * @version 1.6
 */
public class Main
{
	public final static String APPNAME = "Osu!UserInfo";
	public final static String VERSION = "1.6b4";
	private final static String logFileName = "log.log";
	public static String API_KEY = "";
	public static int numberTrackedStatsToKeep;
	public static Configuration config;
	public static ArrayList<Image> icons;
	public static InterfaceStartup startup;
	public static Interface frame;
	public static ResourceBundle resourceBundle;
	public static Logger logger;
	public static boolean testMode = true;
	public static Color backColor, searchBarColor, noticeColor, noticeBorderColor;
	public static Border noticeBorder;
	private static ServerSocket socket;
	private static ThreadUpdater threadUpdater;

	/**
	 * Start the program.
	 * 
	 * @param args Not used.
	 * @throws IOException If there were an error during startup.
	 */
	public static void main(String[] args) throws IOException
	{
		logger = Logger.getLogger(APPNAME);
		if(testMode)
			logger.setLevel(Level.FINEST);
		else
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
		final FileHandler fileTxt = new FileHandler(logFile.getAbsolutePath(), true);
		fileTxt.setFormatter(new LogFormatter());
		fileTxt.setEncoding("UTF-8");
		logger.addHandler(fileTxt);
		logger.log(Level.INFO, "\n\n---------- Starting program ----------\n\nRunning version " + VERSION + "\n");
		if(resetedLog)
			logger.log(Level.INFO, "\nLog file reseted, previous was over 2.5MB\n");
		config = new Configuration();
		Main.logger.log(Level.INFO, "Opening resource bundle...");
		resourceBundle = ResourceBundle.getBundle("resources/lang/lang", getLocale(config.getString("locale", null)));
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
		Main.logger.log(Level.INFO, "Loading icons...");
		icons = new ArrayList<Image>();
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon16.png")));
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon32.png")));
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon64.png")));
		setLookAndFeel();
		int currentStep = 0;
		startup = new InterfaceStartup(4);
		config.writeVar("last_version", VERSION);
		startup.setStartupText(currentStep++, Main.resourceBundle.getString("startup_fecth_updates"));
		int result = Updater.update(startup.getFrame());
		if(result != Updater.UPDATEDDEV && result != Updater.UPDATEDPUBLIC)
		{
			try
			{
				startup.setStartupText(currentStep++, resourceBundle.getString("startup_getting_api_key"));
				String tempApiKey = config.getString("api_key", "");
				if(tempApiKey.equals(""))
					tempApiKey = JOptionPane.showInputDialog(null, resourceBundle.getString("startup_ask_api_key"), resourceBundle.getString("startup_ask_api_key_title"), JOptionPane.INFORMATION_MESSAGE);
				Main.logger.log(Level.INFO, "Verifying API key...");
				startup.setStartupText(currentStep++, resourceBundle.getString("startup_verify_api_key"));
				if(!verifyApiKey(tempApiKey))
				{
					Main.logger.log(Level.WARNING, "Wrong API key!");
					JOptionPane.showMessageDialog(null, resourceBundle.getString("startup_wrong_api_key"), resourceBundle.getString("startup_wrong_api_key_title"), JOptionPane.ERROR_MESSAGE);
					config.deleteVar("api_key");
					System.exit(0);
				}
				config.writeVar("api_key", tempApiKey);
				API_KEY = tempApiKey;
				SystemTrayOsuStats.init();
				numberTrackedStatsToKeep = config.getInt("statsToKeep", 10);
				Main.logger.log(Level.INFO, "Launching interface...");
				startup.setStartupText(currentStep++, resourceBundle.getString("startup_construct_frame"));
				backColor = new Color(240, 236, 250);
				searchBarColor = Color.WHITE;
				noticeColor = Color.WHITE;
				noticeBorderColor = new Color(221, 221, 221);
				noticeBorder = BorderFactory.createLineBorder(noticeBorderColor);
				frame = new Interface();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}
		startup.exit();
	}

	private static Locale getLocale(String string)
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

	public static void setThreadUpdater(boolean state)
	{
		if(state)
		{
			if(threadUpdater == null)
				threadUpdater = new ThreadUpdater();
		}
		else if(threadUpdater != null)
		{
			threadUpdater.stop();
			threadUpdater = null;
		}
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
			new JSONObject(Interface.sendPost("get_user", apiKey, "peppy", 0));
		}
		catch(IOException exception)
		{
			Main.logger.log(Level.SEVERE, "Connexion error?", exception);
			JOptionPane.showMessageDialog(null, "Couldn't connect to osu.ppy.sh!", "Internet problem", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		catch(Exception exception)
		{
			Main.logger.log(Level.SEVERE, "Error verifyng API Key", exception);
			JOptionPane.showMessageDialog(null, exception.getStackTrace(), "Internet problem", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		Main.logger.log(Level.INFO, "API Key valid");
		return true;
	}

	/**
	 * Used to set a better look and feel to the frames.
	 */
	private static void setLookAndFeel()
	{
		Main.logger.log(Level.INFO, "Setting look and feel...");
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			{
				if("Nimbus".equals(info.getName()))
				{
					Main.logger.log(Level.INFO, "Nimbus found, using it...");
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
			UIManager.put("nimbusOrange", new Color(255, 200, 0));
			UIManager.put("nimbusOrange", new Color(255, 200, 0));
		}
		catch(final Exception exception)
		{
			Main.logger.log(Level.WARNING, "Error loading look and feel", exception);
		}
	}

	public static ServerSocket getSocket()
	{
		return socket;
	}

	public static void setSocket(ServerSocket socket)
	{
		Main.socket = socket;
	}

	public static void exit()
	{
		try
		{
			socket.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		socket = null;
	}
}
