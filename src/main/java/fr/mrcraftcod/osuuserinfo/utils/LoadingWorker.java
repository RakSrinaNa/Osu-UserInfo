package fr.mrcraftcod.osuuserinfo.utils;

import fr.mrcraftcod.osuuserinfo.frames.LoadingFrame;
import fr.mrcraftcod.osuuserinfo.listeners.components.FollowComponentListener;
import javax.swing.*;

/**
 * Used to fetch user infos in the background.
 *
 * @author MrCraftCod
 */
public class LoadingWorker extends SwingWorker<Boolean, String>
{
	private final String user;
	private final boolean hard;
	private final boolean forceDisplay;
	private final boolean forceFetch;
	private LoadingFrame frame;

	/**
	 * Constructor.
	 *
	 * @param parent The parent frame.
	 * @param user The user to fetch (username).
	 * @param hard Fetch the infos in 'hard mode'.
	 * @param openFrame Open or not the loading frame.
	 * @param forceDisplay Force the function to update stats on screen even if they are the same.
	 * @param forceFetch Force the program to fetch datas even if the cooldown time isn't finished?
	 */
	public LoadingWorker(JFrame parent, String user, boolean hard, boolean openFrame, boolean forceDisplay, boolean forceFetch)
	{
		this.user = user;
		this.hard = hard;
		this.forceDisplay = forceDisplay;
		this.forceFetch = forceFetch;
		Utils.mainFrame.allowNewSearch(false);
		if(openFrame)
		{
			this.frame = new LoadingFrame(parent);
			parent.addComponentListener(new FollowComponentListener(this.frame, this.frame.getIcon().getIconWidth(), this.frame.getIcon().getIconHeight()));
		}
	}

	/**
	 * Fetch the infos.
	 *
	 * @see SwingWorker#doInBackground()
	 */
	@Override
	protected Boolean doInBackground() throws Exception
	{
		return Utils.getInfosServer(this.user, this.hard, this.forceDisplay, this.forceFetch);
	}

	/**
	 * Return to main frame when done.
	 *
	 * @see SwingWorker#done()
	 */
	@Override
	protected void done()
	{
		Utils.mainFrame.allowNewSearch(true);
		if(this.frame != null)
			this.frame.dispose();
	}
}
