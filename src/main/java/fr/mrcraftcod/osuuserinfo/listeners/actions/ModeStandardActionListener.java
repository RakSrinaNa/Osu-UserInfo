package fr.mrcraftcod.osuuserinfo.listeners.actions;

import fr.mrcraftcod.osuuserinfo.utils.Utils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Used to set the mode to Standard.
 *
 * @author MrCraftCod
 */
public class ModeStandardActionListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Utils.mainFrame.switchMode(0, true);
	}
}
