package fr.mrcraftcod;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import org.json.JSONObject;
import fr.mrcraftcod.interfaces.Interface;
import fr.mrcraftcod.interfaces.InterfaceStartup;
import fr.mrcraftcod.objects.SystemTrayOsuStats;
import fr.mrcraftcod.utils.Configuration;
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
 * @version 1.4
 */
public class Main
{
	public final static String APPNAME = "Osu!UserInfo";
	public final static String VERSION = "1.5b3";
	public static String API_KEY = "";
	public static int numberTrackedStatsToKeep;
	public static Configuration config;
	public static ArrayList<Image> icons;
	public static InterfaceStartup startup;
	public static ResourceBundle resourceBundle;

	/**
	 * Start the program.
	 * 
	 * @param args Not used.
	 * @throws IOException If there were an error during startup.
	 */
	public static void main(String[] args) throws IOException
	{
		config = new Configuration();
		resourceBundle = ResourceBundle.getBundle("resources/lang/lang");
		icons = new ArrayList<Image>();
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon16.png")));
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon32.png")));
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon64.png")));
		setLookAndFeel();
		int currentStep = 0;
		startup = new InterfaceStartup(4);
		config.writeVar("last_version", VERSION);
		Main.startup.setStartupText(currentStep++, Main.resourceBundle.getString("startup_fecth_updates"));
		int result = Updater.update(startup.getFrame());
		if(result != Updater.UPDATEDDEV && result != Updater.UPDATEDPUBLIC)
		{
			try
			{
				startup.setStartupText(currentStep++, resourceBundle.getString("startup_getting_api_key"));
				String tempApiKey = config.getString("api_key", "");
				if(tempApiKey.equals(""))
				{
					tempApiKey = JOptionPane.showInputDialog(null, resourceBundle.getString("startup_ask_api_key"), resourceBundle.getString("startup_ask_api_key_title"), JOptionPane.INFORMATION_MESSAGE);
				}
				startup.setStartupText(currentStep++, resourceBundle.getString("startup_verify_api_key"));
				if(!verifyApiKey(tempApiKey))
				{
					JOptionPane.showMessageDialog(null, resourceBundle.getString("startup_wrong_api_key"), resourceBundle.getString("startup_wrong_api_key_title"), JOptionPane.ERROR_MESSAGE);
					config.deleteVar("api_key");
					System.exit(0);
				}
				config.writeVar("api_key", tempApiKey);
				API_KEY = tempApiKey;
				SystemTrayOsuStats.init();
				numberTrackedStatsToKeep = config.getInt("statsToKeep", 10);
				new Interface();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}
		startup.exit();
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
			JOptionPane.showMessageDialog(null, "Couldn't connect to osu.ppy.sh!", "Internet problem", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Used to set a better look and feel to the frames.
	 */
	private static void setLookAndFeel()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			{
				if("Nimbus".equals(info.getName()))
				{
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
			UIManager.put("nimbusOrange", new Color(255, 200, 0));
		}
		catch(final Exception exception)
		{}
	}
}
