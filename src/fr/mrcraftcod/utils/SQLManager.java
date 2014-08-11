package fr.mrcraftcod.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class SQLManager
{
	public static final String TABLE = "OsuUserInfo", CREATE_TABLE_PLAYERS = "CREATE TABLE IF NOT EXISTS " + TABLE + "(Frm VARCHAR(255),UserID INT(20) UNSIGNED,Username VARCHAR(255),GameMode INT(1) UNSIGNED, Time DATETIME)ENGINE=InnoDB DEFAULT CHARSET=utf8;";
	private Connection con;

	public SQLManager(String databaseURL, int port, String databaseName, String user, String password) throws SQLException
	{
		Utils.logger.log(Level.INFO, "Initializing SQL connection...");
		this.con = DriverManager.getConnection("jdbc:mysql://" + databaseURL + ":" + port + "/" + databaseName, user, password);
		sendUpdateRequest(CREATE_TABLE_PLAYERS);
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return result;
	}
}
