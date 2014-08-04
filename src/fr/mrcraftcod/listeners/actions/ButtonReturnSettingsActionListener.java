package fr.mrcraftcod.listeners.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import fr.mrcraftcod.frames.SettingsFrame;

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
