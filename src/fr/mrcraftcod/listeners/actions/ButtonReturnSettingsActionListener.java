package fr.mrcraftcod.listeners.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import fr.mrcraftcod.interfaces.InterfaceSettings;

public class ButtonReturnSettingsActionListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() instanceof InterfaceSettings)
			((InterfaceSettings) e.getSource()).returnMain(true);
	}
}
