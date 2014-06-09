package fr.mrcraftcod.listeners.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import fr.mrcraftcod.utils.Utils;

public class ValidButtonActionListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Utils.mainFrame.getInfos(true);
	}
}
