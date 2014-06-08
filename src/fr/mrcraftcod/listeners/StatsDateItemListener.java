package fr.mrcraftcod.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import fr.mrcraftcod.utils.Utils;

public class StatsDateItemListener implements ItemListener
{
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		if(e.getStateChange() != 1)
			return;
		Utils.mainFrame.updateInfos(Utils.lastUser.getUsername(), Utils.lastUser.getLastStats(Utils.mainFrame.getSelectedMode()), Utils.lastUser.getStatsByModeAndDate(Utils.mainFrame.getSelectedMode(), Utils.mainFrame.getSelectedDate()));
	}
}
