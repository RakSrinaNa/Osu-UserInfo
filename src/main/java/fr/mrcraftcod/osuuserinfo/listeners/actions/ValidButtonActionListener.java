package mrcraftcod.osuuserinfo.listeners.actions;

import mrcraftcod.osuuserinfo.utils.Utils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Used to search user infos.
 *
 * @author MrCraftCod
 */
public class ValidButtonActionListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Utils.getInfos(true);
	}
}
