package fr.mrcraftcod.osuuserinfo.frames.component;

import fr.mrcraftcod.osuuserinfo.listeners.actions.SaveChartActionListener;
import fr.mrcraftcod.osuuserinfo.utils.Utils;
import org.jfree.chart.JFreeChart;
import javax.swing.*;

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
