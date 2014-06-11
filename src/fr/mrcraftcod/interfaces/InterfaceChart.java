package fr.mrcraftcod.interfaces;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.text.NumberFormat;
import java.util.List;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import fr.mrcraftcod.objects.Stats;
import fr.mrcraftcod.utils.Utils;

public class InterfaceChart extends JFrame
{
	private static final long serialVersionUID = -5220915498588371099L;

	public InterfaceChart(String user, String mode, List<Stats> stats)
	{
		super();
		XYSeriesCollection dataset = processStats(stats);
		JFreeChart chart = ChartFactory.createXYLineChart("Stats", "Time", "Values", dataset, PlotOrientation.VERTICAL, true, true, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		XYPlot plot = (XYPlot) chart.getPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, true);
		plot.setRenderer(renderer);
		renderer.setBaseShapesVisible(false);
		renderer.setBaseShapesFilled(true);
		Stroke stroke = new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
		renderer.setBaseOutlineStroke(stroke);
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(2);
		XYItemLabelGenerator generator = new StandardXYItemLabelGenerator(StandardXYItemLabelGenerator.DEFAULT_ITEM_LABEL_FORMAT, format, format);
		renderer.setBaseItemLabelGenerator(generator);
		renderer.setBaseItemLabelsVisible(false);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);
		setVisible(true);
		pack();
	}

	public InterfaceChart getFrame()
	{
		return this;
	}

	private XYSeriesCollection processStats(List<Stats> stats)
	{
		XYSeries seriePP = new XYSeries("PP");
		XYSeries serieAccuracy = new XYSeries(Utils.resourceBundle.getString("accuracy"));
		XYSeries serie300Percent = new XYSeries("300%");
		XYSeries serie100Percent = new XYSeries("100%");
		XYSeries serie50Percent = new XYSeries("50%");
		for(Stats stat : stats)
		{
			seriePP.add(stat.getDate(), stat.getPp());
			serieAccuracy.add(stat.getDate(), stat.getAccuracy());
			serie300Percent.add(stat.getDate(), stat.getCount300() * 100 / stat.getTotalHits());
			serie100Percent.add(stat.getDate(), stat.getCount100() * 100 / stat.getTotalHits());
			serie50Percent.add(stat.getDate(), stat.getCount50() * 100 / stat.getTotalHits());
		}
		XYSeriesCollection collection = new XYSeriesCollection();
		collection.addSeries(seriePP);
		collection.addSeries(serieAccuracy);
		collection.addSeries(serie300Percent);
		collection.addSeries(serie100Percent);
		collection.addSeries(serie50Percent);
		return collection;
	}
}
