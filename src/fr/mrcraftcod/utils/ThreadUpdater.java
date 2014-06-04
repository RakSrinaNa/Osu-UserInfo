package fr.mrcraftcod.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import fr.mrcraftcod.Main;

public class ThreadUpdater extends TimerTask
{
	private Timer timer;

	public ThreadUpdater()
	{
		try
		{
			Main.logger.log(Level.INFO, "Starting updater task...");
			timer = new Timer();
			timer.schedule(this, 0, 15 * 1000);
		}
		catch(Exception e)
		{
			Main.logger.log(Level.WARNING, "Failed to start updater task", e);
		}
	}

	@Override
	public void run()
	{
		Main.frame.refreshStats(false);
	}

	public void stop()
	{
		Main.logger.log(Level.FINE, "Stopping updater Thread");
		if(timer != null)
		{
			timer.cancel();
			timer.purge();
			timer = null;
		}
	}
}
