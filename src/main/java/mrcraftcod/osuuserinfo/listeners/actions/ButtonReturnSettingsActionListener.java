package mrcraftcod.osuuserinfo.listeners.actions;

import mrcraftcod.osuuserinfo.frames.SettingsFrame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		((SettingsFrame) SwingUtilities.getRoot((JButton) e.getSource())).returnMain(true);
	}
}
