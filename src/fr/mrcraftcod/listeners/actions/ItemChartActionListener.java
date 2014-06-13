package fr.mrcraftcod.listeners.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import fr.mrcraftcod.interfaces.InterfaceChart;
import fr.mrcraftcod.objects.Stats;
import fr.mrcraftcod.utils.Utils;

public class ItemChartActionListener extends AbstractAction
{
	private static final long serialVersionUID = -3935741960271142168L;

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		List<Stats> stats = Utils.lastUser.getAllStats(Utils.mainFrame.getSelectedMode());
		if(stats == null || stats.size() < 1)
			JOptionPane.showMessageDialog(Utils.mainFrame, Utils.resourceBundle.getString("select_user_chart"), Utils.resourceBundle.getString("select_user_chart_title"), JOptionPane.ERROR_MESSAGE);
		else
			new InterfaceChart(Utils.lastUser.getUsername(), "Osu!", stats);
	}
}
