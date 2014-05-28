package fr.mrcraftcod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Used to fetch and download new versions.
 * 
 * @author MrCraftCod
 */
public class Updater // TODO Javadoc
{
	public final static int DEVELOPER = 1, PUBLIC = 2, PUBLICFDEV = 3, NOUPDATE = 0, UPDATEDDEV = 1, UPDATEDPUBLIC = 2, UPDATEERROR = 3;
	private final static String DEVELOPERTAG = "DEVELOPER", PUBLICTAG = "PUBLIC", LINKXML = "https://bitbucket.org/api/1.0/repositories/mrcraftcod/osuuserinfo/raw/master/Infos/lastVersion.xml", LINKPUBLIC = "https://bitbucket.org/api/1.0/repositories/mrcraftcod/osuuserinfo/raw/master/Infos/Jars/Public/public.jar", LINKDEV = "https://bitbucket.org/api/1.0/repositories/mrcraftcod/osuuserinfo/raw/master/Infos/Jars/Developer/developer.jar";
	private static HashMap<String, String> versionsUTD;

	/**
	 * Used to get the XML file containing the versions up to date.
	 * 
	 * @param updateFile The file where to save the XML.
	 * @param link The link of the XML.
	 */
	private static void getLastVersionBitbucket(File updateFile, String link)
	{
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
		{}
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
	 */
	public static void getLastJAR(File newFile, String link)
	{
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
			JOptionPane.showMessageDialog(null, "La nouvelle version \340 \351t\351 t\351l\351charg\351e avec le nom " + newFile.getName() + "\net va maintenant \352tre lanc\351e.");
		}
		catch(IOException e)
		{}
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
	 * Used to update the actual version.
	 * 
	 * @param version The type of version to update.
	 * @return Which version the user updated.
	 */
	private static int update(int version)
	{
		int reply;
		try
		{
			switch(version)
			{
				case DEVELOPER:
					reply = JOptionPane.showConfirmDialog(null, "Nouvelle version developeur disponible!\n\nVoulez-vous mettre à jour?", "Nouvelle version", JOptionPane.YES_NO_OPTION);
					if(reply == JOptionPane.YES_OPTION)
						getLastJAR(new File(".", Main.APPNAME + ".jar"), LINKDEV);
					else
						return NOUPDATE;
					return UPDATEDDEV;
				case PUBLIC:
					reply = JOptionPane.showConfirmDialog(null, "Nouvelle version disponible!\n\nVoulez-vous mettre à jour?", "Nouvelle version", JOptionPane.YES_NO_OPTION);
					if(reply == JOptionPane.YES_OPTION)
						getLastJAR(new File(".", Main.APPNAME + ".jar"), LINKPUBLIC);
					else
						return NOUPDATE;
					return UPDATEDPUBLIC;
				case PUBLICFDEV:
					reply = JOptionPane.showConfirmDialog(null, "Vous utilisez actuellement une version d\351velopeur.\nCependant vous n'avez pas activ\351 cette option dans les pr\351f\351rences.\nVoulez-vous mettre \340 jour vers la derni\350re version publique disponible?", "Nouvelle version", JOptionPane.YES_NO_OPTION);
					if(reply == JOptionPane.YES_OPTION)
						getLastJAR(new File(".", Main.APPNAME + ".jar"), LINKPUBLIC);
					else
						return NOUPDATE;
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
	public static int update()
	{
		Main.startup.setStartupText(Main.resourceBundle.getString("startup_fecth_updates"));
		File updateFile = new File(".", "updates.xml");
		getLastVersionBitbucket(updateFile, LINKXML);
		try
		{
			parseVersions(updateFile);
		}
		catch(SAXException | IOException | ParserConfigurationException e)
		{
			return UPDATEERROR;
		}
		if(versionsUTD == null)
			return UPDATEERROR;
		else if(versionsUTD.size() < 1)
			return UPDATEERROR;
		else if(!isPublicUpToDate())
			return update(PUBLIC);
		return NOUPDATE;
	}

	/**
	 * Used to know if the public version is up to date.
	 * 
	 * @return True if up to date, false if not.
	 */
	private static boolean isPublicUpToDate()
	{
		try
		{
			String upToDateVersion = versionsUTD.get(PUBLICTAG);
			int actualGlobalVersion = Integer.parseInt(String.valueOf(Main.VERSION).split("\\.")[0]);
			int upToDateGlobalVersion = Integer.parseInt(upToDateVersion.split("\\.")[0]);
			int actualSubVersion = -1;
			int upToDateSubVersion = -1;
			String actualSubVersionS = String.valueOf(Main.VERSION).split("\\.")[1];
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
	 * NOT CURENTLY USED.
	 * Used to know if the developer version is up to date.
	 * 
	 * @return True if up to date, false if not.
	 * 
	 * @deprecated Use {@link #isPublicUpToDate()} instead.
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private static boolean isDevUpToDate()
	{
		try
		{
			String upToDateVersion = versionsUTD.get(DEVELOPERTAG);
			int actualGlobalVersion = Integer.parseInt(String.valueOf(Main.VERSION).split("\\.")[0]);
			int upToDateGlobalVersion = Integer.parseInt(upToDateVersion.split("\\.")[0]);
			int actualSubVersion = -1;
			int upToDateSubVersion = -1;
			String actualSubVersionS = String.valueOf(Main.VERSION).split("\\.")[1];
			String upToDateSubVersionS = upToDateVersion.split("\\.")[1];
			if(actualSubVersionS.contains("b"))
				actualSubVersionS = actualSubVersionS.substring(0, actualSubVersionS.indexOf("b"));
			if(upToDateSubVersionS.contains("b"))
				upToDateSubVersionS = upToDateSubVersionS.substring(0, upToDateSubVersionS.indexOf("b"));
			actualSubVersion = Integer.parseInt(actualSubVersionS);
			upToDateSubVersion = Integer.parseInt(upToDateSubVersionS);
			int actualBetaVersion = String.valueOf(Main.VERSION).contains("b") ? Integer.parseInt(String.valueOf(Main.VERSION).split("b")[1]) : -1;
			int upToDateBetaVersion = upToDateVersion.contains("b") ? Integer.parseInt(upToDateVersion.split("b")[1]) : -1;
			if(actualGlobalVersion < upToDateGlobalVersion)
				return false;
			else if(actualSubVersion < upToDateSubVersion)
				return false;
			if(!String.valueOf(Main.VERSION).contains("b"))
				return false;
			if(actualBetaVersion < upToDateBetaVersion)
				return false;
		}
		catch(Exception e)
		{}
		return true;
	}

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
