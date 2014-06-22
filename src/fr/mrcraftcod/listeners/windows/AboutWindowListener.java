package fr.mrcraftcod.listeners.windows;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import fr.mrcraftcod.utils.Utils;

/**
 * Used to show back the main frame.
 *
 * @author MrCraftCod
 */
public class AboutWindowListener implements WindowListener
{
	@Override
	public void windowActivated(WindowEvent arg0)
	{}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		Utils.mainFrame.showFrame();
		Utils.aboutFrame = null;
	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		Utils.mainFrame.showFrame();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0)
	{}

	@Override
	public void windowDeiconified(WindowEvent arg0)
	{}

	@Override
	public void windowIconified(WindowEvent arg0)
	{}

	@Override
	public void windowOpened(WindowEvent arg0)
	{}
}
