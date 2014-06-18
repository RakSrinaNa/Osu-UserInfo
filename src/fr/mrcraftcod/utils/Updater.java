package fr.mrcraftcod.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Date;
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
	 * Used to choose the version to download.
	 *
	 * @return The type of version to download.
	 */
	public static int update(JFrame con)
	{
		Utils.logger.log(Level.INFO, "Checking updates...");
		context = con;
		File updateFile = new File(".", "updates.xml");
		File jarFile = new File(System.getProperty("user.dir"), Main.APPNAME + ".jar");
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
		boolean devMode = Utils.config.getBoolean("devMode", false);
		int result = NOUPDATE;
		if(versionsUTD == null)
			result = UPDATEERROR;
		else if(versionsUTD.size() < 1)
			result = UPDATEERROR;
		for(String key : versionsUTD.keySet())
			Utils.logger.log(Level.INFO, "Version " + key + " latest is " + versionsUTD.get(key) + ", you are in " + Main.VERSION);
		if(!devMode && Main.VERSION.contains("b"))
			result = update(jarFile, PUBLICFDEV);
		if(devMode && !isDevUpToDate())
			result = update(jarFile, DEVELOPER);
		else if(!isPublicUpToDate(devMode))
			result = update(jarFile, PUBLIC);
		else
			result = NOUPDATE;
		if(result == UPDATEDPUBLIC || result == UPDATEDDEV)
			try
			{
				Utils.startup.setPercent(100);
				JOptionPane.showMessageDialog(context, String.format(Utils.resourceBundle.getString("update_complete"), "\n" + jarFile.getAbsolutePath() + "\n"));
				Utils.exit(false);
				String javaHome = System.getProperty("java.home");
				File f = new File(javaHome);
				f = new File(f, "bin");
				f = new File(f, "javaw.exe");
				Runtime.getRuntime().exec(f.getAbsolutePath() + " -jar " + jarFile.getAbsolutePath());
			}
			catch(final IOException e)
			{
				Utils.logger.log(Level.SEVERE, "Error launching new version", e);
			}
		return result;
	}

	/**
	 * Used to download to wanted JAR.
	 *
	 * @param newFile The file where to save the new JAR.
	 * @param link The link of the JAR file.
	 * @return True if the file has been downloaded, false if there were an error.
	 */
	private synchronized static boolean getLastJAR(File file, String link)
	{
		Utils.startup.addStartupText(Utils.resourceBundle.getString("downloading"));
		boolean result = false;
		BufferedInputStream is = null;
		FileOutputStream fos = null;
		BufferedOutputStream bout = null;
		try
		{
			long start = new Date().getTime();
			URL url = new URL(link);
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("charset", "utf-8");
			connection.setReadTimeout(10000);
			connection.setConnectTimeout(10000);
			connection.connect();
			long fileSize = Long.parseLong(connection.getHeaderField("Content-Length"));
			String fileSizeText = Utils.getDownloadSizeText(fileSize);
			if(fileSize < 0)
				return false;
			float readed = 0;
			int byteSize = 1024;
			byte[] data = new byte[byteSize];
			int i = 0;
			try
			{
				is = new BufferedInputStream(connection.getInputStream());
			}
			catch(IOException e)
			{
				is = new BufferedInputStream(connection.getErrorStream());
				String error = "";
				while((i = is.read()) >= 0)
					error += (char) i;
				Utils.logger.log(Level.SEVERE, "" + error);
				return false;
			}
			fos = new FileOutputStream(file);
			bout = new BufferedOutputStream(fos, byteSize);
			float percent;
			while((i = is.read(data, 0, 1024)) >= 0)
			{
				readed += +i;
				bout.write(data, 0, i);
				percent = readed * 100f / fileSize;
				Utils.startup.setBarPercentWithText(percent, Utils.getDownloadSizeText(readed) + " / " + fileSizeText);
			}
			Utils.startup.setBarPercent(100);
			Utils.logger.log(Level.FINEST, "File downloaded in " + (new Date().getTime() - start) / 1000 + " seconds");
			result = true;
		}
		catch(final IOException e)
		{
			Utils.logger.log(Level.SEVERE, "Error when downloading", e);
		}
		try
		{
			if(bout != null)
				bout.close();
		}
		catch(IOException e1)
		{}
		try
		{
			if(fos != null)
				fos.close();
		}
		catch(IOException e1)
		{}
		try
		{
			if(is != null)
				is.close();
		}
		catch(IOException e1)
		{}
		return result;
	}

	/**
	 * Used to get the XML file containing the versions up to date.
	 *
	 * @param updateFile The file where to save the XML.
	 * @param link The link of the XML.
	 */
	private static void getLastVersionBitbucket(File updateFile, String link)
	{
		Utils.logger.log(Level.INFO, "Getting last version...");
		HttpURLConnection request = null;
		ReadableByteChannel rbc = null;
		FileOutputStream fos = null;
		try
		{
			URL url = new URL(link);
			request = (HttpURLConnection) url.openConnection();
			request.setReadTimeout(5000);
			request.setConnectTimeout(5000);
			request.connect();
			rbc = Channels.newChannel(request.getInputStream());
			fos = new FileOutputStream(updateFile);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		}
		catch(IOException e)
		{
			Utils.logger.log(Level.WARNING, "Error writting XML file", e);
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
			if(actualBetaVersion < upToDateBetaVersion)
				return false;
		}
		catch(Exception e)
		{}
		return true;
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
			else if(actualSubVersion < upToDateSubVersion)
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
	 * @throws SAXException If there were an error with the XML file.
	 * @throws IOException If there were an error with the file.
	 * @throws ParserConfigurationException If there were an error with the XML file.
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
					reply = JOptionPane.showConfirmDialog(context, Utils.resourceBundle.getString("new_update_dev") + "\n\n" + Utils.resourceBundle.getString("new_update_want_to_update"), Utils.resourceBundle.getString("new_update"), JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if(reply == JOptionPane.YES_OPTION)
						result = getLastJAR(newFile, LINKDEV);
					else
						return NOUPDATE;
					if(!result)
						return UPDATEERROR;
					return UPDATEDDEV;
				case PUBLIC:
					reply = JOptionPane.showConfirmDialog(context, Utils.resourceBundle.getString("new_update_public") + "\n\n" + Utils.resourceBundle.getString("new_update_want_to_update"), Utils.resourceBundle.getString("new_update"), JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if(reply == JOptionPane.YES_OPTION)
						result = getLastJAR(newFile, LINKPUBLIC);
					else
						return NOUPDATE;
					if(!result)
						return UPDATEERROR;
					return UPDATEDPUBLIC;
				case PUBLICFDEV:
					reply = JOptionPane.showConfirmDialog(context, Utils.resourceBundle.getString("new_update_public_dev"), Utils.resourceBundle.getString("new_update"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if(reply == JOptionPane.YES_OPTION)
						result = getLastJAR(newFile, LINKPUBLIC);
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
}