package fr.mrcraftcod.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import fr.mrcraftcod.objects.Stats;
import fr.mrcraftcod.objects.User;

public class SQLManager
{
	private Connection con;

	public SQLManager(String databaseURL, int port, String databaseName, String user, String password) throws SQLException
	{
		Utils.logger.log(Level.INFO, "Initializing SQL connection...");
		this.con = DriverManager.getConnection("jdbc:mysql://" + databaseURL + ":" + port + "/" + databaseName, user, password);
	}

	public ResultSet getUserStats(int userID, int mode)
	{
		return sendQuerryRequest("SELECT Date,PP,Rank,Country_rank,Lvl,Ranked_score,Total_score,Count300,Count100,Count50,CountSS,CountS,CountA,Playcount,Maximum_combo,Accuracy FROM " + userID + "_" + mode + " ORDER BY Date ASC" + (Utils.numberTrackedStatsToKeep > 0 ? " LIMIT " + Utils.numberTrackedStatsToKeep : "") + ";");
	}

	public ResultSet sendQuerryRequest(String request)
	{
		ResultSet result = null;
		try
		{
			Statement stmt = this.con.createStatement();
			result = stmt.executeQuery(request);
		}
		catch(SQLException e)
		{
			Utils.logger.log(Level.WARNING, "SQL ERROR", e);
		}
		return result;
	}

	public int sendUpdateRequest(String request)
	{
		int result = 0;
		try
		{
			Statement stmt = this.con.createStatement();
			result = stmt.executeUpdate(request);
		}
		catch(SQLException e)
		{
			Utils.logger.log(Level.WARNING, "SQL ERROR", e);
		}
		return result;
	}

	public int sendUser(User currentUser, Stats currentStats, String macString)
	{
		String table = currentUser.getUserID() + "_" + currentStats.getMode();
		String request1 = "CREATE TABLE IF NOT EXISTS " + table + "(Date VARCHAR(20), Frm VARCHAR(17), PP DECIMAL(8,3) UNSIGNED, Rank INT(9) UNSIGNED, Country_rank INT(9), Lvl DECIMAL(10,5) UNSIGNED, Ranked_score VARCHAR(36), Total_score VARCHAR(36), Total_hits VARCHAR(36), Count300 VARCHAR(36), Count100 VARCHAR(36), Count50 VARCHAR(36), Playcount INT(7) UNSIGNED, CountSS INT(7) UNSIGNED, CountS INT(7) UNSIGNED, CountA INT(7) UNSIGNED, Maximum_combo INT(5) UNSIGNED, Accuracy VARCHAR(36)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		String request2 = "INSERT INTO " + table + " SET Date='" + currentStats.getDate() + "', Frm='" + macString + "', PP='" + currentStats.getPP() + "', Rank='" + currentStats.getRank() + "', Country_rank='" + currentStats.getCountryRank() + "', Lvl='" + currentStats.getLevel() + "', Ranked_score='" + currentStats.getRankedScore() + "', Total_score='" + currentStats.getTotalScore() + "', Total_hits='" + currentStats.getTotalHits() + "', Count300='" + currentStats.getCount300() + "', Count100='" + currentStats.getCount100() + "', Count50='" + currentStats.getCount50() + "', Playcount='" + currentStats.getPlayCount() + "', CountSS='" + currentStats.getCountSS() + "', CountS='" + currentStats.getCountS() + "', CountA='" + currentStats.getCountA() + "', Maximum_combo='" + currentStats.getMaximumCombo() + "', Accuracy='" + currentStats.getAccuracy() + "';";
		return sendUpdateRequest(request1) + sendUpdateRequest(request2);
	}
}
