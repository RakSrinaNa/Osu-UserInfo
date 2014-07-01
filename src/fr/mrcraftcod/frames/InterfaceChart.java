package fr.mrcraftcod.frames;

import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.Shape;
import java.sql.Date;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
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
import fr.mrcraftcod.listeners.change.GraphTabChangeListener;
import fr.mrcraftcod.objects.ChartPopupMenu;
import fr.mrcraftcod.objects.Stats;
import fr.mrcraftcod.utils.Utils;

/**
 * Interface showing graphs for a user.
 *
 * @author MrCraftCod
 */
public class InterfaceChart extends JFrame
{
	private static final long serialVersionUID = -5220915498588371099L;
	private static final Color colorLine1 = Color.BLUE, colorLine2 = Color.RED;
	private JTabbedPane contentPane;
	private ChartPanel lastChart;

	/**
	 * Constructor.
	 * Create graphs for given stats.
	 *
	 * @param parent The parent frame.
	 * @param user The name of the user.
	 * @param mode The mode of the graphs.
	 * @param stats The stats to process.
	 */
	public InterfaceChart(JFrame parent, String user, String mode, List<Stats> stats)
	{
		super();
		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		setIconImages(Utils.icons);
		setTitle(user);
		String title = String.format(Utils.resourceBundle.getString("stats_for"), user);
		int shapeOffset = 3;
		Shape shape = new Rectangle(-shapeOffset / 2, -shapeOffset / 2, shapeOffset, shapeOffset);
		ChartPanel chartPPAndRankPanel = getChartInPannel(user, "PP_" + Utils.resourceBundle.getString("rank"), createRankAndPPChart(title, user, stats, shape));
		ChartPanel chartAccuracyPanel = getChartInPannel(user, Utils.resourceBundle.getString("accuracy"), createAccuracyChart(title, user, stats, shape));
		ChartPanel chartRankedScorePanel = getChartInPannel(user, Utils.resourceBundle.getString("ranked_score"), createRankedScoreChart(title, user, stats, shape));
		ChartPanel chartTotalScorePanel = getChartInPannel(user, Utils.resourceBundle.getString("total_score"), createTotalScoreChart(title, user, stats, shape));
		ChartPanel chartPlayCountPanel = getChartInPannel(user, Utils.resourceBundle.getString("play_count"), createPlayCountChart(title, user, stats, shape));
		ChartPanel chartLevelPanel = getChartInPannel(user, Utils.resourceBundle.getString("graph_level"), createLevelChart(title, user, stats, shape));
		ChartPanel chartHitsPanel = getChartInPannel(user, Utils.resourceBundle.getString("total_hits"), createHitsChart(title, user, stats));
		ChartPanel chartRanksPanel = getChartInPannel(user, Utils.resourceBundle.getString("ranks"), createRanksChart(title, user, stats));
		this.contentPane = new JTabbedPane();
		this.contentPane.addTab(Utils.resourceBundle.getString("rank") + " & PP", chartPPAndRankPanel);
		this.contentPane.addTab(Utils.resourceBundle.getString("accuracy"), chartAccuracyPanel);
		this.contentPane.addTab(Utils.resourceBundle.getString("ranked_score"), chartRankedScorePanel);
		this.contentPane.addTab(Utils.resourceBundle.getString("total_score"), chartTotalScorePanel);
		this.contentPane.addTab(Utils.resourceBundle.getString("play_count"), chartPlayCountPanel);
		this.contentPane.addTab(Utils.resourceBundle.getString("graph_level"), chartLevelPanel);
		this.contentPane.addTab("300 / 100 / 50", chartHitsPanel);
		this.contentPane.addTab("SS / S / A", chartRanksPanel);
		this.contentPane.addChangeListener(new GraphTabChangeListener());
		KeyStroke ctrlTab = KeyStroke.getKeyStroke("ctrl TAB");
		setSize(new Dimension(800, 600));
		setLocationRelativeTo(parent);
		KeyStroke ctrlShiftTab = KeyStroke.getKeyStroke("ctrl shift TAB");
		Set<AWTKeyStroke> forwardKeys = new HashSet<AWTKeyStroke>(this.contentPane.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
		forwardKeys.remove(ctrlTab);
		this.contentPane.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);
		Set<AWTKeyStroke> backwardKeys = new HashSet<AWTKeyStroke>(this.contentPane.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
		backwardKeys.remove(ctrlShiftTab);
		this.contentPane.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);
		InputMap inputMap = this.contentPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		inputMap.put(ctrlTab, "navigateNext");
		inputMap.put(ctrlShiftTab, "navigatePrevious");
		setContentPane(this.contentPane);
		setVisible(true);
		pack();
		updateLastChart();
	}

