package fr.mrcraftcod.interfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;
import java.sql.Date;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.Rotation;
import fr.mrcraftcod.objects.Stats;
import fr.mrcraftcod.utils.Utils;

public class InterfaceChart extends JFrame
{
	private static final long serialVersionUID = -5220915498588371099L;
	private static final Color colorLine1 = Color.BLUE, colorLine2 = Color.RED;

	public InterfaceChart(String user, String mode, List<Stats> stats)
	{
		super();
		setIconImages(Utils.icons);
		setTitle(user);
		int shapeOffset = 4;
		Shape shape = new Rectangle(-shapeOffset / 2, -shapeOffset / 2, shapeOffset, shapeOffset);
		ChartPanel chartPPAndRankPanel = getChartInPannel(createRankAndPPChart(user, stats, shape));
		ChartPanel chartAccuracyPanel = getChartInPannel(createAccuracyChart(user, stats, shape));
		ChartPanel chartRankedScorePanel = getChartInPannel(createRankedScoreChart(user, stats, shape));
		ChartPanel chartTotalScorePanel = getChartInPannel(createTotalScoreChart(user, stats, shape));
		ChartPanel chartPlayCountPanel = getChartInPannel(createPlayCountChart(user, stats, shape));
		ChartPanel chartHitsPanel = getChartInPannel(createHitsChart(user, stats));
		ChartPanel chartRanksPanel = getChartInPannel(createRanksChart(user, stats));
		JTabbedPane contentPane = new JTabbedPane();
		contentPane.addTab(Utils.resourceBundle.getString("rank") + " & PP", chartPPAndRankPanel);
		contentPane.addTab(Utils.resourceBundle.getString("accuracy"), chartAccuracyPanel);
		contentPane.addTab(Utils.resourceBundle.getString("ranked_score"), chartRankedScorePanel);
		contentPane.addTab(Utils.resourceBundle.getString("total_score"), chartTotalScorePanel);
		contentPane.addTab(Utils.resourceBundle.getString("play_count"), chartPlayCountPanel);
		contentPane.addTab("300 / 100 / 50", chartHitsPanel);
		contentPane.addTab("SS / S / A", chartRanksPanel);
		setContentPane(contentPane);
		setVisible(true);
		pack();
	}

	private JFreeChart createAccuracyChart(String user, List<Stats> stats, Shape shape)
	{
		JFreeChart chart = ChartFactory.createTimeSeriesChart(String.format(Utils.resourceBundle.getString("stats_for"), user), Utils.resourceBundle.getString("dates"), "%", null, true, true, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		XYPlot xyPlot = chart.getXYPlot();
		xyPlot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		NumberAxis axis = new NumberAxis(Utils.resourceBundle.getString("accuracy") + " (%)");
		axis.setTickLabelPaint(colorLine1);
		axis.setAutoRangeIncludesZero(false);
		NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
		format.setMaximumFractionDigits(4);
		axis.setNumberFormatOverride(format);
		xyPlot.setRangeAxis(0, axis);
		xyPlot.setRangeAxisLocation(0, AxisLocation.TOP_OR_RIGHT);
		xyPlot.setDataset(0, processStatsAccuracy(stats));
		xyPlot.mapDatasetToRangeAxis(0, 0);
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		xyPlot.setRenderer(0, renderer);
		xyPlot.getRendererForDataset(xyPlot.getDataset(0)).setSeriesPaint(0, colorLine1);
		xyPlot.getRendererForDataset(xyPlot.getDataset(0)).setSeriesShape(0, shape);
		DateAxis axisDate = (DateAxis) xyPlot.getDomainAxis();
		axisDate.setDateFormatOverride(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT));
		return chart;
	}

