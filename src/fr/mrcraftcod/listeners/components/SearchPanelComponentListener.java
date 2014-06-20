package fr.mrcraftcod.listeners.components;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;

/**
 * Used to correct a huge white space when frame is too big. Need to be fixed //TODO Fix blank gap
 *
 * @author MrCraftCod
 */
public class SearchPanelComponentListener implements ComponentListener
{
	@Override
	public void componentHidden(ComponentEvent e)
	{}

	@Override
	public void componentMoved(ComponentEvent e)
	{}

	@Override
	public void componentResized(ComponentEvent e)
	{
		if(e.getSource() instanceof JPanel)
		{
			JPanel p = (JPanel) e.getSource();
			Dimension d = p.getSize();
			d.height = 36;
			p.setSize(d);
		}
	}

	@Override
	public void componentShown(ComponentEvent e)
	{}
}