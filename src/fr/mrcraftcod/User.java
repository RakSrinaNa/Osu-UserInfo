package fr.mrcraftcod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class User implements Serializable
{
	private static final long serialVersionUID = 9114477464694621621L;
	private String username = "";
	private int playcount;
	private long rankedScore;
	private long totalScore;
	private double pp;
	private double accuracy;
	private long totalHits;
	private double rank;

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

	public long getTotalHits()
	{
		return totalHits;
	}

	public void setTotalHits(long totalHits)
	{
		this.totalHits = totalHits;
	}

	public double getAccuracy()
	{
		return accuracy;
	}

	public void setAccuracy(double accuracy)
	{
		this.accuracy = accuracy;
	}

	public double getPp()
	{
		return pp;
	}

	public void setPp(double pp)
	{
		this.pp = pp;
	}

	public long getRankedScore()
	{
		return rankedScore;
	}

	public void setRankedScore(long rankedScore)
	{
		this.rankedScore = rankedScore;
	}

	public long getTotalScore()
	{
		return totalScore;
	}

	public void setTotalScore(long totalScore)
	{
		this.totalScore = totalScore;
	}

	public int getPlaycount()
	{
		return playcount;
	}

	public void setPlaycount(int playcount)
	{
		this.playcount = playcount;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public double getRank()
	{
		return rank;
	}

	public void setRank(double rank)
	{
		this.rank = rank;
	}

	private String getSign(double nb)
	{
		if(nb >= 0)
			return "+";
		return "-";
	}

	public String comparePlayCount(User previousUser)
	{
		if(previousUser == null)
			return "";
		int delta = this.getPlaycount() - previousUser.getPlaycount();
		if(delta == 0)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	public String compareRankedScore(User previousUser)
	{
		if(previousUser == null)
			return "";
		long delta = this.getRankedScore() - previousUser.getRankedScore();
		if(delta == 0L)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	public String compareTotalScore(User previousUser)
	{
		if(previousUser == null)
			return "";
		long delta = this.getTotalScore() - previousUser.getTotalScore();
		if(delta == 0L)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	public String comparePP(User previousUser)
	{
		if(previousUser == null)
			return "";
		double delta = this.getPp() - previousUser.getPp();
		if(delta == 0D)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	public String compareAccuracy(User previousUser)
	{
		if(previousUser == null)
			return "";
		double delta = this.getAccuracy() - previousUser.getAccuracy();
		if(delta == 0D)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	public String compareTotalHits(User previousUser)
	{
		if(previousUser == null)
			return "";
		long delta = this.getTotalHits() - previousUser.getTotalHits();
		if(delta == 0L)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	public String compareRank(User previousUser)
	{
		if(previousUser == null)
			return "";
		double delta = this.getRank() - previousUser.getRank();
		if(delta == 0D)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}
}
