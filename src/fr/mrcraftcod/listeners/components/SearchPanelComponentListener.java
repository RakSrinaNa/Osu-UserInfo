package fr.mrcraftcod.listeners.components;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;

public class SearchPanelComponentListener implements ComponentListener
{
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
	public void componentMoved(ComponentEvent e)
	{}

	@Override
	public void componentShown(ComponentEvent e)
	{}

	@Override
	public void componentHidden(ComponentEvent e)
	{}
}