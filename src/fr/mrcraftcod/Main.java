package fr.mrcraftcod;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

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
		icons = new ArrayList<Image>();
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon16.png")));
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon32.png")));
		icons.add(ImageIO.read(Main.class.getClassLoader().getResource("resources/icons/icon64.png")));
		setLookAndFeel();
		new Interface();
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
		}
		catch(final Exception exception)
		{}
	}
}
