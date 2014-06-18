package fr.mrcraftcod.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

/**
 * Task to fetch user info every 15 seconds.
 *
 * @author MrCraftCod
 */
public class TaskUpdater extends TimerTask
{
	private Timer timer;

	/**
	 * Constructor.
	 */
	public TaskUpdater()
	{
		try
		{
			Utils.logger.log(Level.INFO, "Starting updater task...");
			this.timer = new Timer();
			this.timer.schedule(this, 0, 15 * 1000);
		}
		catch(Exception e)
		{
			Utils.logger.log(Level.WARNING, "Failed to start updater task", e);
		}
	}

	/**
	 * Run the task.
	 *
	 * @see TimerTask#run()
	 */
	@Override
	public void run()
	{
		Utils.mainFrame.updateInfos(false);
	}

	/**
	 * Stop the current task.
	 */
	public void stop()
	{
		Utils.logger.log(Level.INFO, "Stopping updater Thread");
		if(this.timer != null)
		{
			this.timer.cancel();
			this.timer.purge();
			this.timer = null;
		}
	}
}
