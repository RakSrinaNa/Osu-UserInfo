package fr.mrcraftcod.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import fr.mrcraftcod.utils.Utils;

/**
 * Object to store stats.
 *
 * @author MrCraftCod
 */
public class Stats implements Serializable, Cloneable
{
	private static final long serialVersionUID = -3548705459172185871L;
	private static final int STATS_VERSION = 2;
	private transient long totalHits;
	private transient double level;
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
	private double pp;
	private double rank;
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
		this.rank = 0;
		this.date = 0;
		this.level = 0;
		this.countSS = 0;
		this.countS = 0;
		this.countA = 0;
		this.count300 = 0;
		this.count100 = 0;
		this.count50 = 0;
	}

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
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
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
		double delta = getPp() - previousStats.getPp();
		if(delta == 0D)
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
		double delta = previousStats.getRank() - getRank();
		if(delta == 0D)
			return "";
		return "<font color=" + (delta >= 0 ? "green" : "red") + ">(" + getArrow(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")</font>";
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
		long delta = getTotalHits() - previousStats.getTotalHits();
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
		long delta = getTotalScore() - previousStats.getTotalScore();
		if(delta == 0L)
			return "";
		return " (" + getSign(delta) + NumberFormat.getInstance(Locale.getDefault()).format(Math.abs(delta)) + ")";
	}

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

	public long getCount100()
	{
		return this.count100;
	}

	public long getCount300()
	{
		return this.count300;
	}

	public long getCount50()
	{
		return this.count50;
	}

	public int getCountA()
	{
		return this.countA;
	}

	public int getCountS()
	{
		return this.countS;
	}

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

	public int getDiffPlayCount(Stats previousStats)
	{
		if(previousStats == null)
			return 0;
		return getPlayCount() - previousStats.getPlayCount();
	}

	public double getDiffPP(Stats previousStats)
	{
		if(previousStats == null)
			return 0;
		return getPp() - previousStats.getPp();
	}

	public double getDiffRank(Stats previousStats)
	{
		if(previousStats == null)
			return 0;
		return previousStats.getRank() - getRank();
	}

	public long getDiffTotalHits(Stats previousStats)
	{
		if(previousStats == null)
			return 0;
		return getTotalHits() - previousStats.getTotalHits();
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
		return String.format(Utils.resourceBundle.getString("last_stats_date"), DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM, Locale.getDefault()).format(new Date(lastDate)));
	}

	public double getLevel()
	{
		return this.level;
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
	public double getPp()
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

	public int getVersion()
	{
		return this.version;
	}

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
		updateTotalHits();
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

	public void setCount100(long count100)
	{
		this.count100 = count100;
	}

	public void setCount300(long count300)
	{
		this.count300 = count300;
	}

	public void setCount50(long count50)
	{
		this.count50 = count50;
	}

	public void setCountA(int countA)
	{
		this.countA = countA;
	}

	public void setCountS(int countS)
	{
		this.countS = countS;
	}

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

	public void setLevel(double level)
	{
		this.level = level;
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

	public void updateTotalHits()
	{
		setTotalHits(getCount300() + getCount100() + getCount50());
	}

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
		oos.flush();
	}
}
