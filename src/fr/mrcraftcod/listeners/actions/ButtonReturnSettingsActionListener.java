package fr.mrcraftcod.listeners.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import fr.mrcraftcod.utils.Utils;

/**
 * Used to close and save settings frame.
 *
 * @author MrCraftCod
 */
public class ButtonReturnSettingsActionListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Utils.configFrame.returnMain(true);
	}
}
