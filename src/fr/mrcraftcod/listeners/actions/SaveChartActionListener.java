package fr.mrcraftcod.listeners.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import fr.mrcraftcod.utils.Utils;

public class SaveChartActionListener implements ActionListener
{
	private JFreeChart chart;
	private String user;
	private String type;

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
			JOptionPane.showMessageDialog((Component) e.getSource(), String.format(Utils.resourceBundle.getString("save_error"), path.getName()), Utils.resourceBundle.getString("save_error_title"), JOptionPane.ERROR_MESSAGE);
		}
	}
}
