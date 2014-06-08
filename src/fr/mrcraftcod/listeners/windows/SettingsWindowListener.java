package fr.mrcraftcod.listeners.windows;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import fr.mrcraftcod.interfaces.InterfaceSettings;

public class SettingsWindowListener implements WindowListener
{
	@Override
	public void windowOpened(WindowEvent e)
	{}

	@Override
	public void windowClosing(final WindowEvent e)
	{
		if(e.getSource() instanceof InterfaceSettings)
			((InterfaceSettings) e.getSource()).closeFrame();
	}

	@Override
	public void windowClosed(WindowEvent e)
	{}

	@Override
	public void windowIconified(WindowEvent e)
	{}

	@Override
	public void windowDeiconified(WindowEvent e)
	{}

	@Override
	public void windowActivated(WindowEvent e)
	{}

	@Override
	public void windowDeactivated(WindowEvent e)
	{}
}
