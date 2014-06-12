package fr.mrcraftcod.interfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.Date;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import fr.mrcraftcod.objects.Stats;
import fr.mrcraftcod.utils.Utils;

public class InterfaceChart extends JFrame
{
	private static final long serialVersionUID = -5220915498588371099L;

	public InterfaceChart(String user, String mode, List<Stats> stats)
	{
		super();
		JFreeChart chart = ChartFactory.createTimeSeriesChart("Stats", "Time", "Values", processStats(stats), true, true, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		XYPlot xyPlot = chart.getXYPlot();
		xyPlot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		NumberAxis axisPP = new NumberAxis("PP");
		NumberAxis axisRank = new NumberAxis("Rank");
		axisPP.setAutoRangeIncludesZero(false);
		axisRank.setAutoRangeIncludesZero(false);
		xyPlot.setRangeAxis(1, axisPP);
		xyPlot.setRangeAxis(2, axisRank);
		xyPlot.setDataset(1, processStatsPP(stats));
		xyPlot.setDataset(2, processStatsRank(stats));
		xyPlot.setRangeAxisLocation(1, AxisLocation.TOP_OR_RIGHT);
		xyPlot.setRangeAxisLocation(2, AxisLocation.TOP_OR_RIGHT);
		StandardXYItemRenderer rendererPP = new StandardXYItemRenderer();
		StandardXYItemRenderer rendererRank = new StandardXYItemRenderer();
		rendererPP.setSeriesPaint(1, new Color(150, 56, 213));
		rendererRank.setSeriesPaint(1, new Color(200, 56, 213));
		xyPlot.setRenderer(1, rendererPP);
		xyPlot.setRenderer(1, rendererRank);
		DateAxis axisDate = (DateAxis) xyPlot.getDomainAxis();
		axisDate.setDateFormatOverride(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		chartPanel.setDomainZoomable(true);
		chartPanel.setRangeZoomable(true);
		setContentPane(chartPanel);
		setVisible(true);
		pack();
	}

	public InterfaceChart getFrame()
	{
		return this;
	}

	private TimeSeriesCollection processStats(List<Stats> stats)
	{
		TimeSeries serieAccuracy = new TimeSeries(Utils.resourceBundle.getString("accuracy"));
		TimeSeries serie300Percent = new TimeSeries("300%");
		TimeSeries serie100Percent = new TimeSeries("100%");
		TimeSeries serie50Percent = new TimeSeries("50%");
		for(Stats stat : stats)
		{
			Millisecond time = new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Locale.getDefault());
			serieAccuracy.add(time, stat.getAccuracy());
			serie300Percent.add(time, stat.getTotalHits() * 100f / stat.getTotalHits());
			serie100Percent.add(time, stat.getTotalHits() * 66f / stat.getTotalHits());
			serie50Percent.add(time, stat.getTotalHits() * 33f / stat.getTotalHits());
		}
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serieAccuracy);
		collection.addSeries(serie300Percent);
		collection.addSeries(serie100Percent);
		collection.addSeries(serie50Percent);
		return collection;
	}

	private TimeSeriesCollection processStatsPP(List<Stats> stats)
	{
		TimeSeries seriePP = new TimeSeries("PP");
		for(Stats stat : stats)
			seriePP.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Locale.getDefault()), stat.getPp());
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(seriePP);
		return collection;
	}

	private TimeSeriesCollection processStatsRank(List<Stats> stats)
	{
		TimeSeries serieRank = new TimeSeries("Rank");
		for(Stats stat : stats)
			serieRank.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Locale.getDefault()), 0);
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serieRank);
		return collection;
	}
}
