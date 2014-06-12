package fr.mrcraftcod.listeners.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import fr.mrcraftcod.interfaces.InterfaceChart;
import fr.mrcraftcod.utils.Utils;

public class ItemChartActionListener extends AbstractAction
{
	private static final long serialVersionUID = -3935741960271142168L;

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		new InterfaceChart(Utils.lastUser.getUsername(), "Osu!", Utils.lastUser.getAllStats(Utils.mainFrame.getSelectedMode()));
	}
}
