package fr.mrcraftcod.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import fr.mrcraftcod.utils.Utils;

/**
 * Used to handle ENTER key on the text field.
 *
 * @author MrCraftCod
 */
public class UserNameTextFieldKeyListener implements KeyListener
{
	@Override
	public void keyPressed(KeyEvent arg0)
	{
		if(KeyEvent.VK_ENTER == arg0.getExtendedKeyCode())
			Utils.getInfos(true);
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{}

	@Override
	public void keyTyped(KeyEvent arg0)
	{}
}
