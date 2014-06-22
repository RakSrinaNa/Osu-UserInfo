package fr.mrcraftcod.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class used to get the changelog for a version from Bitbucket.
 *
 * @author MrCraftCod
 */
public class Changelog
{
	private final static String LINKXML = "https://bitbucket.org/api/1.0/repositories/mrcraftcod/osuuserinfo/raw/master/Infos/changelog.xml";

	/**
	 * Get the changelogs.
	 *
	 * @param version The version wanted.
	 * @param processBetas Should return changelog for betas or not.
	 * @return The changelog.
	 */
	public static LinkedHashMap<String, String> getAllChangelog(boolean processBetas)
	{
		LinkedHashMap<String, String> changelogs = null;
		File changelogFile = new File(".", "changelog.xml");
		getChangelogBitbucket(changelogFile, LINKXML);
		try
		{
			changelogs = getAllChangelogText(changelogFile, processBetas);
		}
		catch(SAXException | IOException | ParserConfigurationException e)
		{}
		changelogFile.delete();
		return changelogs;
	}

	/**
	 * Get the changelog for the wanted version.
	 *
	 * @param version The version wanted.
	 * @return The changelog.
	 */
	public static String getChangelogForVersion(String version)
	{
		String changelogText = "";
		File changelogFile = new File(".", "changelog.xml");
		getChangelogBitbucket(changelogFile, LINKXML);
		try
		{
			changelogText = getChangelogText(changelogFile, version);
		}
		catch(SAXException | IOException | ParserConfigurationException e)
		{}
		changelogFile.delete();
		return changelogText;
	}

	/**
	 * Used to get the changelogs.
	 *
	 * @param changelogFile The XML file.
	 * @param version The version to get.
	 * @param processBetas Should return changelog for betas or not.
	 * @return The changelog text of the wanted version.
	 *
	 * @throws SAXException If there were an error with the XML file.
	 * @throws IOException If there were an error with the file.
	 * @throws ParserConfigurationException If there were an error with the XML file.
	 */
	private static LinkedHashMap<String, String> getAllChangelogText(File changelogFile, boolean processBetas) throws SAXException, IOException, ParserConfigurationException
	{
		return parseVersions(changelogFile, processBetas);
	}

	/**
	 * Used to get the XML file containing the changelog.
	 *
	 * @param updateFile The file where to save the XML.
	 * @param link The link of the XML.
	 */
	private static void getChangelogBitbucket(File updateFile, String link)
	{
		Utils.logger.log(Level.INFO, "Getting changelog...");
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
	 * Used to get the changelog text from an XML file.
	 *
	 * @param changelogFile The XML file.
	 * @param version The version to get.
	 * @return The changelog text of the wanted version.
	 *
	 * @throws SAXException If there were an error with the XML file.
	 * @throws IOException If there were an error with the file.
	 * @throws ParserConfigurationException If there were an error with the XML file.
	 */
	private static String getChangelogText(File changelogFile, String version) throws SAXException, IOException, ParserConfigurationException
	{
		return parseVersions(changelogFile, true).get(version);
	}

	/**
	 * Used to extract versions and changelog from an XML file.
	 *
	 * @param file The XML file.
	 * @param processBetas Should return changelog for betas or not.
	 * @return An HashMap object containing the versions as key and changelog as values.
	 *
	 * @throws SAXException If there were an error with the XML file.
	 * @throws IOException If there were an error with the file.
	 * @throws ParserConfigurationException If there were an error with the XML file.
	 */
	private static LinkedHashMap<String, String> parseVersions(File file, boolean processBetas) throws SAXException, IOException, ParserConfigurationException
	{
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(file);
		doc.getDocumentElement().normalize();
		NodeList listVersion = doc.getElementsByTagName("version");
		LinkedHashMap<String, String> changelogs = new LinkedHashMap<String, String>();
		for(int s = listVersion.getLength() - 1; s > -1; s--)
		{
			Node versionNode = listVersion.item(s);
			if(versionNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element versionElement = (Element) versionNode;
				NodeList nameList = versionElement.getElementsByTagName("name");
				Element nameElement = (Element) nameList.item(0);
				NodeList textNaList = nameElement.getChildNodes();
				NodeList numberList = versionElement.getElementsByTagName("changes");
				Element numberElement = (Element) numberList.item(0);
				NodeList textNuList = numberElement.getChildNodes();
				if(textNaList.item(0).getNodeValue().trim().contains("b") && !processBetas)
					continue;
				changelogs.put(textNaList.item(0).getNodeValue().trim(), textNuList.item(0).getNodeValue().trim());
			}
		}
		return changelogs;
	}
}
