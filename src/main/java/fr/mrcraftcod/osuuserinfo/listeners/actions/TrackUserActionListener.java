package fr.mrcraftcod.osuuserinfo.listeners.actions;

import fr.mrcraftcod.osuuserinfo.utils.Utils;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Used to track or untrack a user.
 *
 * @author MrCraftCod
 */
public class TrackUserActionListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if(arg0.getSource() instanceof JCheckBox)
		{
			JCheckBox check = (JCheckBox) arg0.getSource();
			if(check.isSelected())
				try
				{
					Utils.trackNewUser(Utils.lastUser);
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			else
				Utils.unTrackUser(Utils.lastUser);
		}
	}
}