	public ChartPanel getLastChart()
	{
		return this.lastChart;
	}

	public void updateLastChart()
	{
		if(this.contentPane.getSelectedComponent() instanceof ChartPanel)
			this.lastChart = (ChartPanel) this.contentPane.getSelectedComponent();
	}

	/**
	 * Used to create the accuracy graph.
	 *
	 * @param title The title of the graph.
	 * @param user The name of the user.
	 * @param stats The stats to process.
	 * @param shape The shape of the points.
	 * @return The graph.
	 */
	private JFreeChart createAccuracyChart(String title, String user, List<Stats> stats, Shape shape)
	{
		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, null, "%", null, true, true, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		XYPlot xyPlot = chart.getXYPlot();
		xyPlot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		NumberAxis axis = new NumberAxis(Utils.resourceBundle.getString("accuracy") + " (%)");
		axis.setTickLabelPaint(colorLine1);
		axis.setAutoRangeIncludesZero(false);
		axis.setTickLabelFont(Utils.fontMain);
		axis.setLabelFont(Utils.fontMain);
		NumberFormat format = NumberFormat.getInstance(Utils.locale);
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
		axisDate.setTickLabelFont(Utils.fontMain);
		axisDate.setLabelFont(Utils.fontMain);
		return chart;
	}

	/**
	 * Used to create the hits pie.
	 *
	 * @param title The title of the graph.
	 * @param user The name of the user.
	 * @param stats The stats to process.
	 * @return The graph.
	 */
	private JFreeChart createHitsChart(String title, String user, List<Stats> stats)
	{
		JFreeChart chart = ChartFactory.createPieChart3D(title + " (" + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date(stats.get(stats.size() - 1).getDate())) + ")", processStatsHits(stats), true, false, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		chart.getLegend().setItemFont(Utils.fontMain);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		plot.setLabelFont(Utils.fontMain);
		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMaximumFractionDigits(2);
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})", NumberFormat.getNumberInstance(), percentFormat));
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0} : {1}"));
		plot.setNoDataMessage("You shouldn't be there! How have you come here, are you a wizard??! :o");
		return chart;
	}

	/**
	 * Used to create the level graph.
	 *
	 * @param title The title of the graph.
	 * @param user The name of the user.
	 * @param stats The stats to process.
	 * @param shape The shape of the points.
	 * @return The graph.
	 */
	private JFreeChart createLevelChart(String title, String user, List<Stats> stats, Shape shape)
	{
		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, null, "", null, true, true, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		XYPlot xyPlot = chart.getXYPlot();
		xyPlot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		NumberAxis axis = new NumberAxis(Utils.resourceBundle.getString("graph_level"));
		axis.setTickLabelPaint(colorLine1);
		axis.setAutoRangeIncludesZero(false);
		axis.setTickLabelFont(Utils.fontMain);
		axis.setLabelFont(Utils.fontMain);
		NumberFormat format = NumberFormat.getInstance(Utils.locale);
		format.setMaximumFractionDigits(4);
		axis.setNumberFormatOverride(format);
		xyPlot.setRangeAxis(0, axis);
		xyPlot.setRangeAxisLocation(0, AxisLocation.TOP_OR_RIGHT);
		xyPlot.setDataset(0, processStatsLevel(stats));
		xyPlot.mapDatasetToRangeAxis(0, 0);
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		xyPlot.setRenderer(0, renderer);
		xyPlot.getRendererForDataset(xyPlot.getDataset(0)).setSeriesPaint(0, colorLine1);
		xyPlot.getRendererForDataset(xyPlot.getDataset(0)).setSeriesShape(0, shape);
		DateAxis axisDate = (DateAxis) xyPlot.getDomainAxis();
		axisDate.setDateFormatOverride(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT));
		axisDate.setTickLabelFont(Utils.fontMain);
		axisDate.setLabelFont(Utils.fontMain);
		return chart;
	}

	/**
	 * Used to create the play count graph.
	 *
	 * @param title The title of the graph.
	 * @param user The name of the user.
	 * @param stats The stats to process.
	 * @param shape The shape of the points.
	 * @return The graph.
	 */
	private JFreeChart createPlayCountChart(String title, String user, List<Stats> stats, Shape shape)
	{
		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, null, "", null, true, true, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		XYPlot xyPlot = chart.getXYPlot();
		xyPlot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		NumberAxis axis = new NumberAxis(Utils.resourceBundle.getString("play_count"));
		axis.setTickLabelPaint(colorLine1);
		axis.setAutoRangeIncludesZero(false);
		axis.setTickLabelFont(Utils.fontMain);
		axis.setLabelFont(Utils.fontMain);
		NumberFormat format = NumberFormat.getInstance(Utils.locale);
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
		axisDate.setTickLabelFont(Utils.fontMain);
		axisDate.setLabelFont(Utils.fontMain);
		return chart;
	}

	/**
	 * Used to create the pp with rank graph.
	 *
	 * @param title The title of the graph.
	 * @param user The name of the user.
	 * @param stats The stats to process.
	 * @param shape The shape of the points.
	 * @return The graph.
	 */
	private JFreeChart createRankAndPPChart(String title, String user, List<Stats> stats, Shape shape)
	{
		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, null, "", null, true, true, false);
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
		axisRank.setTickLabelFont(Utils.fontMain);
		axisPP.setTickLabelFont(Utils.fontMain);
		axisRank.setLabelFont(Utils.fontMain);
		axisPP.setLabelFont(Utils.fontMain);
		NumberFormat formatPP = NumberFormat.getInstance(Utils.locale);
		NumberFormat formatRank = NumberFormat.getInstance(Utils.locale);
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
		axisDate.setTickLabelFont(Utils.fontMain);
		axisDate.setLabelFont(Utils.fontMain);
		return chart;
	}

	/**
	 * Used to create the ranked score graph.
	 *
	 * @param title The title of the graph.
	 * @param user The name of the user.
	 * @param stats The stats to process.
	 * @param shape The shape of the points.
	 * @return The graph.
	 */
	private JFreeChart createRankedScoreChart(String title, String user, List<Stats> stats, Shape shape)
	{
		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, null, "", null, true, true, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		XYPlot xyPlot = chart.getXYPlot();
		xyPlot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		NumberAxis axis = new NumberAxis(Utils.resourceBundle.getString("ranked_score"));
		axis.setTickLabelPaint(colorLine1);
		axis.setAutoRangeIncludesZero(false);
		axis.setTickLabelFont(Utils.fontMain);
		axis.setLabelFont(Utils.fontMain);
		NumberFormat format = NumberFormat.getInstance(Utils.locale);
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
		axisDate.setTickLabelFont(Utils.fontMain);
		axisDate.setLabelFont(Utils.fontMain);
		return chart;
	}

	/**
	 * Used to create the ranks pie.
	 *
	 * @param title The title of the graph.
	 * @param user The name of the user.
	 * @param stats The stats to process.
	 * @return The graph.
	 */
	private JFreeChart createRanksChart(String title, String user, List<Stats> stats)
	{
		JFreeChart chart = ChartFactory.createPieChart3D(title + " (" + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date(stats.get(stats.size() - 1).getDate())) + ")", processStatsRanks(stats), true, false, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		chart.getLegend().setItemFont(Utils.fontMain);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		plot.setLabelFont(Utils.fontMain);
		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMaximumFractionDigits(2);
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2})", NumberFormat.getNumberInstance(), percentFormat));
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0} : {1}"));
		plot.setNoDataMessage("You shouldn't be there! How have you come here, are you a wizard??! :o");
		return chart;
	}

	/**
	 * Used to create the total score graph.
	 *
	 * @param title The title of the graph.
	 * @param user The name of the user.
	 * @param stats The stats to process.
	 * @param shape The shape of the points.
	 * @return The graph.
	 */
	private JFreeChart createTotalScoreChart(String title, String user, List<Stats> stats, Shape shape)
	{
		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, null, "", null, true, true, false);
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		XYPlot xyPlot = chart.getXYPlot();
		xyPlot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		NumberAxis axis = new NumberAxis(Utils.resourceBundle.getString("total_score"));
		axis.setTickLabelPaint(colorLine1);
		axis.setAutoRangeIncludesZero(false);
		axis.setTickLabelFont(Utils.fontMain);
		axis.setLabelFont(Utils.fontMain);
		axis.setNumberFormatOverride(NumberFormat.getInstance(Utils.locale));
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
		axisDate.setTickLabelFont(Utils.fontMain);
		axisDate.setLabelFont(Utils.fontMain);
		return chart;
	}

	/**
	 * Used to get a panel with a graph inside.
	 *
	 * @param user The name of the user.
	 * @param type What the graph is about.
	 * @param chart The graph to set inside.
	 * @return The panel.
	 */
	private ChartPanel getChartInPannel(String user, String type, JFreeChart chart)
	{
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 270));
		chartPanel.setDomainZoomable(true);
		chartPanel.setRangeZoomable(true);
		chartPanel.setPopupMenu(new ChartPopupMenu(chart, user, type));
		return chartPanel;
	}

	/**
	 * Used to process the accuracy stats.
	 *
	 * @param stats The stats to process.
	 * @return The dataset for graphs.
	 */
	private XYDataset processStatsAccuracy(List<Stats> stats)
	{
		TimeSeries serie = new TimeSeries(Utils.resourceBundle.getString("accuracy"));
		for(Stats stat : stats)
			serie.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Utils.locale), stat.getAccuracy());
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serie);
		return collection;
	}

	/**
	 * Used to process the hits stats.
	 *
	 * @param stats The stats to process.
	 * @return The dataset for pies.
	 */
	private PieDataset processStatsHits(List<Stats> stats)
	{
		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("300", stats.get(stats.size() - 1).getCount300());
		data.setValue("100", stats.get(stats.size() - 1).getCount100());
		data.setValue("50", stats.get(stats.size() - 1).getCount50());
		return data;
	}

	/**
	 * Used to process the level stats.
	 *
	 * @param stats The stats to process.
	 * @return The dataset for graphs.
	 */
	private XYDataset processStatsLevel(List<Stats> stats)
	{
		TimeSeries serie = new TimeSeries(Utils.resourceBundle.getString("graph_level"));
		for(Stats stat : stats)
			serie.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Utils.locale), stat.getLevel());
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serie);
		return collection;
	}

	/**
	 * Used to process the play count stats.
	 *
	 * @param stats The stats to process.
	 * @return The dataset for graphs.
	 */
	private XYDataset processStatsPlayCount(List<Stats> stats)
	{
		TimeSeries serie = new TimeSeries(Utils.resourceBundle.getString("play_count"));
		for(Stats stat : stats)
			serie.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Utils.locale), stat.getPlayCount());
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serie);
		return collection;
	}

	/**
	 * Used to process the PP stats.
	 *
	 * @param stats The stats to process.
	 * @return The dataset for graphs.
	 */
	private XYDataset processStatsPP(List<Stats> stats)
	{
		TimeSeries serie = new TimeSeries("PP");
		for(Stats stat : stats)
			serie.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Utils.locale), stat.getPp());
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serie);
		return collection;
	}

	/**
	 * Used to process the rank stats.
	 *
	 * @param stats The stats to process.
	 * @return The dataset for graphs.
	 */
	private XYDataset processStatsRank(List<Stats> stats)
	{
		TimeSeries serie = new TimeSeries(Utils.resourceBundle.getString("rank"));
		for(Stats stat : stats)
			serie.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Utils.locale), stat.getRank());
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serie);
		return collection;
	}

	/**
	 * Used to process the ranked score stats.
	 *
	 * @param stats The stats to process.
	 * @return The dataset for graphs.
	 */
	private XYDataset processStatsRankedScore(List<Stats> stats)
	{
		TimeSeries serie = new TimeSeries(Utils.resourceBundle.getString("ranked_score"));
		for(Stats stat : stats)
			serie.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Utils.locale), stat.getRankedScore());
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serie);
		return collection;
	}

	/**
	 * Used to process the ranks stats.
	 *
	 * @param stats The stats to process.
	 * @return The dataset for pies.
	 */
	private PieDataset processStatsRanks(List<Stats> stats)
	{
		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("SS", stats.get(stats.size() - 1).getCountSS());
		data.setValue("S", stats.get(stats.size() - 1).getCountS());
		data.setValue("A", stats.get(stats.size() - 1).getCountA());
		return data;
	}

	/**
	 * Used to process the total score stats.
	 *
	 * @param stats The stats to process.
	 * @return The dataset for graphs.
	 */
	private XYDataset processStatsTotalScore(List<Stats> stats)
	{
		TimeSeries serie = new TimeSeries(Utils.resourceBundle.getString("total_score"));
		for(Stats stat : stats)
			serie.add(new Millisecond(new Date(stat.getDate()), TimeZone.getDefault(), Utils.locale), stat.getTotalScore());
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serie);
		return collection;
	}
}
