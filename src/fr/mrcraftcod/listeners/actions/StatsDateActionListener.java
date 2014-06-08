package fr.mrcraftcod.listeners.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import fr.mrcraftcod.utils.Utils;

public class StatsDateActionListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getModifiers() == 0)
			return;
		Utils.mainFrame.updateTrackedInfos();
	}
}
