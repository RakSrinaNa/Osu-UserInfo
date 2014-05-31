package fr.mrcraftcod.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.logging.Level;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import fr.mrcraftcod.Main;

/**
 * Used to fetch and download new versions.
 * 
 * @author MrCraftCod
 */
public class Updater
{
	public final static int DEVELOPER = 1, PUBLIC = 2, PUBLICFDEV = 3, NOUPDATE = 0, UPDATEDDEV = 1, UPDATEDPUBLIC = 2, UPDATEERROR = 3;
	private final static String DEVELOPERTAG = "DEVELOPER", PUBLICTAG = "PUBLIC", LINKXML = "https://bitbucket.org/api/1.0/repositories/mrcraftcod/osuuserinfo/raw/master/Infos/lastVersion.xml", LINKPUBLIC = "https://bitbucket.org/api/1.0/repositories/mrcraftcod/osuuserinfo/raw/master/Infos/Jars/Public/Osu!UserInfo.jar", LINKDEV = "https://bitbucket.org/api/1.0/repositories/mrcraftcod/osuuserinfo/raw/master/Infos/Jars/Developer/Osu!UserInfo.jar";
	private static HashMap<String, String> versionsUTD;
	private static JFrame context;

	/**
	 * Used to get the XML file containing the versions up to date.
	 * 
	 * @param updateFile The file where to save the XML.
	 * @param link The link of the XML.
	 */
	private static void getLastVersionBitbucket(File updateFile, String link)
	{
		Main.logger.log(Level.INFO, "Getting last version...");
		HttpURLConnection request = null;
		ReadableByteChannel rbc = null;
		FileOutputStream fos = null;
		try
		{
			URL url = new URL(link);
			request = (HttpURLConnection) url.openConnection();
			request.connect();
			request.setReadTimeout(60000);
			request.setConnectTimeout(60000);
			rbc = Channels.newChannel(request.getInputStream());
			fos = new FileOutputStream(updateFile);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		}
		catch(IOException e)
		{
			Main.logger.log(Level.WARNING, "Error writting XML file", e);
		}
		try
		{
			request.disconnect();
		}
		catch(Exception e)
		{}
		try
		{
			fos.close();
		}
		catch(Exception e)
		{}
		try
		{
			rbc.close();
		}
		catch(Exception e)
		{}
	}

	/**
	 * Used to download to wanted JAR.
	 * 
	 * @param newFile The file where to save the new JAR.
	 * @param link The link of the JAR file.
	 * @return True if the file has been downloaded, false if there were an error.
	 */
	public static boolean getLastJAR(File newFile, String link)
	{
		Main.logger.log(Level.INFO, "Getting last JAR...");
		Main.startup.addStartupText(Main.resourceBundle.getString("downloading"));
		boolean result = false;
		HttpURLConnection request = null;
		ReadableByteChannel rbc = null;
		FileOutputStream fos = null;
		try
		{
			URL url = new URL(link);
			request = (HttpURLConnection) url.openConnection();
			request.connect();
			request.setReadTimeout(60000);
			request.setConnectTimeout(60000);
			rbc = Channels.newChannel(request.getInputStream());
			fos = new FileOutputStream(newFile);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			result = true;
		}
		catch(IOException e)
		{
			Main.logger.log(Level.WARNING, "Error writting JAR file", e);
		}
		try
		{
			request.disconnect();
		}
		catch(Exception e)
		{}
		try
		{
			fos.close();
		}
		catch(Exception e)
		{}
		try
		{
			rbc.close();
		}
		catch(Exception e)
		{}
		return result;
	}

