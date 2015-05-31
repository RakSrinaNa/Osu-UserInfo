package mrcraftcod.osuuserinfo.objects;

import mrcraftcod.osuuserinfo.utils.Utils;
import java.io.*;
import java.text.DateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;

/**
 * User object. Used to store stats of a given user.
 *
 * @author MrCraftCod
 */
public class User implements Serializable
{
	private static final long serialVersionUID = 9114477464694621621L;
	private static final int USER_VERSION = 3;
	private HashMap<Long, Stats> stats_normal, stats_taiko, stats_ctb, stats_mania;
	private String username = "";
	private int version;
	private transient int userID;
	private transient String country;

	/**
	 * Constructor.
	 */
	public User()
	{
		this.version = USER_VERSION;
		this.stats_normal = new HashMap<>();
		this.stats_taiko = new HashMap<>();
		this.stats_ctb = new HashMap<>();
		this.stats_mania = new HashMap<>();
	}

	public static User deserialize(File file) throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		User user = (User) ois.readObject();
		ois.close();
		return user;
	}

	/**
	 * Used to get all the saved stats.
	 *
	 * @return An ArrayList with the stats.
	 */
	public HashMap<Long, Stats> getAllStats()
	{
		HashMap<Long, Stats> stats = new HashMap<>();
		stats.putAll(this.stats_normal);
		stats.putAll(this.stats_taiko);
		stats.putAll(this.stats_ctb);
		stats.putAll(this.stats_mania);
		return stats;
	}

	/**
	 * Used to get all the saved stats for a given mode.
	 *
	 * @param mode The mode to get the stats.
	 * @return An ArrayList with the stats.
	 */
	public HashMap<Long, Stats> getAllStats(int mode)
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
	public String[] getAvalidbleStatsDates(int mode)
	{
		Set<Long> date = getAllStats(mode).keySet();
		DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM, Utils.locale);
		String[] dates = new String[date.size()];
		date.forEach(new Consumer<Long>()
		{
			int i = 0;

			@Override
			public void accept(Long t)
			{
				dates[this.i++] = formatter.format(t);
			}
		});
		return dates;
	}

	/**
	 * Used to get the country of the user.
	 *
	 * @return The country.
	 */
	public String getCountry()
	{
		return this.country;
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
	public Stats getLastStats(int mode)
	{
		try
		{
			switch(mode)
			{
				case 0:
					return getStatsOsuStandard(new TreeSet<>(this.stats_normal.keySet()).last());
				case 1:
					return getStatsTaiko(new TreeSet<>(this.stats_taiko.keySet()).last());
				case 2:
					return getStatsCTB(new TreeSet<>(this.stats_ctb.keySet()).last());
				case 3:
					return getStatsOsuMania(new TreeSet<>(this.stats_mania.keySet()).last());
			}
		}
		catch(NoSuchElementException e)
		{
			return null;
		}
		throw new IllegalArgumentException("Mode must be between 0 and 3!");
	}

	/**
	 * Used to get a Stats object by its date.
	 *
	 * @param mode The mode of the Stats.
	 * @param date The date of the Stats.
	 * @param defaultStats The stats to return is the date is incorrect.
	 * @return The wanted Stats object, null if it doesn't exists.
	 *
	 * @see Stats
	 */
	public Stats getStatsByModeAndDate(int mode, long date, Stats defaultStats)
	{
		if(date < 0)
			return defaultStats;
		try
		{
			HashMap<Long, Stats> stats = getAllStats(mode);
			for(Stats stat : stats.values())
			{
				String temp = String.valueOf(stat.getDate());
				String tempp = String.valueOf(date);
				if(temp.substring(0, temp.length() - 4).equals(tempp.substring(0, tempp.length() - 4)))
					return stat;
			}
		}
		catch(Exception e)
		{
			Utils.logger.log(Level.WARNING, "", e);
		}
		return defaultStats;
	}

	/**
	 * Used to get the stats for the CTB mode.
	 *
	 * @param index The index of the stats in the history.
	 * @return The Stats for this mode.
	 *
	 * @see Stats
	 */
	private Stats getStatsCTB(long index)
	{
		try
		{
			return this.stats_ctb.get(index);
		}
		catch(Exception ignored)
		{}
		return null;
	}

	/**
	 * Used to get the stats for the Osu!Mania mode.
	 *
	 * @param index The index of the stats in the history.
	 * @return The Stats for this mode.
	 *
	 * @see Stats
	 */
	private Stats getStatsOsuMania(long index)
	{
		try
		{
			return this.stats_mania.get(index);
		}
		catch(Exception ignored)
		{}
		return null;
	}

	/**
	 * Used to get the stats for the Osu!Standard mode.
	 *
	 * @param index The index of the stats in the history.
	 * @return The Stats for this mode.
	 *
	 * @see Stats
	 */
	private Stats getStatsOsuStandard(long index)
	{
		try
		{
			return this.stats_normal.get(index);
		}
		catch(Exception ignored)
		{}
		return null;
	}

	/**
	 * Used to get the stats for the Taiko mode.
	 *
	 * @param index The index of the stats in the history.
	 * @return The Stats for this mode.
	 *
	 * @see Stats
	 */
	private Stats getStatsTaiko(long index)
	{
		try
		{
			return this.stats_taiko.get(index);
		}
		catch(Exception ignored)
		{}
		return null;
	}

	/**
	 * Used to get the user ID.
	 *
	 * @return The user ID.
	 */
	public int getUserID()
	{
		return this.userID;
	}

	/**
	 * Used to get the username.
	 *
	 * @return The username.
	 */
	public String getUsername()
	{
		return this.username;
	}

	/**
	 * Used to know if the stats have changed.
	 *
	 * @param previousStats The old Stats.
	 * @param newStats The new Stats.
	 * @return True is there is a modification, false if not.
	 *
	 * @see Stats
	 */
	private boolean hasStatsChanged(boolean forceNewStats, Stats previousStats, Stats newStats)
	{
		return previousStats == null || !forceNewStats || !newStats.equals(previousStats);
	}

	/**
	 * Used to know if two User objects represents the same user.
	 *
	 * @param lastUser The user to compare with.
	 * @return True if they are same user, false if not.
	 */
	public boolean isSameUser(User lastUser)
	{
		return lastUser != null && this.username.equals(lastUser.getUsername());
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
	 * Used to set the country of the user.
	 *
	 * @param country The country to set.
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}

	/**
	 * Used to set the stats of a given mode.
	 *
	 * @param stats The Stats to set.
	 *
	 * @see Stats
	 */
	public void setStats(boolean forceNewStats, Stats stats)
	{
		switch(stats.getMode())
		{
			case 0:
				setStatsOsuStandard(forceNewStats, stats);
				return;
			case 1:
				setStatsTaiko(forceNewStats, stats);
				return;
			case 2:
				setStatsCTB(forceNewStats, stats);
				return;
			case 3:
				setStatsOsuMania(forceNewStats, stats);
				return;
		}
		throw new IllegalArgumentException("Mode must be between 0 and 3!");
	}

	/**
	 * Used to set the stats for the CTB mode.
	 *
	 * @param stats The Stats to set for this mode.
	 *
	 * @see Stats
	 */
	private void setStatsCTB(boolean forceNewStats, Stats stats)
	{
		if(Utils.numberTrackedStatsToKeep > 0)
			while(this.stats_ctb.size() > Utils.numberTrackedStatsToKeep + 1)
				this.stats_ctb = removeOlder(this.stats_ctb);
		if(hasStatsChanged(forceNewStats, getLastStats(2), stats))
			this.stats_ctb.put(stats.getDate(), stats);
		else
		{
			Stats newStats = getLastStats(2);
			this.stats_ctb.remove(newStats.getDate());
			newStats.setDate(stats.getDate());
			this.stats_ctb.put(newStats.getDate(), newStats);
		}
	}

	/**
	 * Used to set the stats for the Osu!Mania mode.
	 *
	 * @param stats The Stats to set for this mode.
	 *
	 * @see Stats
	 */
	private void setStatsOsuMania(boolean forceNewStats, Stats stats)
	{
		if(Utils.numberTrackedStatsToKeep > 0)
			while(this.stats_mania.size() > Utils.numberTrackedStatsToKeep + 1)
				this.stats_mania = removeOlder(this.stats_mania);
		if(hasStatsChanged(forceNewStats, getLastStats(3), stats))
			this.stats_mania.put(stats.getDate(), stats);
		else
		{
			Stats newStats = getLastStats(3);
			this.stats_mania.remove(newStats.getDate());
			newStats.setDate(stats.getDate());
			this.stats_mania.put(newStats.getDate(), newStats);
		}
	}

	/**
	 * Used to set the stats for the Osu!Standard mode.
	 *
	 * @param stats The Stats to set for this mode.
	 *
	 * @see Stats
	 */
	private void setStatsOsuStandard(boolean forceNewStats, Stats stats)
	{
		if(Utils.numberTrackedStatsToKeep > 0)
			while(this.stats_normal.size() > Utils.numberTrackedStatsToKeep + 1)
				this.stats_normal = removeOlder(this.stats_normal);
		if(hasStatsChanged(forceNewStats, getLastStats(0), stats))
			this.stats_normal.put(stats.getDate(), stats);
		else
		{
			Stats newStats = getLastStats(0);
			this.stats_normal.remove(newStats.getDate());
			newStats.setDate(stats.getDate());
			this.stats_normal.put(newStats.getDate(), newStats);
		}
	}

	/**
	 * Used to set the stats for the Taiko mode.
	 *
	 * @param stats The Stats to set for this mode.
	 *
	 * @see Stats
	 */
	private void setStatsTaiko(boolean forceNewStats, Stats stats)
	{
		if(Utils.numberTrackedStatsToKeep > 0)
			while(this.stats_taiko.size() > Utils.numberTrackedStatsToKeep + 1)
				this.stats_taiko = removeOlder(this.stats_taiko);
		if(hasStatsChanged(forceNewStats, getLastStats(1), stats))
			this.stats_taiko.put(stats.getDate(), stats);
		else
		{
			Stats newStats = getLastStats(1);
			this.stats_taiko.remove(newStats.getDate());
			newStats.setDate(stats.getDate());
			this.stats_taiko.put(newStats.getDate(), newStats);
		}
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
	 * Used to set the username.
	 *
	 * @param username The username to set.
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	private HashMap<Long, Stats> readArrayStats(ArrayList<Stats> readObject)
	{
		HashMap<Long, Stats> stats = new HashMap<>();
		for(Stats stat : readObject)
			stats.put(stat.getDate(), stat);
		return stats;
	}

	/**
	 * Called to read object from a file.
	 *
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException
	{
		this.version = USER_VERSION;
		int version = ois.readInt();
		if(version >= 2 && version < 3)
		{
			this.username = ois.readUTF();
			this.stats_normal = readArrayStats((ArrayList<Stats>) ois.readObject());
			this.stats_taiko = readArrayStats((ArrayList<Stats>) ois.readObject());
			this.stats_ctb = readArrayStats((ArrayList<Stats>) ois.readObject());
			this.stats_mania = readArrayStats((ArrayList<Stats>) ois.readObject());
		}
		else if(version >= 3)
		{
			this.username = ois.readUTF();
			this.stats_normal = (HashMap<Long, Stats>) ois.readObject();
			this.stats_taiko = (HashMap<Long, Stats>) ois.readObject();
			this.stats_ctb = (HashMap<Long, Stats>) ois.readObject();
			this.stats_mania = (HashMap<Long, Stats>) ois.readObject();
		}
		if(this.stats_normal == null)
			this.stats_normal = new HashMap<>();
		if(this.stats_taiko == null)
			this.stats_taiko = new HashMap<>();
		if(this.stats_ctb == null)
			this.stats_ctb = new HashMap<>();
		if(this.stats_mania == null)
			this.stats_mania = new HashMap<>();
	}

	private HashMap<Long, Stats> removeOlder(HashMap<Long, Stats> stats)
	{
		stats.remove(new TreeSet<>(stats.keySet()).first());
		return stats;
	}

	/**
	 * Called to write the object in a file.
	 *
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.writeInt(this.version);
		oos.writeUTF(this.username);
		oos.writeObject(this.stats_normal);
		oos.writeObject(this.stats_taiko);
		oos.writeObject(this.stats_ctb);
		oos.writeObject(this.stats_mania);
		oos.flush();
	}
}
