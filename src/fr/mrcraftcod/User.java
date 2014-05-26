package fr.mrcraftcod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * User object. Used to store stats of a given user.
 * 
 * @author MrCraftCod
 */
public class User implements Serializable
{
	private static final long serialVersionUID = 9114477464694621621L;
	private Stats stats_normal, stats_taiko, stats_ctb, stats_mania;
	private String username = "";
	private transient int userID;

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
				return getStatsOsuStandard();
			case 1:
				return getStatsTaiko();
			case 2:
				return getStatsCTB();
			case 3:
				return getStatsOsuMania();
		}
		throw new IllegalArgumentException("Mode must be between 0 and 3!");
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
	 * @return The Stats for this mode.
	 * 
	 * @see Stats
	 */
	public Stats getStatsOsuStandard()
	{
		return stats_normal;
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
		this.stats_normal = stats;
	}

	/**
	 * Used to get the stats for the Taiko mode.
	 * 
	 * @return The Stats for this mode.
	 * 
	 * @see Stats
	 */
	public Stats getStatsTaiko()
	{
		return stats_taiko;
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
		this.stats_taiko = stats;
	}

	/**
	 * Used to get the stats for the CTB mode.
	 * 
	 * @return The Stats for this mode.
	 * 
	 * @see Stats
	 */
	public Stats getStatsCTB()
	{
		return stats_ctb;
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
		this.stats_ctb = stats;
	}

	/**
	 * Used to get the stats for the Osu!Mania mode.
	 * 
	 * @return The Stats for this mode.
	 * 
	 * @see Stats
	 */
	public Stats getStatsOsuMania()
	{
		return stats_mania;
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
		this.stats_mania = stats;
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
}
