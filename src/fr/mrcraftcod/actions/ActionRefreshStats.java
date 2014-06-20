package fr.mrcraftcod.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import fr.mrcraftcod.utils.Utils;

/**
 * Used to refresh infos (on F5 press).
 *
 * @author MrCraftCod
 */
public class ActionRefreshStats extends AbstractAction
{
	private static final long serialVersionUID = -3422845002112474989L;

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Utils.getInfos(false);
	}
}