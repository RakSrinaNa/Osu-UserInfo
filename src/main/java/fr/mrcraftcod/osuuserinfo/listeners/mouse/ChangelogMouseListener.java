package mrcraftcod.osuuserinfo.listeners.mouse;

import mrcraftcod.osuuserinfo.utils.Utils;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Used to open changelog frame.
 *
 * @author MrCraftCod
 */
public class ChangelogMouseListener implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		Utils.getAllChangelogFrame(Utils.mainFrame);
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}
}
