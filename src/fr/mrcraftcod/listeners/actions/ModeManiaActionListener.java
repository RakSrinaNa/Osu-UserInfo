package fr.mrcraftcod.listeners.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import fr.mrcraftcod.utils.Utils;

/**
 * Used to set the mode to Mania.
 *
 * @author MrCraftCod
 */
public class ModeManiaActionListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Utils.mainFrame.switchMode(3, true);
	}
}