package fr.mrcraftcod;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class Stats implements Serializable
{
	private static final long serialVersionUID = -3548705459172185871L;
	private int playcount;
	private long rankedScore;
	private long totalScore;
	private double pp;
	private double accuracy;
	private long totalHits;
	private double rank;
	private long date;

	public Stats()
	{}

	public double getRank()
	{
		return rank;
	}

	public void setRank(double rank)
	{
		this.rank = rank;
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

	public String comparePlayCount(Stats previousStats)
	{
		if(previousStats == null)
			return "";
		int delta = this.getPlaycount() - previousStats.getPlaycount();
		if(delta == 0)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	public String compareRankedScore(Stats previousStats)
	{
		if(previousStats == null)
			return "";
		long delta = this.getRankedScore() - previousStats.getRankedScore();
		if(delta == 0L)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	public String compareTotalScore(Stats previousStats)
	{
		if(previousStats == null)
			return "";
		long delta = this.getTotalScore() - previousStats.getTotalScore();
		if(delta == 0L)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	public String comparePP(Stats previousStats)
	{
		if(previousStats == null)
			return "";
		double delta = this.getPp() - previousStats.getPp();
		if(delta == 0D)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	public String compareAccuracy(Stats previousStats)
	{
		if(previousStats == null)
			return "";
		double delta = this.getAccuracy() - previousStats.getAccuracy();
		if(Interface.round(delta, 2) == 0D)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	public String compareTotalHits(Stats previousStats)
	{
		if(previousStats == null)
			return "";
		long delta = this.getTotalHits() - previousStats.getTotalHits();
		if(delta == 0L)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	public String compareRank(Stats previousStats)
	{
		if(previousStats == null)
			return "";
		double delta = previousStats.getRank() - this.getRank();
		if(delta == 0D)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	private String getSign(double nb)
	{
		if(nb >= 0)
			return "+";
		return "-";
	}

	public long getDate()
	{
		return date;
	}

	public void setDate(long date)
	{
		this.date = date;
	}

	public String getLastStatsDate(Stats previousStats)
	{
		if(previousStats == null)
			return "";
		long lastDate = previousStats.getDate();
		if(lastDate < 0)
			return "";
		return String.format(Main.resourceBundle.getString("last_stats_date"), DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM, Locale.getDefault()).format(new Date(lastDate)));
	}
}
