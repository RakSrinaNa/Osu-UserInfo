package fr.mrcraftcod.osuuserinfo.listeners.actions;

import fr.mrcraftcod.osuuserinfo.utils.Utils;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;

/**
 * Called when the user want to save a graph.
 *
 * @author MrCraftCod
 */
public class SaveChartActionListener implements ActionListener
{
	private final JFreeChart chart;
	private final String user;
	private final String type;

	/**
	 * Constructor.
	 *
	 * @param chart The chart to save.
	 * @param user The name of the user.
	 * @param type What the graph is about.
	 */
	public SaveChartActionListener(JFreeChart chart, String user, String type)
	{
		this.chart = chart;
		this.user = user;
		this.type = type;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		File path = Utils.getNewFilePatch(new File("."), JFileChooser.DIRECTORIES_ONLY, null);
		if(path == null)
			return;
		path.mkdirs();
		path = new File(path.getAbsolutePath(), this.user + "_" + this.type + "_" + String.valueOf(new Date().getTime()) + ".png");
		try
		{
			ChartUtilities.saveChartAsPNG(path, this.chart, 1000, 700);
		}
		catch(IOException e1)
		{
			JOptionPane.showMessageDialog((Component) e.getSource(), MessageFormat.format(Utils.resourceBundle.getString("save_error"), path.getName()), Utils.resourceBundle.getString("save_error_title"), JOptionPane.ERROR_MESSAGE);
		}
	}
}
