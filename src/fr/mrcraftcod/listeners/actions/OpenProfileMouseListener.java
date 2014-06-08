package fr.mrcraftcod.listeners.actions;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import fr.mrcraftcod.utils.Utils;

public class OpenProfileMouseListener implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent arg0)
	{}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{}

	@Override
	public void mouseExited(MouseEvent arg0)
	{}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		if(arg0.getClickCount() > 1)
			Utils.mainFrame.openUserProfile();
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{}
}
