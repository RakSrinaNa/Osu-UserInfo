package fr.mrcraftcod.osuuserinfo.utils;

import org.kohsuke.github.GHRelease;
import org.kohsuke.github.PagedIterable;
import java.util.LinkedHashMap;
import java.util.logging.Level;

public class Changelog
{
	public static LinkedHashMap<String, String> getAllChangelog()
	{
		return getAllChangelogText(getChangelogGit());
	}

	public static String getChangelogForVersion(String version)
	{
		return getChangelogText(getChangelogGit(), version);
	}

	private static LinkedHashMap<String, String> getAllChangelogText(PagedIterable<GHRelease> changelog)
	{
		return parseVersions(changelog);
	}

	private static PagedIterable<GHRelease> getChangelogGit()
	{
		Utils.logger.log(Level.INFO, "Getting changelog...");
		return Utils.github.getReleases();
	}

	private static String getChangelogText(PagedIterable<GHRelease> changelog, String version)
	{
		return getAllChangelogText(changelog).get(version);
	}

	private static LinkedHashMap<String, String> parseVersions(PagedIterable<GHRelease> changelog)
	{
		LinkedHashMap<String, String> changelogs = new LinkedHashMap<>();
		for(GHRelease release : changelog)
			changelogs.put(release.getTagName(), release.getBody());
		return changelogs;
	}
}
