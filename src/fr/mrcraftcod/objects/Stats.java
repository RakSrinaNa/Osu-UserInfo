package fr.mrcraftcod.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Date;
import fr.mrcraftcod.utils.Utils;

/**
 * Object to store stats.
 *
 * @author MrCraftCod
 */
public class Stats implements Serializable, Cloneable
{
	private static final long serialVersionUID = -3548705459172185871L;
	private static final int STATS_VERSION = 5;
	private transient long totalHits;
	private double level;
	private long rankedScore;
	private long totalScore;
	private long date;
	private long count300;
	private long count100;
	private long count50;
	private int version;
	private int mode;
	private int playcount;
	private int countSS;
	private int countS;
	private int countA;
	private int maximumCombo;
	private double pp;
	private double rank;
	private double countryRank;
	private double accuracy;

	/**
	 * Constructor.
	 */
	public Stats()
	{
		this.version = STATS_VERSION;
		this.mode = 0;
		this.playcount = 0;
		this.rankedScore = 0;
		this.totalScore = 0;
		this.pp = 0;
		this.accuracy = 0;
		this.totalHits = 0;
		this.maximumCombo = 0;
		this.rank = 0;
		this.countryRank = 0;
		this.date = 0;
		this.level = 0;
		this.countSS = 0;
		this.countS = 0;
		this.countA = 0;
		this.count300 = 0;
		this.count100 = 0;
		this.count50 = 0;
	}

