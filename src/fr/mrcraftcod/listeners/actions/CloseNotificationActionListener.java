package fr.mrcraftcod.listeners.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

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
		((JDialog) SwingUtilities.getRoot((JButton) arg0.getSource())).dispose();;
	}
}
