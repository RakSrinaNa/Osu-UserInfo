package fr.mrcraftcod.listeners.mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import fr.mrcraftcod.utils.Utils;

/**
 * Used to open changelog frame.
 *
 * @author MrCraftCod
 */
public class ChangelogMouseListener implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent e)
	{}

	@Override
	public void mouseEntered(MouseEvent e)
	{}

	@Override
	public void mouseExited(MouseEvent e)
	{}

	@Override
	public void mousePressed(MouseEvent e)
	{
		Utils.getAllChangelogFrame();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{}
}
