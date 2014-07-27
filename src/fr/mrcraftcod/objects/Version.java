package fr.mrcraftcod.objects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version
{
	private boolean isValid = false;
	private String version;
	private int globalVersion = -1, subVersion = -1, betaVersion = -1;

	public Version(String version)
	{
		this.version = version;
		Pattern pattern = Pattern.compile("\\d+[.]{1}\\d+([b]{1}\\d+)?");
		Matcher matcher = pattern.matcher(version);
		if(!matcher.find())
			return;
		this.isValid = true;
		String[] vers = version.split("\\.");
		this.globalVersion = Integer.parseInt(vers[0]);
		if(vers[1].contains("b"))
		{
			vers = vers[1].split("b");
			this.subVersion = Integer.parseInt(vers[0]);
			this.betaVersion = Integer.parseInt(vers[1]);
		}
		else
			this.subVersion = Integer.parseInt(vers[1]);
	}

	public int getBetaVersion()
	{
		return this.betaVersion;
	}

	public int getGlobalVersion()
	{
		return this.globalVersion;
	}

	public int getSubVersion()
	{
		return this.subVersion;
	}

	public String getVersion()
	{
		return this.version;
	}

	public boolean isBetaVersion()
	{
		return this.betaVersion != -1;
	}

	public boolean isNewer(Version old)
	{
		if(!(old.isValid() && isValid()))
			return false;
		if(getGlobalVersion() > old.getGlobalVersion())
			return true;
		if(getSubVersion() > old.getSubVersion())
			return true;
		if(getBetaVersion() > old.getBetaVersion())
			return true;
		if(old.isBetaVersion() && !isBetaVersion() && getSubVersion() == old.getSubVersion())
			return true;
		return false;
	}

	public boolean isValid()
	{
		return this.isValid;
	}

	@Override
	public String toString()
	{
		return getVersion();
	}
}
