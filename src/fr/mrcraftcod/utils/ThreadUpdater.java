package fr.mrcraftcod.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class ThreadUpdater extends TimerTask
{
	private Timer timer;

	public ThreadUpdater()
	{
		try
		{
			Utils.logger.log(Level.INFO, "Starting updater task...");
			timer = new Timer();
			timer.schedule(this, 0, 15 * 1000);
		}
		catch(Exception e)
		{
			Utils.logger.log(Level.WARNING, "Failed to start updater task", e);
		}
	}

	@Override
	public void run()
	{
		Utils.mainFrame.getInfos(false);
	}

	public void stop()
	{
		Utils.logger.log(Level.FINE, "Stopping updater Thread");
		if(timer != null)
		{
			timer.cancel();
			timer.purge();
			timer = null;
		}
	}
}
