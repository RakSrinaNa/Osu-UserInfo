package fr.mrcraftcod.interfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import fr.mrcraftcod.objects.Stats;
import fr.mrcraftcod.utils.Utils;

public class InterfaceChart extends JFrame
{
	private static final long serialVersionUID = -5220915498588371099L;

	public InterfaceChart(String user, String mode, List<Stats> stats)
	{
		super();
		setTitle(String.format(Utils.resourceBundle.getString("stats_for"), user));
		Color colorPP = Color.BLUE, colorRank = Color.RED, colorAcc = Color.BLACK;
		int shapeOffset = 4;
		Shape shape = new Rectangle(-shapeOffset / 2, -shapeOffset / 2, shapeOffset, shapeOffset);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(String.format(Utils.resourceBundle.getString("stats_for"), user), Utils.resourceBundle.getString("dates"), "%", null, true, true, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		XYPlot xyPlot = chart.getXYPlot();
		xyPlot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		NumberAxis axisAcc = new NumberAxis(Utils.resourceBundle.getString("accuracy"));
		NumberAxis axisPP = new NumberAxis("PP");
		NumberAxis axisRank = new NumberAxis(Utils.resourceBundle.getString("rank"));
		axisAcc.setTickLabelPaint(colorAcc);
		axisPP.setTickLabelPaint(colorPP);
		axisRank.setTickLabelPaint(colorRank);
		axisAcc.setAutoRangeIncludesZero(true);
		axisPP.setAutoRangeIncludesZero(false);
		axisRank.setAutoRangeIncludesZero(false);
		axisAcc.setNumberFormatOverride(new DecimalFormat("0.00"));
		axisPP.setNumberFormatOverride(new DecimalFormat("0.00"));
		axisRank.setNumberFormatOverride(new DecimalFormat("0"));
		xyPlot.setRangeAxis(0, axisAcc);
		xyPlot.setRangeAxis(1, axisPP);
		xyPlot.setRangeAxis(2, axisRank);
		xyPlot.setDataset(0, processStatsAccuracy(stats));
		xyPlot.setDataset(1, processStatsPP(stats));
		xyPlot.setDataset(2, processStatsRank(stats));
		xyPlot.mapDatasetToRangeAxis(0, 0);
		xyPlot.mapDatasetToRangeAxis(1, 1);
		xyPlot.mapDatasetToRangeAxis(2, 2);
		xyPlot.setRangeAxisLocation(1, AxisLocation.BOTTOM_OR_LEFT);
		xyPlot.setRangeAxisLocation(2, AxisLocation.BOTTOM_OR_LEFT);
		XYLineAndShapeRenderer rendererAccuracy = new XYLineAndShapeRenderer();
		XYLineAndShapeRenderer rendererPP = new XYLineAndShapeRenderer();
		XYLineAndShapeRenderer rendererRank = new XYLineAndShapeRenderer();
		xyPlot.setRenderer(0, rendererAccuracy);
		xyPlot.setRenderer(1, rendererPP);
		xyPlot.setRenderer(2, rendererRank);
		xyPlot.getRendererForDataset(xyPlot.getDataset(0)).setSeriesPaint(0, colorAcc);
		xyPlot.getRendererForDataset(xyPlot.getDataset(1)).setSeriesPaint(0, colorPP);
		xyPlot.getRendererForDataset(xyPlot.getDataset(2)).setSeriesPaint(0, colorRank);
		xyPlot.getRendererForDataset(xyPlot.getDataset(0)).setSeriesShape(0, shape);
		xyPlot.getRendererForDataset(xyPlot.getDataset(1)).setSeriesShape(0, shape);
		xyPlot.getRendererForDataset(xyPlot.getDataset(2)).setSeriesShape(0, shape);
		DateAxis axisDate = (DateAxis) xyPlot.getDomainAxis();
		axisDate.setDateFormatOverride(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT));
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

	private XYDataset processStatsAccuracy(List<Stats> stats)
	{
		TimeSeries serieAcc = new TimeSeries("Acc");
		for(Stats stat : stats)
			serieAcc.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Locale.getDefault()), stat.getAccuracy());
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serieAcc);
		return collection;
	}

	private XYDataset processStatsPP(List<Stats> stats)
	{
		TimeSeries seriePP = new TimeSeries("PP");
		for(Stats stat : stats)
			seriePP.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Locale.getDefault()), stat.getPp());
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(seriePP);
		return collection;
	}

	private XYDataset processStatsRank(List<Stats> stats)
	{
		TimeSeries serieRank = new TimeSeries("Rank");
		for(Stats stat : stats)
			serieRank.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Locale.getDefault()), stat.getRank());
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serieRank);
		return collection;
	}
}
