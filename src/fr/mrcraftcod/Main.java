package fr.mrcraftcod;

import java.awt.Color;
import java.awt.Image;
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
	public final static String APPNAME = "Osu!Stats";
	public static String API_KEY = "";
	public static Configuration config;
	public static ArrayList<Image> icons;
	public static InterfaceStartup startup;
	public static ResourceBundle resourceBundle;

	public static void main(String[] args) throws IOException
	{
		config = new Configuration();
		resourceBundle = ResourceBundle.getBundle("resources/lang/lang", Locale.getDefault());
		icons = new ArrayList<Image>();
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon16.png")));
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon32.png")));
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon64.png")));
		setLookAndFeel();
		startup = new InterfaceStartup(3);
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
		new Interface();
		startup.exit();
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
