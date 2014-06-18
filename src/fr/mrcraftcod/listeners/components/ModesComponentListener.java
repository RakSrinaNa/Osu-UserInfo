package fr.mrcraftcod.listeners.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;
import fr.mrcraftcod.objects.JButtonMode;

public class ModesComponentListener implements ComponentListener
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
		if(e.getComponent() instanceof JPanel)
		{
			int offset = 3;
			JPanel panel = (JPanel) e.getComponent();
			for(Component comp : panel.getComponents())
				if(comp instanceof JButtonMode)
				{
					JButtonMode but = (JButtonMode) comp;
					Dimension dim = but.getSize();
					dim.setSize((panel.getSize().getWidth() / panel.getComponentCount()) - offset, dim.getHeight());
					but.setSize(dim);
					but.setMinimumSize(dim);
					but.setPreferredSize(dim);
					but.setMaximumSize(dim);
				}
		}
	}

	@Override
	public void componentShown(ComponentEvent e)
	{}
}