	/**
	 * @see Object#clone()
	 */
	@Override
	public Stats clone()
	{
		return this;
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
		double delta = getAccuracy() - previousStats.getAccuracy();
		if(Utils.round(delta, 2) == 0D)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Utils.locale).format(Math.abs(delta)) + ")";
	}

	/**
	 * Used to get a string showing the differences of the country rank between this stats and an other one.
	 *
	 * @param previousStats The stats to compare data.
	 * @return A String representing this difference, an empty string if there isn't any changes.
	 */
	public String compareCountryRank(Stats previousStats)
	{
		if(previousStats == null)
			return "";
		if(previousStats.getCountryRank() < 1)
			return "";
		double delta = previousStats.getCountryRank() - getCountryRank();
		if(delta == 0D)
			return "";
		return "<font color=" + (delta >= 0 ? "green" : "red") + ">(" + getArrow(delta) + NumberFormat.getInstance(Utils.locale).format(Math.abs(delta)) + ")</font>";
	}

	/**
	 * Used to get a string showing the differences of the maximum combo between this stats and an other one.
	 *
	 * @param previousStats The stats to compare data.
	 * @return A String representing this difference, an empty string if there isn't any changes.
	 */
	public String compareMaximumCombo(Stats previousStats)
	{
		if(previousStats == null)
			return "";
		long delta = getMaximumCombo() - previousStats.getMaximumCombo();
		if(delta == 0L)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Utils.locale).format(Math.abs(delta)) + ")";
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
		int delta = getPlayCount() - previousStats.getPlayCount();
		if(delta == 0)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Utils.locale).format(Math.abs(delta)) + ")";
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
		double delta = getPP() - previousStats.getPP();
		if(delta == 0D)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Utils.locale).format(Math.abs(delta)) + ")";
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
		if(previousStats.rank == 0)
			return "";
		double delta = previousStats.getRank() - getRank();
		if(delta == 0D)
			return "";
		return "<font color=" + (delta >= 0 ? "green" : "red") + ">(" + getArrow(delta) + NumberFormat.getInstance(Utils.locale).format(Math.abs(delta)) + ")</font>";
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
		long delta = getRankedScore() - previousStats.getRankedScore();
		if(delta == 0L)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Utils.locale).format(Math.abs(delta)) + ")";
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
		long delta = getTotalHits() - previousStats.getTotalHits();
		if(delta == 0L)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Utils.locale).format(Math.abs(delta)) + ")";
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
		long delta = getTotalScore() - previousStats.getTotalScore();
		if(delta == 0L)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Utils.locale).format(Math.abs(delta)) + ")";
	}

	/**
	 * Used to know if two Stats objects are the same.
	 *
	 * @param stats the Stats to compare with.
	 * @return True if stats are the same, false if not.
	 */
	public boolean equals(Stats stats)
	{
		if(stats == null)
			return false;
		if(getPlayCount() != stats.getPlayCount())
			return false;
		if(getTotalHits() != stats.getTotalHits())
			return false;
		if(getTotalScore() != stats.getTotalScore())
			return false;
		if(getRank() != stats.getRank())
			return false;
		return true;
	}

	/**
	 * Used to get the accuracy.
	 *
	 * @return The accuracy.
	 */
	public double getAccuracy()
	{
		return this.accuracy;
	}

	/**
	 * Used to get the count of 100 hits.
	 *
	 * @return The count of 100 hits.
	 */
	public long getCount100()
	{
		return this.count100;
	}

	/**
	 * Used to get the count of 300 hits.
	 *
	 * @return The count of 300 hits.
	 */
	public long getCount300()
	{
		return this.count300;
	}

	/**
	 * Used to get the count of 50 hits.
	 *
	 * @return The count of 50 hits.
	 */
	public long getCount50()
	{
		return this.count50;
	}

	/**
	 * Used to get the count of A ranks.
	 *
	 * @return The count of A ranks.
	 */
	public int getCountA()
	{
		return this.countA;
	}

	public double getCountryRank()
	{
		return this.countryRank;
	}

	/**
	 * Used to get the count of S ranks.
	 *
	 * @return The count of S ranks.
	 */
	public int getCountS()
	{
		return this.countS;
	}

	/**
	 * Used to get the count of SS ranks.
	 *
	 * @return The count of SS ranks.
	 */
	public int getCountSS()
	{
		return this.countSS;
	}

	/**
	 * Used to get the date of the stats.
	 *
	 * @return The date.
	 */
	public long getDate()
	{
		return this.date;
	}

	/**
	 * Used to get the difference of play count between stats.
	 *
	 * @param previousStats The stats to compare with.
	 * @return The difference.
	 */
	public int getDiffPlayCount(Stats previousStats)
	{
		if(previousStats == null)
			return 0;
		return getPlayCount() - previousStats.getPlayCount();
	}

	/**
	 * Used to get the difference of PP between stats.
	 *
	 * @param previousStats The stats to compare with.
	 * @return The difference.
	 */
	public double getDiffPP(Stats previousStats)
	{
		if(previousStats == null)
			return 0;
		return getPP() - previousStats.getPP();
	}

	/**
	 * Used to get the difference of rank between stats.
	 *
	 * @param previousStats The stats to compare with.
	 * @return The difference.
	 */
	public double getDiffRank(Stats previousStats)
	{
		if(previousStats == null)
			return 0;
		return previousStats.getRank() - getRank();
	}

	/**
	 * Used to get the difference of ranked score between stats.
	 *
	 * @param previousStats The stats to compare with.
	 * @return The difference.
	 */
	public long getDiffRankedScore(Stats previousStats)
	{
		if(previousStats == null)
			return 0;
		return getRankedScore() - previousStats.getRankedScore();
	}

	/**
	 * Used to get the difference of total hits between stats.
	 *
	 * @param previousStats The stats to compare with.
	 * @return The difference.
	 */
	public long getDiffTotalHits(Stats previousStats)
	{
		if(previousStats == null)
			return 0;
		return getTotalHits() - previousStats.getTotalHits();
	}

	/**
	 * Used to get the difference of total score between stats.
	 *
	 * @param previousStats The stats to compare with.
	 * @return The difference.
	 */
	public long getDiffTotalScore(Stats previousStats)
	{
		if(previousStats == null)
			return 0;
		return getTotalScore() - previousStats.getTotalScore();
	}

	/**
	 * Used to get the level.
	 *
	 * @return The level.
	 */
	public double getLevel()
	{
		return this.level;
	}

	/**
	 * @return the maximumCombo
	 */
	public int getMaximumCombo()
	{
		return this.maximumCombo;
	}

	/**
	 * Used to get the mode of the stats.
	 *
	 * @return The number of the mode.
	 */
	public int getMode()
	{
		return this.mode;
	}

	/**
	 * Used to get the play count.
	 *
	 * @return The play count.
	 */
	public int getPlayCount()
	{
		return this.playcount;
	}

	/**
	 * Used to set get the amount of pp.
	 *
	 * @return The amount of pp.
	 */
	public double getPP()
	{
		return this.pp;
	}

	/**
	 * Used to get rank.
	 *
	 * @return The rank.
	 */
	public double getRank()
	{
		return this.rank;
	}

	/**
	 * Used to get the ranked score.
	 *
	 * @return The ranked score.
	 */
	public long getRankedScore()
	{
		return this.rankedScore;
	}

	/**
	 * Used to get the number of hits.
	 *
	 * @return The number of hits.
	 */
	public long getTotalHits()
	{
		return this.totalHits;
	}

	/**
	 * Used to get the total score.
	 *
	 * @return The total score.
	 */
	public long getTotalScore()
	{
		return this.totalScore;
	}

	/**
	 * Used to get the version of the Stats object.
	 *
	 * @return The version.
	 */
	public int getVersion()
	{
		return this.version;
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
	 * Used to set the number of 100 hits.
	 *
	 * @param accuracy The number of 100 hits to set.
	 */
	public void setCount100(long count100)
	{
		this.count100 = count100;
		updateTotalHits();
	}

	/**
	 * Used to set the number of 300 hits.
	 *
	 * @param accuracy The number of 300 hits to set.
	 */
	public void setCount300(long count300)
	{
		this.count300 = count300;
		updateTotalHits();
	}

	/**
	 * Used to set the number of 50 hits.
	 *
	 * @param accuracy The number of 50 hits to set.
	 */
	public void setCount50(long count50)
	{
		this.count50 = count50;
		updateTotalHits();
	}

	/**
	 * Used to set the number of A ranks.
	 *
	 * @param accuracy The number of A ranks to set.
	 */
	public void setCountA(int countA)
	{
		this.countA = countA;
	}

	public void setCountryRank(double countryRank)
	{
		this.countryRank = countryRank;
	}

	/**
	 * Used to set the number of S ranks.
	 *
	 * @param accuracy The number of S ranks to set.
	 */
	public void setCountS(int countS)
	{
		this.countS = countS;
	}

	/**
	 * Used to set the number of SS ranks.
	 *
	 * @param accuracy The number of SS ranks to set.
	 */
	public void setCountSS(int countSS)
	{
		this.countSS = countSS;
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
	 * Used to set the date of the stats.
	 *
	 * @param date The date to set.
	 */
	public void setDate(long date)
	{
		this.date = date;
	}

	/**
	 * Used to set the level.
	 *
	 * @param level The level to set.
	 */
	public void setLevel(double level)
	{
		this.level = level;
	}

	/**
	 * @param maximumCombo the maximumCombo to set
	 */
	public void setMaximumCombo(int maximumCombo)
	{
		this.maximumCombo = maximumCombo;
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
	 * Used to set the amount of pp.
	 *
	 * @param pp The amount of pp to set.
	 */
	public void setPp(double pp)
	{
		this.pp = pp;
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
	 * Used to set the ranked score.
	 *
	 * @param rankedScore The ranked score to set.
	 */
	public void setRankedScore(long rankedScore)
	{
		this.rankedScore = rankedScore;
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
	 * Used to set the total score.
	 *
	 * @param totalScore The total score to set.
	 */
	public void setTotalScore(long totalScore)
	{
		this.totalScore = totalScore;
	}

	/**
	 * Used to update the number of total hits.
	 */
	public void updateTotalHits()
	{
		setTotalHits(getCount300() + getCount100() + getCount50());
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
	 * Called to read object from a file.
	 *
	 * @param ois
	 * @throws IOException
	 */
	private void readObject(ObjectInputStream ois) throws IOException
	{
		this.version = STATS_VERSION;
		int version = ois.readInt();
		if(version >= 2)
		{
			this.mode = ois.readInt();
			this.date = ois.readLong();
			this.playcount = ois.readInt();
			this.rankedScore = ois.readLong();
			this.totalScore = ois.readLong();
			this.rank = ois.readDouble();
			this.pp = ois.readDouble();
			this.accuracy = ois.readDouble();
			this.countSS = ois.readInt();
			this.countS = ois.readInt();
			this.countA = ois.readInt();
			this.count300 = ois.readLong();
			this.count100 = ois.readLong();
			this.count50 = ois.readLong();
		}
		if(version >= 3)
			this.level = ois.readDouble();
		if(version >= 4)
			this.maximumCombo = ois.readInt();
		if(version >= 5)
			this.countryRank = ois.readDouble();
		updateTotalHits();
	}

	/**
	 * Called to write the object in a file.
	 *
	 * @param ois
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.writeInt(this.version);
		oos.writeInt(this.mode);
		oos.writeLong(this.date);
		oos.writeInt(this.playcount);
		oos.writeLong(this.rankedScore);
		oos.writeLong(this.totalScore);
		oos.writeDouble(this.rank);
		oos.writeDouble(this.pp);
		oos.writeDouble(this.accuracy);
		oos.writeInt(this.countSS);
		oos.writeInt(this.countS);
		oos.writeInt(this.countA);
		oos.writeLong(this.count300);
		oos.writeLong(this.count100);
		oos.writeLong(this.count50);
		oos.writeDouble(this.level);
		oos.writeInt(this.maximumCombo);
		oos.writeDouble(this.countryRank);
		oos.flush();
	}
}
