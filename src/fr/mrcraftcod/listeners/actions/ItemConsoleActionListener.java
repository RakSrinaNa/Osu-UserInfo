package fr.mrcraftcod.listeners.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import fr.mrcraftcod.frames.Console;

/**
 * Used to open the about frame.
 *
 * @author MrCraftCod
 */
public class ItemConsoleActionListener extends AbstractAction
{
	private static final long serialVersionUID = 2496517330411797001L;

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		new Console();
	}
}
