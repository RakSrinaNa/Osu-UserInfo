package fr.mrcraftcod.listeners.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import fr.mrcraftcod.utils.Utils;

public class AutoUpdateActionListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if(arg0.getSource() instanceof JCheckBox)
		{
			JCheckBox check = (JCheckBox) arg0.getSource();
			Utils.setThreadUpdater(check.isSelected());
		}
	}
}
