package fr.mrcraftcod.objects;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.jfree.chart.JFreeChart;
import fr.mrcraftcod.listeners.actions.SaveChartActionListener;
import fr.mrcraftcod.utils.Utils;

public class ChartPopupMenu extends JPopupMenu
{
	private static final long serialVersionUID = -948953601077699991L;

	public ChartPopupMenu(JFreeChart chart, String user, String type)
	{
		JMenuItem save = new JMenuItem(Utils.resourceBundle.getString("save_graph"));
		save.addActionListener(new SaveChartActionListener(chart, user, type));
		this.add(save);
	}
}
