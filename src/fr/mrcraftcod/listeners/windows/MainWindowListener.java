package fr.mrcraftcod.listeners.windows;

import java.awt.AWTException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import fr.mrcraftcod.objects.SystemTrayOsuStats;
import fr.mrcraftcod.utils.Utils;

public class MainWindowListener implements WindowListener
{
	@Override
	public void windowActivated(final WindowEvent event)
	{}

	@Override
	public void windowClosed(final WindowEvent event)
	{}

	@Override
	public void windowClosing(final WindowEvent event)
	{
		Utils.exit();
	}

	@Override
	public void windowDeactivated(final WindowEvent event)
	{}

	@Override
	public void windowDeiconified(final WindowEvent event)
	{}

	@Override
	public void windowIconified(final WindowEvent event)
	{
		try
		{
			if(Utils.config.getBoolean("reduceTray", false))
			{
				SystemTrayOsuStats.add();
				Utils.mainFrame.hideFrame();
				Utils.mainFrame.getFrame().setVisible(false);
			}
		}
		catch(final AWTException exception)
		{}
	}

	@Override
	public void windowOpened(final WindowEvent event)
	{}
}
