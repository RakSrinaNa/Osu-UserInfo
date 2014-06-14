package fr.mrcraftcod.listeners.mouse;

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
			Utils.openUserProfile(Utils.lastUser);
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{}
}
