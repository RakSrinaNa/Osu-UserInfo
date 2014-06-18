package fr.mrcraftcod.listeners.windows;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import fr.mrcraftcod.utils.Utils;

public class SettingsWindowListener implements WindowListener
{
	@Override
	public void windowActivated(WindowEvent e)
	{}

	@Override
	public void windowClosed(WindowEvent e)
	{
		Utils.configFrame = null;
	}

	@Override
	public void windowClosing(final WindowEvent e)
	{
		Utils.configFrame.closeFrame();
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{}

	@Override
	public void windowDeiconified(WindowEvent e)
	{}

	@Override
	public void windowIconified(WindowEvent e)
	{}

	@Override
	public void windowOpened(WindowEvent e)
	{}
}
