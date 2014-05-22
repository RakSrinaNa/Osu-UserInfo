package fr.mrcraftcod;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import org.json.JSONObject;

public class Main
{
	public final static String APPNAME = "Osu!UserInfo";
	public final static double VERSION = 1.3D;
	public static String API_KEY = "";
	public static Configuration config;
	public static ArrayList<Image> icons;
	public static InterfaceStartup startup;
	public static ResourceBundle resourceBundle;

	public static void main(String[] args) throws IOException
	{
		getPreviousConfigFolder();
		config = new Configuration();
		if(config.getDouble("last_version", -1D) < 1.2D)
			for(File file : Configuration.appData.listFiles())
				if(file.getName() != config.getConfigFile().getName())
					file.delete();
		resourceBundle = ResourceBundle.getBundle("resources/lang/lang", Locale.getDefault());
		icons = new ArrayList<Image>();
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon16.png")));
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon32.png")));
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon64.png")));
		setLookAndFeel();
		startup = new InterfaceStartup(3);
		config.writeVar("last_version", VERSION);
		try
		{
			startup.setStartupText(resourceBundle.getString("startup_getting_api_key"));
			String temp = config.getVar("api_key");
			if(temp.equals(""))
				temp = JOptionPane.showInputDialog(null, resourceBundle.getString("startup_ask_api_key"), resourceBundle.getString("startup_ask_api_key_title"), JOptionPane.INFORMATION_MESSAGE);
			startup.setStartupText(resourceBundle.getString("startup_verify_api_key"));
			if(!verifyApiKey(temp))
			{
				JOptionPane.showMessageDialog(null, resourceBundle.getString("startup_wrong_api_key"), resourceBundle.getString("startup_wrong_api_key_title"), JOptionPane.ERROR_MESSAGE);
				config.deleteVar("api_key");
				System.exit(0);
			}
			config.writeVar("api_key", temp);
			API_KEY = temp;
			SystemTrayOsuStats.init();
			new Interface();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		startup.exit();
	}

	private static void getPreviousConfigFolder()
	{
		if(new File(System.getenv("APPDATA"), "Osu!Stats").exists())
			try
			{
				new File(System.getenv("APPDATA"), "Osu!Stats").renameTo(new File(System.getenv("APPDATA"), Main.APPNAME));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}

	private static boolean verifyApiKey(String temp)
	{
		try
		{
			new JSONObject(Interface.sendPost(temp, "peppy", 0));
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null, "Couldn't connect to osu.ppy.sh!", "Internet problem", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

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
