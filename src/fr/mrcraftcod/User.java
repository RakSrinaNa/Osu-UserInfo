package fr.mrcraftcod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable
{
	private static final long serialVersionUID = 9114477464694621621L;
	private Stats stats_normal, stats_taiko, stats_ctb, stats_mania;
	private String username = "";

	public void serialize(File file) throws IOException
	{
		if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(this);
		oos.close();
	}

	public static User deserialize(File file) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		User user = (User) ois.readObject();
		ois.close();
		return user;
	}

	public void setStats(Stats stats, int mode)
	{
		switch(mode)
		{
			case 0:
				setStats_normal(stats);
				return;
			case 1:
				setStats_taiko(stats);
				return;
			case 2:
				setStats_ctb(stats);
				return;
			case 3:
				setStats_mania(stats);
				return;
		}
		throw new IllegalArgumentException("Mode must be between 0 and 3!");
	}

	public Stats getStats(int mode)
	{
		switch(mode)
		{
			case 0:
				return getStats_normal();
			case 1:
				return getStats_taiko();
			case 2:
				return getStats_ctb();
			case 3:
				return getStats_mania();
		}
		throw new IllegalArgumentException("Mode must be between 0 and 3!");
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public Stats getStats_normal()
	{
		return stats_normal;
	}

	public void setStats_normal(Stats stats_normal)
	{
		this.stats_normal = stats_normal;
	}

	public Stats getStats_taiko()
	{
		return stats_taiko;
	}

	public void setStats_taiko(Stats stats_taiko)
	{
		this.stats_taiko = stats_taiko;
	}

	public Stats getStats_ctb()
	{
		return stats_ctb;
	}

	public void setStats_ctb(Stats stats_ctb)
	{
		this.stats_ctb = stats_ctb;
	}

	public Stats getStats_mania()
	{
		return stats_mania;
	}

	public void setStats_mania(Stats stats_mania)
	{
		this.stats_mania = stats_mania;
	}
}
