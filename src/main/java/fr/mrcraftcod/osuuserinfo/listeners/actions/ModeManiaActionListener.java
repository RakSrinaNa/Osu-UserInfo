package mrcraftcod.osuuserinfo.listeners.actions;

import mrcraftcod.osuuserinfo.utils.Utils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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