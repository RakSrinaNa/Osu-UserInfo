package fr.mrcraftcod.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import fr.mrcraftcod.utils.Utils;

public class AutoUpdateItemListener implements ItemListener
{
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		if(e.getSource() instanceof JCheckBox)
			Utils.setThreadUpdater(((JCheckBox) e.getSource()).isSelected());
	}
}