	private JFreeChart createHitsChart(String user, List<Stats> stats)
	{
		JFreeChart chart = ChartFactory.createPieChart3D(String.format(Utils.resourceBundle.getString("stats_for"), user) + " (" + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date(stats.get(stats.size() - 1).getDate())) + ")", processStatsHits(stats), true, false, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMaximumFractionDigits(2);
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})", NumberFormat.getNumberInstance(), percentFormat));
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0} : {1}"));
		plot.setNoDataMessage("You shouldn't be there! How have you come here, are you a wizard??! :o");
		return chart;
	}

	private JFreeChart createPlayCountChart(String user, List<Stats> stats, Shape shape)
	{
		JFreeChart chart = ChartFactory.createTimeSeriesChart(String.format(Utils.resourceBundle.getString("stats_for"), user), Utils.resourceBundle.getString("dates"), "", null, true, true, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		XYPlot xyPlot = chart.getXYPlot();
		xyPlot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		NumberAxis axis = new NumberAxis(Utils.resourceBundle.getString("play_count"));
		axis.setTickLabelPaint(colorLine1);
		axis.setAutoRangeIncludesZero(false);
		NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
		format.setMaximumFractionDigits(0);
		axis.setNumberFormatOverride(format);
		xyPlot.setRangeAxis(0, axis);
		xyPlot.setRangeAxisLocation(0, AxisLocation.TOP_OR_RIGHT);
		xyPlot.setDataset(0, processStatsPlayCount(stats));
		xyPlot.mapDatasetToRangeAxis(0, 0);
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		xyPlot.setRenderer(0, renderer);
		xyPlot.getRendererForDataset(xyPlot.getDataset(0)).setSeriesPaint(0, colorLine1);
		xyPlot.getRendererForDataset(xyPlot.getDataset(0)).setSeriesShape(0, shape);
		DateAxis axisDate = (DateAxis) xyPlot.getDomainAxis();
		axisDate.setDateFormatOverride(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT));
		return chart;
	}

	private JFreeChart createRankAndPPChart(String user, List<Stats> stats, Shape shape)
	{
		JFreeChart chart = ChartFactory.createTimeSeriesChart(String.format(Utils.resourceBundle.getString("stats_for"), user), Utils.resourceBundle.getString("dates"), "", null, true, true, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		XYPlot xyPlot = chart.getXYPlot();
		xyPlot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		NumberAxis axisPP = new NumberAxis("PP");
		NumberAxis axisRank = new NumberAxis(Utils.resourceBundle.getString("rank"));
		axisPP.setTickLabelPaint(colorLine1);
		axisRank.setTickLabelPaint(colorLine2);
		axisPP.setAutoRangeIncludesZero(false);
		axisRank.setAutoRangeIncludesZero(false);
		NumberFormat formatPP = NumberFormat.getInstance(Locale.getDefault());
		NumberFormat formatRank = NumberFormat.getInstance(Locale.getDefault());
		formatPP.setMaximumFractionDigits(2);
		formatRank.setMaximumFractionDigits(0);
		axisPP.setNumberFormatOverride(formatPP);
		axisRank.setNumberFormatOverride(formatRank);
		axisRank.setInverted(true);
		xyPlot.setRangeAxis(0, axisPP);
		xyPlot.setRangeAxis(1, axisRank);
		xyPlot.setDataset(0, processStatsPP(stats));
		xyPlot.setDataset(1, processStatsRank(stats));
		xyPlot.mapDatasetToRangeAxis(0, 0);
		xyPlot.mapDatasetToRangeAxis(1, 1);
		xyPlot.setRangeAxisLocation(0, AxisLocation.TOP_OR_RIGHT);
		xyPlot.setRangeAxisLocation(1, AxisLocation.TOP_OR_RIGHT);
		XYLineAndShapeRenderer rendererPP = new XYLineAndShapeRenderer();
		XYLineAndShapeRenderer rendererRank = new XYLineAndShapeRenderer();
		xyPlot.setRenderer(0, rendererPP);
		xyPlot.setRenderer(1, rendererRank);
		xyPlot.getRendererForDataset(xyPlot.getDataset(0)).setSeriesPaint(0, colorLine1);
		xyPlot.getRendererForDataset(xyPlot.getDataset(1)).setSeriesPaint(0, colorLine2);
		xyPlot.getRendererForDataset(xyPlot.getDataset(0)).setSeriesShape(0, shape);
		xyPlot.getRendererForDataset(xyPlot.getDataset(1)).setSeriesShape(0, shape);
		DateAxis axisDate = (DateAxis) xyPlot.getDomainAxis();
		axisDate.setDateFormatOverride(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT));
		return chart;
	}

	private JFreeChart createRankedScoreChart(String user, List<Stats> stats, Shape shape)
	{
		JFreeChart chart = ChartFactory.createTimeSeriesChart(String.format(Utils.resourceBundle.getString("stats_for"), user), Utils.resourceBundle.getString("dates"), "", null, true, true, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		XYPlot xyPlot = chart.getXYPlot();
		xyPlot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		NumberAxis axis = new NumberAxis(Utils.resourceBundle.getString("ranked_score"));
		axis.setTickLabelPaint(colorLine1);
		axis.setAutoRangeIncludesZero(false);
		NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
		format.setMaximumFractionDigits(0);
		axis.setNumberFormatOverride(format);
		xyPlot.setRangeAxis(0, axis);
		xyPlot.setRangeAxisLocation(0, AxisLocation.TOP_OR_RIGHT);
		xyPlot.setDataset(0, processStatsRankedScore(stats));
		xyPlot.mapDatasetToRangeAxis(0, 0);
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		xyPlot.setRenderer(0, renderer);
		xyPlot.getRendererForDataset(xyPlot.getDataset(0)).setSeriesPaint(0, colorLine1);
		xyPlot.getRendererForDataset(xyPlot.getDataset(0)).setSeriesShape(0, shape);
		DateAxis axisDate = (DateAxis) xyPlot.getDomainAxis();
		axisDate.setDateFormatOverride(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT));
		return chart;
	}

	private JFreeChart createRanksChart(String user, List<Stats> stats)
	{
		JFreeChart chart = ChartFactory.createPieChart3D(String.format(Utils.resourceBundle.getString("stats_for"), user) + " (" + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date(stats.get(stats.size() - 1).getDate())) + ")", processStatsRanks(stats), true, false, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMaximumFractionDigits(2);
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})", NumberFormat.getNumberInstance(), percentFormat));
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0} : {1}"));
		plot.setNoDataMessage("You shouldn't be there! How have you come here, are you a wizard??! :o");
		return chart;
	}

	private JFreeChart createTotalScoreChart(String user, List<Stats> stats, Shape shape)
	{
		JFreeChart chart = ChartFactory.createTimeSeriesChart(String.format(Utils.resourceBundle.getString("stats_for"), user), Utils.resourceBundle.getString("dates"), "", null, true, true, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		XYPlot xyPlot = chart.getXYPlot();
		xyPlot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		NumberAxis axis = new NumberAxis(Utils.resourceBundle.getString("total_score"));
		axis.setTickLabelPaint(colorLine1);
		axis.setAutoRangeIncludesZero(false);
		axis.setNumberFormatOverride(NumberFormat.getInstance(Locale.getDefault()));
		xyPlot.setRangeAxis(0, axis);
		xyPlot.setRangeAxisLocation(0, AxisLocation.TOP_OR_RIGHT);
		xyPlot.setDataset(0, processStatsTotalScore(stats));
		xyPlot.mapDatasetToRangeAxis(0, 0);
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		xyPlot.setRenderer(0, renderer);
		xyPlot.getRendererForDataset(xyPlot.getDataset(0)).setSeriesPaint(0, colorLine1);
		xyPlot.getRendererForDataset(xyPlot.getDataset(0)).setSeriesShape(0, shape);
		DateAxis axisDate = (DateAxis) xyPlot.getDomainAxis();
		axisDate.setDateFormatOverride(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT));
		return chart;
	}

	private ChartPanel getChartInPannel(JFreeChart chart)
	{
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		chartPanel.setDomainZoomable(true);
		chartPanel.setRangeZoomable(true);
		return chartPanel;
	}

	public InterfaceChart getFrame()
	{
		return this;
	}

	private XYDataset processStatsAccuracy(List<Stats> stats)
	{
		TimeSeries serie = new TimeSeries(Utils.resourceBundle.getString("accuracy"));
		for(Stats stat : stats)
			serie.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Locale.getDefault()), stat.getAccuracy());
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serie);
		return collection;
	}

	private PieDataset processStatsHits(List<Stats> stats)
	{
		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("300", stats.get(stats.size() - 1).getCount300());
		data.setValue("100", stats.get(stats.size() - 1).getCount100());
		data.setValue("50", stats.get(stats.size() - 1).getCount50());
		return data;
	}

	private XYDataset processStatsPlayCount(List<Stats> stats)
	{
		TimeSeries serie = new TimeSeries(Utils.resourceBundle.getString("play_count"));
		for(Stats stat : stats)
			serie.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Locale.getDefault()), stat.getPlayCount());
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serie);
		return collection;
	}

	private XYDataset processStatsPP(List<Stats> stats)
	{
		TimeSeries serie = new TimeSeries("PP");
		for(Stats stat : stats)
			serie.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Locale.getDefault()), stat.getPp());
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serie);
		return collection;
	}

	private XYDataset processStatsRank(List<Stats> stats)
	{
		TimeSeries serie = new TimeSeries(Utils.resourceBundle.getString("rank"));
		for(Stats stat : stats)
			serie.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Locale.getDefault()), stat.getRank());
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serie);
		return collection;
	}

	private XYDataset processStatsRankedScore(List<Stats> stats)
	{
		TimeSeries serie = new TimeSeries("TS");
		for(Stats stat : stats)
			serie.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Locale.getDefault()), stat.getRankedScore());
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serie);
		return collection;
	}

	private PieDataset processStatsRanks(List<Stats> stats)
	{
		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("SS", stats.get(stats.size() - 1).getCountSS());
		data.setValue("S", stats.get(stats.size() - 1).getCountS());
		data.setValue("A", stats.get(stats.size() - 1).getCountA());
		return data;
	}

	private XYDataset processStatsTotalScore(List<Stats> stats)
	{
		TimeSeries serie = new TimeSeries("TS");
		for(Stats stat : stats)
			serie.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Locale.getDefault()), stat.getTotalScore());
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serie);
		return collection;
	}
}
