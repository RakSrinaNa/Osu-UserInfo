package fr.mrcraftcod;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import org.json.JSONObject;

public class Main
{
	public final static String APPNAME = "Osu!Rank", API_KEY = "";
	public static Logger logger;
	public static Configuration config;
	public static ResourceBundle resourceBundle;
	public static ArrayList<Image> icons;

	public static void main(String[] args) throws IOException
	{
		logger = Logger.getLogger(APPNAME);
		config = new Configuration();
		resourceBundle = ResourceBundle.getBundle("resources/lang/lang", Locale.getDefault());
		if(temp.equals(""))
			temp = JOptionPane.showInputDialog(null, "Enter your API key:", "API Key needed", JOptionPane.INFORMATION_MESSAGE);
		if(!verifyApiKey(temp))
		{
			JOptionPane.showMessageDialog(null, "Wrong API key!", "API key not valid", JOptionPane.ERROR_MESSAGE);
			config.deleteVar("api_key");
			System.exit(0);
		}
		config.writeVar("api_key", temp);
		API_KEY = temp;
		icons = new ArrayList<Image>();
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon16.png")));
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon32.png")));
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon64.png")));
		setLookAndFeel();
		new Interface();
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
