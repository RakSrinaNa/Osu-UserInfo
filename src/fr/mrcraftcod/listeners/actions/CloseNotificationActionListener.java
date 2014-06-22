package fr.mrcraftcod.listeners.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

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
		((JFrame) arg0.getSource()).dispose();
	}
}
