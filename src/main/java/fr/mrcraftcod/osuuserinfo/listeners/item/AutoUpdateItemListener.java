package fr.mrcraftcod.osuuserinfo.listeners.item;

import fr.mrcraftcod.osuuserinfo.utils.Utils;
import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Used to launch or stop the Updater task.
 *
 * @author MrCraftCod
 */
public class AutoUpdateItemListener implements ItemListener
{
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		if(e.getSource() instanceof JCheckBox)
			Utils.setThreadUpdater(((JCheckBox) e.getSource()).isSelected());
	}
}