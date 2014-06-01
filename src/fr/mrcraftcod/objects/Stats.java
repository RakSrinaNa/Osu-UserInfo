package fr.mrcraftcod.objects;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import fr.mrcraftcod.Main;
import fr.mrcraftcod.interfaces.Interface;

/**
 * Object to store stats.
 * 
 * @author MrCraftCod
 */
public class Stats implements Serializable
{
	private static final long serialVersionUID = -3548705459172185871L;
	private int mode;
	private int playcount;
	private long rankedScore;
	private long totalScore;
	private double pp;
	private double accuracy;
	private long totalHits;
	private double rank;
	private long date;

	/**
	 * Constructor.
	 */
	public Stats()
	{}

	/**
	 * Used to get rank.
	 * 
	 * @return The rank.
	 */
	public double getRank()
	{
		return rank;
	}

	/**
	 * Used to set the rank.
	 * 
	 * @param rank The rank to set.
	 */
	public void setRank(double rank)
	{
		this.rank = rank;
	}

	/**
	 * Used to get the number of hits.
	 * 
	 * @return The number of hits.
	 */
	public long getTotalHits()
	{
		return totalHits;
	}

	/**
	 * Used to set the number of hits.
	 * 
	 * @param totalHits The number of hits to set.
	 */
	public void setTotalHits(long totalHits)
	{
		this.totalHits = totalHits;
	}

	/**
	 * Used to get the accuracy.
	 * 
	 * @return The accuracy.
	 */
	public double getAccuracy()
	{
		return accuracy;
	}

	/**
	 * Used to set the accuracy.
	 * 
	 * @param accuracy The accuracy to set.
	 */
	public void setAccuracy(double accuracy)
	{
		this.accuracy = accuracy;
	}

	/**
	 * Used to set get the amount of pp.
	 * 
	 * @return The amount of pp.
	 */
	public double getPp()
	{
		return pp;
	}

	/**
	 * Used to set the amount of pp.
	 * 
	 * @param pp The amount of pp to set.
	 */
	public void setPp(double pp)
	{
		this.pp = pp;
	}

	/**
	 * Used to get the ranked score.
	 * 
	 * @return The ranked score.
	 */
	public long getRankedScore()
	{
		return rankedScore;
	}

	/**
	 * Used to set the ranked score.
	 * 
	 * @param rankedScore The ranked score to set.
	 */
	public void setRankedScore(long rankedScore)
	{
		this.rankedScore = rankedScore;
	}

	/**
	 * Used to get the total score.
	 * 
	 * @return The total score.
	 */
	public long getTotalScore()
	{
		return totalScore;
	}

	/**
	 * Used to set the total score.
	 * 
	 * @param totalScore The total score to set.
	 */
	public void setTotalScore(long totalScore)
	{
		this.totalScore = totalScore;
	}

	/**
	 * Used to get the play count.
	 * 
	 * @return The play count.
	 */
	public int getPlaycount()
	{
		return playcount;
	}

	/**
	 * Used to set the play count.
	 * 
	 * @param playcount The play count to set.
	 */
	public void setPlaycount(int playcount)
	{
		this.playcount = playcount;
	}

