package fr.mrcraftcod.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import fr.mrcraftcod.utils.Configuration;
import fr.mrcraftcod.utils.Utils;

public class ActionGetFavInfos extends AbstractAction
{
	private static final long serialVersionUID = -8858167297002187511L;

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(Utils.config.getString(Configuration.FAVOURITEUSER, "").equals(""))
			return;
		Utils.mainFrame.setTextUser(Utils.config.getString(Configuration.FAVOURITEUSER, ""));
		Utils.getInfos(false);
	}
}
