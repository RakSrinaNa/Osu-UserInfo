package mrcraftcod.osuuserinfo.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class GitHubSupport
{
	private static final String TOKEN = "";
	private static final String REPO = "Osu-UserInfo";
	private static final String OWNER = "MrCraftCod";
	private static final String APIBASE = "https://api.github.com/";
	private static final String AUTHORIZATION_TYPE_KEY = "Authorization";
	private static final String AUTHORIZATION_TYPE = "token " + TOKEN;
	private final PagedIterable<GHRelease> releases;
	private GitHub github;

	public GitHubSupport() throws IOException
	{
		Utils.logger.log(Level.INFO, "Initializing GitHub...");
		this.github = GitHub.connectAnonymously();
		this.releases = this.github.getRepository(OWNER + "/" + REPO).listReleases();
	}

	public ArrayList<String> getCommitMessage(String hash)
	{
		ArrayList<String> lines = new ArrayList<>();
		Utils.logger.log(Level.INFO, "Getting commit message for commit " + hash);
		try
		{
			for(String s : this.github.getRepository(OWNER + "/" + REPO).getCommit(hash).getLastStatus().getDescription().split("\n"))
				if(!s.equals(""))
					lines.add(s);
		}
		catch(Exception e)
		{
			Utils.logger.log(Level.SEVERE, "Error with github!", e);
		}
		return lines;
	}

	public boolean getFile(String branch, String path, File file)
	{
		boolean b = true;
		Utils.logger.log(Level.INFO, "Getting file from github " + path + " to " + file.getAbsolutePath());
		InputStream is = null;
		ReadableByteChannel rbc = null;
		FileOutputStream fos = null;
		try
		{
			URL url = new URL(APIBASE + "repos/" + OWNER + "/" + REPO + "/git/blobs/" + getFileSha(path, getLastBranchCommitSHA(branch)));
			Map<String, String> headers = new HashMap<>();
			headers.put(AUTHORIZATION_TYPE_KEY, AUTHORIZATION_TYPE);
			is = URLHandler.getAsBinary(url, headers);
			rbc = Channels.newChannel(is);
			fos = new FileOutputStream(file);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		}
		catch(Exception e)
		{
			Utils.logger.log(Level.SEVERE, "Error writing file from github!", e);
			b = false;
		}
		try
		{
			if(fos != null)
			{
				fos.close();
			}
		}
		catch(Exception ignored)
		{
		}
		try
		{
			if(rbc != null)
			{
				rbc.close();
			}
		}
		catch(Exception ignored)
		{
		}
		try
		{
			if(is != null)
			{
				is.close();
			}
		}
		catch(Exception ignored)
		{
		}
		return b;
	}

	private String getFileSha(String path, String shaCommit)
	{
		String sha = "";
		try
		{
			URL url = new URL(APIBASE + "repos/" + OWNER + "/" + REPO + "/git/trees/" + shaCommit + "?recursive=1");
			Map<String, String> headers = new HashMap<>();
			headers.put(AUTHORIZATION_TYPE_KEY, AUTHORIZATION_TYPE);
			JSONObject jsonResponse = URLHandler.getAsJSON(url, headers);
			JSONArray treeArray = jsonResponse.getJSONArray("tree");
			for(int i = 0; i < treeArray.length(); i++)
			{
				JSONObject file = treeArray.getJSONObject(i);
				if(file.getString("path").equals(path))
				{
					sha = file.getString("sha");
					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return sha;
	}

	public String getIssueTitle(int number)
	{
		String title = "";
		Utils.logger.log(Level.INFO, "Getting issue title for issue #" + number);
		try
		{
			title = this.github.getRepository(OWNER + "/" + REPO).getIssue(number).getTitle();
		}
		catch(Exception e)
		{
			Utils.logger.log(Level.SEVERE, "Error with github!", e);
		}
		return title;
	}

	private String getLastBranchCommitSHA(String branch)
	{
		String sha = "";
		try
		{
			URL url = new URL(APIBASE + "repos/" + OWNER + "/" + REPO + "/git/refs/heads/" + branch);
			Map<String, String> headers = new HashMap<>();
			headers.put(AUTHORIZATION_TYPE_KEY, AUTHORIZATION_TYPE);
			JSONObject jsonResponse = URLHandler.getAsJSON(url, headers);
			JSONObject objectArray = jsonResponse.getJSONObject("object");
			sha = objectArray.getString("sha");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return sha;
	}

	public ArrayList<String> getTagMessage(int tag)
	{
		ArrayList<String> lines = new ArrayList<>();
		Utils.logger.log(Level.INFO, "Getting tag message for tag " + tag);
		try
		{
			for(GHRelease r : releases)
				if(r.getId() == tag)
					Collections.addAll(lines, r.getBody().split("\n"));
		}
		catch(Exception e)
		{
			Utils.logger.log(Level.SEVERE, "Error with github!", e);
		}
		return lines;
	}

	public ArrayList<String> getTagMessage(String tag)
	{
		ArrayList<String> lines = new ArrayList<>();
		Utils.logger.log(Level.INFO, "Getting tag message for tag " + tag);
		try
		{
			for(GHRelease r : releases)
				if(r.getTagName().equalsIgnoreCase(tag))
				{
					Utils.logger.log(Level.INFO, "Tag " + tag + " have id " + r.getId());
					Collections.addAll(lines, r.getBody().split("\n"));
				}
		}
		catch(Exception e)
		{
			Utils.logger.log(Level.SEVERE, "Error with github!", e);
		}
		return lines;
	}

	public PagedIterable<GHRelease> getReleases()
	{
		return releases;
	}

	public GHRelease getLastReleases()
	{
		GHRelease versions = null;
		for(GHRelease release : releases)

			if(!release.isDraft() && (versions == null || Updater.isVersionGreater(versions.getTagName(), release.getTagName()) > 0))
				versions = release;

		return versions;
	}
}
