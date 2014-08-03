package fr.mrcraftcod.listeners.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import fr.mrcraftcod.frames.AboutFrame;
import fr.mrcraftcod.utils.Utils;

/**
 * Used to open the about frame.
 *
 * @author MrCraftCod
 */
public class ItemAboutActionListener extends AbstractAction
{
	private static final long serialVersionUID = 2496517330411797001L;

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Utils.aboutFrame = new AboutFrame(Utils.mainFrame);
	}
}
