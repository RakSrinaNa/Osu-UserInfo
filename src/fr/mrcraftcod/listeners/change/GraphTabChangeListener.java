package fr.mrcraftcod.listeners.change;

import java.util.Date;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import fr.mrcraftcod.utils.Utils;

public class GraphTabChangeListener implements ChangeListener
{
	@Override
	public void stateChanged(ChangeEvent e)
	{
		JTabbedPane pane = (JTabbedPane) e.getSource();
		if(pane.getSelectedComponent() instanceof ChartPanel)
		{
			ChartPanel prevChartPanel = Utils.chartFrame.getLastChart();
			ChartPanel newChartPanel = (ChartPanel) pane.getSelectedComponent();
			JFreeChart prevChart = prevChartPanel.getChart();
			JFreeChart newChart = newChartPanel.getChart();
			XYPlot prevXYPlot = prevChart.getXYPlot();
			XYPlot newXYPlot = newChart.getXYPlot();
			DateAxis prevAxisDate = (DateAxis) prevXYPlot.getDomainAxis();
			DateAxis newAxisDate = (DateAxis) newXYPlot.getDomainAxis();
			Date min = prevAxisDate.getMinimumDate();
			Date max = prevAxisDate.getMaximumDate();
			newAxisDate.setMinimumDate(min);
			newAxisDate.setMaximumDate(max);
			Utils.chartFrame.updateLastChart();
		}
	}
}