	/**
	 * Used to get a string showing the differences of the play count between this stats and an other one.
	 * 
	 * @param previousStats The stats to compare data.
	 * @return A String representing this difference, an empty string if there isn't any changes.
	 */
	public String comparePlayCount(Stats previousStats)
	{
		if(previousStats == null)
			return "";
		int delta = this.getPlaycount() - previousStats.getPlaycount();
		if(delta == 0)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	/**
	 * Used to get a string showing the differences of the ranked score between this stats and an other one.
	 * 
	 * @param previousStats The stats to compare data.
	 * @return A String representing this difference, an empty string if there isn't any changes.
	 */
	public String compareRankedScore(Stats previousStats)
	{
		if(previousStats == null)
			return "";
		long delta = this.getRankedScore() - previousStats.getRankedScore();
		if(delta == 0L)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	/**
	 * Used to get a string showing the differences of the total score between this stats and an other one.
	 * 
	 * @param previousStats The stats to compare data.
	 * @return A String representing this difference, an empty string if there isn't any changes.
	 */
	public String compareTotalScore(Stats previousStats)
	{
		if(previousStats == null)
			return "";
		long delta = this.getTotalScore() - previousStats.getTotalScore();
		if(delta == 0L)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	/**
	 * Used to get a string showing the differences of the pp between this stats and an other one.
	 * 
	 * @param previousStats The stats to compare data.
	 * @return A String representing this difference, an empty string if there isn't any changes.
	 */
	public String comparePP(Stats previousStats)
	{
		if(previousStats == null)
			return "";
		double delta = this.getPp() - previousStats.getPp();
		if(delta == 0D)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	/**
	 * Used to get a string showing the differences of the accuracy between this stats and an other one.
	 * 
	 * @param previousStats The stats to compare data.
	 * @return A String representing this difference, an empty string if there isn't any changes.
	 */
	public String compareAccuracy(Stats previousStats)
	{
		if(previousStats == null)
			return "";
		double delta = this.getAccuracy() - previousStats.getAccuracy();
		if(Interface.round(delta, 2) == 0D)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	/**
	 * Used to get a string showing the differences of the number of hits between this stats and an other one.
	 * 
	 * @param previousStats The stats to compare data.
	 * @return A String representing this difference, an empty string if there isn't any changes.
	 */
	public String compareTotalHits(Stats previousStats)
	{
		if(previousStats == null)
			return "";
		long delta = this.getTotalHits() - previousStats.getTotalHits();
		if(delta == 0L)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	/**
	 * Used to get a string showing the differences of the rank between this stats and an other one.
	 * 
	 * @param previousStats The stats to compare data.
	 * @return A String representing this difference, an empty string if there isn't any changes.
	 */
	public String compareRank(Stats previousStats)
	{
		if(previousStats == null)
			return "";
		double delta = previousStats.getRank() - this.getRank();
		if(delta == 0D)
			return "";
		return " (" + getArrow(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

	/**
	 * Used to get the sign of a number.
	 * 
	 * @param number The number to get the sign.
	 * @return A String of the sign.
	 */
	private String getSign(double number)
	{
		if(number >= 0)
			return "+";
		return "-";
	}

	/**
	 * Used to get the arrow of a number.
	 * 
	 * @param number The number to get the sign.
	 * @return A String of the arrow.
	 */
	private String getArrow(double number)
	{
		if(number >= 0)
			return "\u2191";
		return "\u2193";
	}

	/**
	 * Used to get the date of the stats.
	 * 
	 * @return The date.
	 */
	public long getDate()
	{
		return date;
	}

	/**
	 * Used to set the date of the stats.
	 * 
	 * @param date The date to set.
	 */
	public void setDate(long date)
	{
		this.date = date;
	}

	/**
	 * Used to set the date of the stats.
	 * 
	 * @param date The date to set.
	 */
	public void setDate(Date date)
	{
		setDate(date.getTime());
	}

	/**
	 * Used to get a formated date of a stats object.
	 * 
	 * @param stats The stats to get the date.
	 * @return A formatted date string.
	 */
	public String getLastStatsDate(Stats stats)
	{
		if(stats == null)
			return "";
		long lastDate = stats.getDate();
		if(lastDate <= 0)
			return "";
		return String.format(Main.resourceBundle.getString("last_stats_date"), DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM, Locale.getDefault()).format(new Date(lastDate)));
	}

	/**
	 * Used to get the mode of the stats.
	 * 
	 * @return The number of the mode.
	 */
	public int getMode()
	{
		return mode;
	}

	/**
	 * Used to set the mode of the stats.
	 * 
	 * @param mode The mode to set.
	 */
	public void setMode(int mode)
	{
		this.mode = mode;
	}
}
