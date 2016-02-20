package fr.mrcraftcod.osuuserinfo.utils;

import fr.mrcraftcod.osuuserinfo.Main;
import org.kohsuke.github.GHRelease;
import javax.swing.*;
import java.io.File;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class Updater
{
	public final static int NOUPDATE = 0;
	public final static int UPDATEDDEV = 1;
	public final static int UPDATEDPUBLIC = 2;
	private final static int UPDATEERROR = 3;
	private String downloadedName;

	public static int isVersionGreater(String base, String test)
	{
		base = convertOldReleases(base);
		test = convertOldReleases(test);
		if(base.equalsIgnoreCase(test))
			return 0;
		StringTokenizer t1 = new StringTokenizer(base, ".");
		StringTokenizer t2 = new StringTokenizer(test, ".");
		while(t1.hasMoreTokens() || t2.hasMoreTokens())
		{
			int i1 = t1.hasMoreTokens() ? Integer.parseInt(t1.nextToken()) : 0;
			int i2 = t2.hasMoreTokens() ? Integer.parseInt(t2.nextToken()) : 0;
			if(i1 != i2)
				return i2 - i1;
		}
		return test.compareTo(base);
	}

	private static String convertOldReleases(String tagName)
	{
		if(!tagName.contains("b"))
			return tagName;
		String tag = "";
		String[] nums = tagName.substring(0, tagName.indexOf("b")).split("\\.");
		for(int i = 0; i < nums.length - 1; i++)
			tag += nums[i] + ".";
		tag += (Integer.parseInt(nums[nums.length - 1]) - 1) + "." + tagName.substring(tagName.indexOf("b") + 1);
		return tag;
	}

	private boolean getLastJARGit(GHRelease release, String path)
	{
		boolean b;
		File newFile;
		int i = 0;
		do
		{
			downloadedName = "Osu!UserInfo-" + release.getTagName() + (i != 0 ? "(" + i + ")" : "") + ".jar";
			newFile = new File(".", downloadedName);
		}
		while(!newFile.exists());
		if(b = Utils.github.getFile(release.getTargetCommitish(), path, newFile))
			JOptionPane.showMessageDialog(null, "La nouvelle version \340 \351t\351 t\351l\351charg\351e avec le nom " + newFile.getName() + "\net va maintenant \352tre lanc\351e.");
		return b;
	}

	public int update()
	{
		GHRelease versionsUTD = Utils.github.getLastReleases();
		if(versionsUTD == null)
			return UPDATEERROR;
		Utils.logger.log(Level.INFO, "Version latest is " + versionsUTD.getTagName() + ", you are in " + Main.VERSION);
		if(isVersionGreater(Main.VERSION, versionsUTD.getTagName()) > 0)
			return update(versionsUTD);
		return NOUPDATE;
	}

	private int update(GHRelease release)
	{
		int reply;
		try
		{
			reply = JOptionPane.showConfirmDialog(null, "Nouvelle version disponible!\n\nVoulez-vous mettre à jour?", "Nouvelle version", JOptionPane.YES_NO_OPTION);
			if(reply == JOptionPane.YES_OPTION)
			{
				if(getLastJARGit(release, "Compiled/Osu!UserInfo v" + release.getTagName() + ".jar"))
					return UPDATEDPUBLIC;
				return UPDATEERROR;
			}
			return NOUPDATE;
		}
		catch(Exception e)
		{
			Utils.logger.log(Level.WARNING, "Error getting update!", e);
			return UPDATEERROR;
		}
	}

	public String getDownloadedName()
	{
		return downloadedName;
	}
}
