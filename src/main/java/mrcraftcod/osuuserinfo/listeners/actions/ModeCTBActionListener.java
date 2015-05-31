package mrcraftcod.osuuserinfo.listeners.actions;

import mrcraftcod.osuuserinfo.utils.Utils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Used to set the mode to CTB.
 *
 * @author MrCraftCod
 */
public class ModeCTBActionListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Utils.mainFrame.switchMode(2, true);
	}
}
