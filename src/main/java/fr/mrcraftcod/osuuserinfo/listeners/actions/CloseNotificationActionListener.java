package fr.mrcraftcod.osuuserinfo.listeners.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Used to close the notification frame.
 *
 * @author MrCraftCod
 */
public class CloseNotificationActionListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		((JDialog) SwingUtilities.getRoot((JButton) arg0.getSource())).dispose();
	}
}