	/**
	 * Used to update the actual version.
	 * 
	 * @param version The type of version to update.
	 * @return Which version the user updated.
	 */
	private static int update(File newFile, int version)
	{
		int reply;
		boolean result = true;
		try
		{
			switch(version)
			{
				case DEVELOPER:
					reply = JOptionPane.showConfirmDialog(context, Main.resourceBundle.getString("new_update_dev") + "\n\n" + Main.resourceBundle.getString("new_update_want_to_update"), Main.resourceBundle.getString("new_update"), JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if(reply == JOptionPane.YES_OPTION)
						result = getLastJAR(new File(".", Main.APPNAME + ".jar"), LINKDEV);
					else
						return NOUPDATE;
					if(!result)
						return UPDATEERROR;
					return UPDATEDDEV;
				case PUBLIC:
					reply = JOptionPane.showConfirmDialog(context, Main.resourceBundle.getString("new_update_public") + "\n\n" + Main.resourceBundle.getString("new_update_want_to_update"), Main.resourceBundle.getString("new_update"), JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if(reply == JOptionPane.YES_OPTION)
						result = getLastJAR(new File(".", Main.APPNAME + ".jar"), LINKPUBLIC);
					else
						return NOUPDATE;
					if(!result)
						return UPDATEERROR;
					return UPDATEDPUBLIC;
				case PUBLICFDEV:
					reply = JOptionPane.showConfirmDialog(context, Main.resourceBundle.getString("new_update_public_dev"), Main.resourceBundle.getString("new_update"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if(reply == JOptionPane.YES_OPTION)
						result = getLastJAR(new File(".", Main.APPNAME + ".jar"), LINKPUBLIC);
					else
						return NOUPDATE;
					if(!result)
						return UPDATEERROR;
					return UPDATEDPUBLIC;
				default:
					return UPDATEERROR;
			}
		}
		catch(Exception e)
		{
			return UPDATEERROR;
		}
	}

	/**
	 * Used to choose the version to download.
	 * 
	 * @return The type of version to download.
	 */
	public static int update(JFrame con)
	{
		Main.logger.log(Level.INFO, "Checking updates...");
		context = con;
		File updateFile = new File(".", "updates.xml");
		File jarFile = new File(".", Main.APPNAME + ".jar");
		getLastVersionBitbucket(updateFile, LINKXML);
		try
		{
			parseVersions(updateFile);
		}
		catch(SAXException | IOException | ParserConfigurationException e)
		{
			updateFile.delete();
			return UPDATEERROR;
		}
		updateFile.delete();
		boolean devMode = Main.config.getBoolean("devMode", false);
		int result = NOUPDATE;
		if(versionsUTD == null)
			result = UPDATEERROR;
		else if(versionsUTD.size() < 1)
			result = UPDATEERROR;
		for(String key : versionsUTD.keySet())
			Main.logger.log(Level.INFO, "Version " + key + " latest is " + versionsUTD.get(key) + ", you are in " + Main.VERSION);
		if(!devMode && Main.VERSION.contains("b"))
			result = update(jarFile, PUBLICFDEV);
		if(devMode && !isDevUpToDate())
			result = update(jarFile, DEVELOPER);
		else if(!isPublicUpToDate(devMode))
			result = update(jarFile, PUBLIC);
		else
			result = NOUPDATE;
		if(result == UPDATEDPUBLIC || result == UPDATEDDEV)
		{
			try
			{
				Main.startup.setPercent(100);
				JOptionPane.showMessageDialog(context, String.format(Main.resourceBundle.getString("update_complete"), "\n" + jarFile.getAbsolutePath() + "\n"));
				String javaHome = System.getProperty("java.home");
				File f = new File(javaHome);
				f = new File(f, "bin");
				f = new File(f, "javaw.exe");
				Runtime.getRuntime().exec(f.getAbsolutePath() + " -jar " + jarFile.getAbsolutePath());
			}
			catch(final IOException e)
			{
				Main.logger.log(Level.SEVERE, "Error launching new version", e);
			}
		}
		return result;
	}

	/**
	 * Used to know if the public version is up to date.
	 * 
	 * @return True if up to date, false if not.
	 */
	private static boolean isPublicUpToDate(boolean devMode)
	{
		try
		{
			String upToDateVersion = versionsUTD.get(PUBLICTAG);
			int actualGlobalVersion = Integer.parseInt(Main.VERSION.split("\\.")[0]);
			int upToDateGlobalVersion = Integer.parseInt(upToDateVersion.split("\\.")[0]);
			int actualSubVersion = -1;
			int upToDateSubVersion = -1;
			String actualSubVersionS = Main.VERSION.split("\\.")[1];
			String upToDateSubVersionS = upToDateVersion.split("\\.")[1];
			if(actualSubVersionS.contains("b"))
				actualSubVersionS = actualSubVersionS.substring(0, actualSubVersionS.indexOf("b"));
			if(upToDateSubVersionS.contains("b"))
				upToDateSubVersionS = upToDateSubVersionS.substring(0, upToDateSubVersionS.indexOf("b"));
			actualSubVersion = Integer.parseInt(actualSubVersionS);
			upToDateSubVersion = Integer.parseInt(upToDateSubVersionS);
			if(actualGlobalVersion < upToDateGlobalVersion)
				return false;
			else if(actualSubVersion < upToDateSubVersion || (devMode && (actualSubVersion <= upToDateSubVersion)))
				return false;
		}
		catch(Exception e)
		{}
		return true;
	}

	/**
	 * NOT CURENTLY USED.
	 * Used to know if the developer version is up to date.
	 * 
	 * @return True if up to date, false if not.
	 */
	private static boolean isDevUpToDate()
	{
		try
		{
			String upToDateVersion = versionsUTD.get(DEVELOPERTAG);
			int actualGlobalVersion = Integer.parseInt(Main.VERSION.split("\\.")[0]);
			int upToDateGlobalVersion = Integer.parseInt(upToDateVersion.split("\\.")[0]);
			int actualSubVersion = -1;
			int upToDateSubVersion = -1;
			String actualSubVersionS = Main.VERSION.split("\\.")[1];
			String upToDateSubVersionS = upToDateVersion.split("\\.")[1];
			if(actualSubVersionS.contains("b"))
				actualSubVersionS = actualSubVersionS.substring(0, actualSubVersionS.indexOf("b"));
			if(upToDateSubVersionS.contains("b"))
				upToDateSubVersionS = upToDateSubVersionS.substring(0, upToDateSubVersionS.indexOf("b"));
			actualSubVersion = Integer.parseInt(actualSubVersionS);
			upToDateSubVersion = Integer.parseInt(upToDateSubVersionS);
			int actualBetaVersion = Main.VERSION.contains("b") ? Integer.parseInt(Main.VERSION.split("b")[1]) : -1;
			int upToDateBetaVersion = upToDateVersion.contains("b") ? Integer.parseInt(upToDateVersion.split("b")[1]) : -1;
			if(actualGlobalVersion < upToDateGlobalVersion)
				return false;
			else if(actualSubVersion < upToDateSubVersion)
				return false;
			if(actualGlobalVersion == upToDateGlobalVersion && actualSubVersion == upToDateSubVersion)
				return true;
			if(actualBetaVersion < upToDateBetaVersion)
				return false;
		}
		catch(Exception e)
		{}
		return true;
	}

	/**
	 * Used to parse an XML file to versionsUTD.
	 * 
	 * @param file The XML file.
	 * 
	 * @throws SAXException
	 * @throws IOException If the file can't be read.
	 * @throws ParserConfigurationException
	 */
	private static void parseVersions(File file) throws SAXException, IOException, ParserConfigurationException
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(file);
		doc.getDocumentElement().normalize();
		NodeList listVersion = doc.getElementsByTagName("version");
		versionsUTD = new HashMap<String, String>();
		for(int s = 0; s < listVersion.getLength(); s++)
		{
			Node versionNode = listVersion.item(s);
			if(versionNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element versionElement = (Element) versionNode;
				NodeList nameList = versionElement.getElementsByTagName("name");
				Element nameElement = (Element) nameList.item(0);
				NodeList textNaList = nameElement.getChildNodes();
				NodeList numberList = versionElement.getElementsByTagName("number");
				Element numberElement = (Element) numberList.item(0);
				NodeList textNuList = numberElement.getChildNodes();
				versionsUTD.put(textNaList.item(0).getNodeValue().trim(), textNuList.item(0).getNodeValue().trim());
			}
		}
	}
}
