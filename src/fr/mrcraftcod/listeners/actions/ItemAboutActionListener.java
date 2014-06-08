package fr.mrcraftcod.listeners.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import fr.mrcraftcod.interfaces.InterfaceAbout;
import fr.mrcraftcod.utils.Utils;

public class ItemAboutActionListener extends AbstractAction
{
	private static final long serialVersionUID = 2496517330411797001L;

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		new InterfaceAbout(Utils.mainFrame.getFrame());
	}
}
