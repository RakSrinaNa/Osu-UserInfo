package fr.mrcraftcod.osuuserinfo.listeners.components;

import fr.mrcraftcod.osuuserinfo.frames.component.JButtonMode;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Used to resize modes buttons to the correct width (all 4 have the same)
 *
 * @author MrCraftCod
 */
public class ModesComponentListener implements ComponentListener
{
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
					dim.setSize(panel.getSize().getWidth() / panel.getComponentCount() - offset, dim.getHeight());
					but.setSize(dim);
					but.setMinimumSize(dim);
					but.setPreferredSize(dim);
					but.setMaximumSize(dim);
				}
		}
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
	}

	@Override
	public void componentHidden(ComponentEvent e)
	{
	}
}