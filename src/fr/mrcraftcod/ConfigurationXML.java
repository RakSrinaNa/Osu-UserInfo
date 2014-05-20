package fr.mrcraftcod;

import java.io.File;

public class ConfigurationXML // TODO Javadoc
{
	private File configFile;
	private String normalConfigName = "vars.xml";

	public ConfigurationXML()
	{
		final File appData = new File(System.getenv("APPDATA"), Main.APPNAME);
		if(!appData.exists())
			appData.mkdir();
		configFile = new File(appData, normalConfigName);
	}

	public boolean readXML(String xml)
	{}
}
