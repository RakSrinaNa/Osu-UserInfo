package mrcraftcod.osuuserinfo.listeners.mouse;

import mrcraftcod.osuuserinfo.utils.Utils;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Used to open the user's profile page.
 *
 * @author MrCraftCod
 */
public class OpenProfileMouseListener implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent arg0)
	{
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		if(arg0.getClickCount() > 1)
			Utils.openUserProfile(Utils.lastUser);
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
	}
}
