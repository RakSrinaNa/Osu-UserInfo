package fr.mrcraftcod.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import fr.mrcraftcod.utils.Utils;

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