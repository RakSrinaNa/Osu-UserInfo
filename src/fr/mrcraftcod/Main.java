package fr.mrcraftcod;

import java.io.IOException;
import fr.mrcraftcod.utils.Utils;

/**
 * <h1>Osu!UserInfo</h1>
 * <p>
 * <a href="https://osu.ppy.sh/forum/p/3094583">Osu!UserInfo</a> is a simple program that will give you information (and track them) about a Osu! user!
 * </p>
 * <h2>Informations given/tracked</h2>
 * <p>
 * <ul>
 * <li>
 * Play count</li>
 * <li>
 * Scores</li>
 * <li>
 * PP</li>
 * <li>
 * Accuracy</li>
 * <li>
 * Country</li>
 * <li>
 * Rank</li>
 * <li>
 * Total hits</li>
 * <li>
 * Number of 300s, 100s, 50s</li>
 * <li>
 * Number of SS, S, A</li>
 * </ul>
 * </p>
 * <h2>How to help that project?</h2>
 * <p>
 * If you find any bugs, please <a href="https://bitbucket.org/MrCraftCod/osuuserinfo/issues">report them here</a> <br>
 * If you want, you can translate this project. <a href="https://bitbucket.org/MrCraftCod/osuuserinfo/fork">Fork the repository</a> and add your language file in src/resources/lang/
 * </p>
 *
 * @author MrCraftCod
 * @version 1.7
 */
public class Main
{
	public final static String APPNAME = "Osu!UserInfo";
	public final static String VERSION = "1.7b5";

	/**
	 * Start the program.
	 *
	 * @param args <ul>
	 *            <li>nosocket : Allow to run multiple instances of the program</li>
	 *            <li>noupdate : Don't check updates</li>
	 *            <li>noapi : Don't verify the API key</li>
	 *            </ul>
	 * @throws IOException If there were an error during startup.
	 */
	public static void main(String[] args) throws IOException
	{
		Utils.init(args);
	}
}
