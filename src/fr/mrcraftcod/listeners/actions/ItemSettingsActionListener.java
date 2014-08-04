package fr.mrcraftcod.listeners.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import fr.mrcraftcod.frames.SettingsFrame;
import fr.mrcraftcod.utils.Utils;

/**
 * Used to open the settings frame.
 *
 * @author MrCraftCod
 */
public class ItemSettingsActionListener extends AbstractAction
{
	private static final long serialVersionUID = 151605064904946278L;

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Utils.configFrame = new SettingsFrame(Utils.mainFrame);
	}
}
