package fr.mrcraftcod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;

/**
 * User object. Used to store stats of a given user.
 * 
 * @author MrCraftCod
 */
public class User implements Serializable
{
	private static final long serialVersionUID = 9114477464694621621L;
	private ArrayList<Stats> stats_normal, stats_taiko, stats_ctb, stats_mania;
	private String username = "";
	private transient int userID;

	public User()
	{
		stats_normal = new ArrayList<Stats>();
		stats_taiko = new ArrayList<Stats>();
		stats_ctb = new ArrayList<Stats>();
		stats_mania = new ArrayList<Stats>();
	}

	/**
	 * Used to serialize the object to a file.
	 * 
	 * @param file The file where to save the object.
	 * 
	 * @throws IOException If the file cannot be created.
	 */
	public void serialize(File file) throws IOException
	{
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(this);
		oos.close();
	}

	/**
	 * Used to deserialize a serialised User object.
	 * 
	 * @param file The file of the serialized object.
	 * @return A user object of the serialized file.
	 * 
	 * @throws FileNotFoundException If the file cannot be found.
	 * @throws IOException If the file cannot be read.
	 * @throws ClassNotFoundException If the file isn't a serialized Used object.
	 */
	public static User deserialize(File file) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		User user = (User) ois.readObject();
		ois.close();
		return user;
	}

	/**
	 * Used to set the stats of a given mode.
	 * 
	 * @param stats The Stats to set.
	 * @param mode The mode of the stats.
	 *            <ul>
	 *            <li>0 - Osu!Standard</li>
	 *            <li>1 - Taiko</li>
	 *            <li>2 - CTB</li>
	 *            <li>3 - Osu!Mania</li>
	 *            </ul>
	 * 
	 * @see Stats
	 */
	public void setStats(Stats stats, int mode)
	{
		switch(mode)
		{
			case 0:
				setStatsOsuStandard(stats);
				return;
			case 1:
				setStatsTaiko(stats);
				return;
			case 2:
				setStatsCTB(stats);
				return;
			case 3:
				setStatsOsuMania(stats);
				return;
		}
		throw new IllegalArgumentException("Mode must be between 0 and 3!");
	}

	/**
	 * Used to get the stats of a given mode.
	 * 
	 * @param mode The mode of the stats to get:
	 *            <ul>
	 *            <li>0 - Osu!Standard</li>
	 *            <li>1 - Taiko</li>
	 *            <li>2 - CTB</li>
	 *            <li>3 - Osu!Mania</li>
	 *            </ul>
	 * @return A Stats object for the stats asked
	 * 
	 * @see Stats
	 */
	public Stats getStats(int mode)
	{
		switch(mode)
		{
			case 0:
				return getStatsOsuStandard(this.stats_normal.size() - 1);
			case 1:
				return getStatsTaiko(this.stats_taiko.size() - 1);
			case 2:
				return getStatsCTB(this.stats_ctb.size() - 1);
			case 3:
				return getStatsOsuMania(this.stats_mania.size() - 1);
		}
		throw new IllegalArgumentException("Mode must be between 0 and 3!");
	}

	// TODO JAVADOC
	public Stats getStatsByModeAndDate(int mode, long date)
	{
		ArrayList<Stats> stats = getAllStats(mode);
		for(Stats stat : stats)
		{
			String temp = String.valueOf(stat.getDate());
			String tempp = String.valueOf(date);
			if(temp.substring(0, temp.length() - 4).equals(tempp.substring(0, tempp.length() - 4)))
				return stat;
		}
		return null;
	}

	// TODO JAVADOC
	public ArrayList<Stats> getAllStats(int mode)
	{
		switch(mode)
		{
			case 0:
				return this.stats_normal;
			case 1:
				return this.stats_taiko;
			case 2:
				return this.stats_ctb;
			case 3:
				return this.stats_mania;
		}
		throw new IllegalArgumentException("Mode must be between 0 and 3!");
	}

	// TODO JAVADOC
	private boolean hasStatsChanged(Stats p, Stats n)
	{
		if(p == null)
			return true;
		boolean b = !(n.compareAccuracy(p).equals("") && n.comparePlayCount(p).equals("") && n.comparePP(p).equals("") && n.compareRank(p).equals("") && n.compareRankedScore(p).equals("") && n.compareTotalHits(p).equals(""));
		return b;
	}

	/**
	 * Used to get the username.
	 * 
	 * @return The username.
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * Used to set the username.
	 * 
	 * @param username The username to set.
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * Used to get the stats for the Osu!Standard mode.
	 * 
	 * @param index The index of the stats in the history.
	 * @return The Stats for this mode.
	 * 
	 * @see Stats
	 */
	public Stats getStatsOsuStandard(int index)
	{
		try
		{
			return this.stats_normal.get(index);
		}
		catch(Exception e)
		{}
		return null;
	}

	/**
	 * Used to set the stats for the Osu!Standard mode.
	 * 
	 * @param stats The Stats to set for this mode.
	 * 
	 * @see Stats
	 */
	public void setStatsOsuStandard(Stats stats)
	{
		while(this.stats_normal.size() > Main.trackedStats)
			this.stats_normal.remove(0);
		if(hasStatsChanged(this.getStats(0), stats))
			this.stats_normal.add(stats);
		else
		{
			Stats newStats = this.getStats(0);
			newStats.setDate(stats.getDate());
			this.stats_normal.remove(this.stats_normal.size() - 1);
			this.stats_normal.add(newStats);
		}
	}

	/**
	 * Used to get the stats for the Taiko mode.
	 * 
	 * @param index The index of the stats in the history.
	 * @return The Stats for this mode.
	 * 
	 * @see Stats
	 */
	public Stats getStatsTaiko(int index)
	{
		try
		{
			return this.stats_taiko.get(index);
		}
		catch(Exception e)
		{}
		return null;
	}

	/**
	 * Used to set the stats for the Taiko mode.
	 * 
	 * @param stats The Stats to set for this mode.
	 * 
	 * @see Stats
	 */
	public void setStatsTaiko(Stats stats)
	{
		while(this.stats_taiko.size() > Main.trackedStats)
			this.stats_taiko.remove(0);
		if(hasStatsChanged(this.getStats(1), stats))
			this.stats_taiko.add(stats);
		else
		{
			Stats newStats = this.getStats(1);
			newStats.setDate(stats.getDate());
			this.stats_taiko.remove(this.stats_taiko.size() - 1);
			this.stats_taiko.add(newStats);
		}
	}

	/**
	 * Used to get the stats for the CTB mode.
	 * 
	 * @param index The index of the stats in the history.
	 * @return The Stats for this mode.
	 * 
	 * @see Stats
	 */
	public Stats getStatsCTB(int index)
	{
		try
		{
			return this.stats_ctb.get(index);
		}
		catch(Exception e)
		{}
		return null;
	}

	/**
	 * Used to set the stats for the CTB mode.
	 * 
	 * @param stats The Stats to set for this mode.
	 * 
	 * @see Stats
	 */
	public void setStatsCTB(Stats stats)
	{
		while(this.stats_ctb.size() > Main.trackedStats)
			this.stats_ctb.remove(0);
		if(hasStatsChanged(this.getStats(2), stats))
			this.stats_ctb.add(stats);
		else
		{
			Stats newStats = this.getStats(2);
			newStats.setDate(stats.getDate());
			this.stats_ctb.remove(this.stats_ctb.size() - 1);
			this.stats_ctb.add(newStats);
		}
	}

	/**
	 * Used to get the stats for the Osu!Mania mode.
	 * 
	 * @param index The index of the stats in the history.
	 * @return The Stats for this mode.
	 * 
	 * @see Stats
	 */
	public Stats getStatsOsuMania(int index)
	{
		try
		{
			return this.stats_mania.get(index);
		}
		catch(Exception e)
		{}
		return null;
	}

	/**
	 * Used to set the stats for the Osu!Mania mode.
	 * 
	 * @param stats The Stats to set for this mode.
	 * 
	 * @see Stats
	 */
	public void setStatsOsuMania(Stats stats)
	{
		while(this.stats_mania.size() > Main.trackedStats)
			this.stats_mania.remove(0);
		if(hasStatsChanged(this.getStats(3), stats))
			this.stats_mania.add(stats);
		else
		{
			Stats newStats = this.getStats(3);
			newStats.setDate(stats.getDate());
			this.stats_mania.remove(this.stats_mania.size() - 1);
			this.stats_mania.add(newStats);
		}
	}

	/**
	 * Used to get the user ID.
	 * 
	 * @return The user ID.
	 */
	public int getUserID()
	{
		return userID;
	}

	/**
	 * Used to set the user ID.
	 * 
	 * @param userID The ID to set.
	 */
	public void setUserID(int userID)
	{
		this.userID = userID;
	}

	/**
	 * Used to get the dates for saved stats for a given mode.
	 * 
	 * @param mode The mode of the stats to get:
	 *            <ul>
	 *            <li>0 - Osu!Standard</li>
	 *            <li>1 - Taiko</li>
	 *            <li>2 - CTB</li>
	 *            <li>3 - Osu!Mania</li>
	 *            </ul>
	 * @return An array of String (normalised dates).
	 * 
	 * @see Stats
	 */
	@SuppressWarnings("unchecked")
	public String[] getAvalidbleStatsDates(int mode)
	{
		ArrayList<Stats> stats = (ArrayList<Stats>) getAllStats(mode).clone();
		if(stats == null)
			return null;
		stats.remove(stats.size() - 1);
		DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM);
		String[] dates = new String[stats.size()];
		for(int i = 0; i < stats.size(); i++)
			dates[i] = formatter.format(stats.get(i).getDate());
		return dates;
	}
}
