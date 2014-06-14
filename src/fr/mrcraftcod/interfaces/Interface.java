package fr.mrcraftcod.interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import fr.mrcraftcod.Main;
import fr.mrcraftcod.actions.ActionRefreshStats;
import fr.mrcraftcod.listeners.AutoUpdateItemListener;
import fr.mrcraftcod.listeners.UserNameFieldDocumentListener;
import fr.mrcraftcod.listeners.UserNameTextFieldKeyListener;
import fr.mrcraftcod.listeners.actions.ItemAboutActionListener;
import fr.mrcraftcod.listeners.actions.ItemChartActionListener;
import fr.mrcraftcod.listeners.actions.ItemSettingsActionListener;
import fr.mrcraftcod.listeners.actions.ModeCTBActionListener;
import fr.mrcraftcod.listeners.actions.ModeManiaActionListener;
import fr.mrcraftcod.listeners.actions.ModeStandardActionListener;
import fr.mrcraftcod.listeners.actions.ModeTaikoActionListener;
import fr.mrcraftcod.listeners.actions.StatsDateActionListener;
import fr.mrcraftcod.listeners.actions.TrackUserActionListener;
import fr.mrcraftcod.listeners.actions.ValidButtonActionListener;
import fr.mrcraftcod.listeners.components.ModesComponentListener;
import fr.mrcraftcod.listeners.components.SearchPanelComponentListener;
import fr.mrcraftcod.listeners.mouse.OpenProfileMouseListener;
import fr.mrcraftcod.listeners.windows.MainWindowListener;
import fr.mrcraftcod.objects.AutoComboBox;
import fr.mrcraftcod.objects.GhostText;
import fr.mrcraftcod.objects.ImagePanel;
import fr.mrcraftcod.objects.JButtonMode;
import fr.mrcraftcod.objects.Stats;
import fr.mrcraftcod.objects.User;
import fr.mrcraftcod.utils.CountryCode;
import fr.mrcraftcod.utils.Utils;

public class Interface extends JFrame // TODO Javadoc
{
	private static final long serialVersionUID = -6393144716196499998L;
	public final JTextComponent userNameFieldTextComponent;
	public final AutoComboBox userNameField;
	public final JComboBox<String> lastStatsDateBox;
	public final JLabel lastStatsDate;
	public final JLabel username;
	public final JCheckBox track;
	public final JCheckBox autoUpdateCheck;
	private final ImageIcon iconRefresh, iconSearch;
	private final ImagePanel avatar, countryFlag;
	private final DefaultComboBoxModel<String> statsDateModel, userNameFieldModel;
	private final JButton validButon;
	private final JButtonMode buttonStandard, buttonTaiko, buttonCTB, buttonMania;
	private final JLabel totalHits;
	private final JLabel countSS;
	private final JLabel countS;
	private final JLabel countA;
	private final JLabel playCount;
	private final JLabel rankedScore;
	private final JLabel totalScore;
	private final JLabel ppCount;
	private final JLabel accuracy;
	private final JLabel country;
	private final JLabel hitCount300;
	private final JLabel hitCount100;
	private final JLabel hitCount50;
	private final JProgressBar levelBar;

	@SuppressWarnings("unchecked")
	public Interface() throws IOException
	{
		super(Main.APPNAME + " v" + Main.VERSION);
		int pictureButtonSize = 20;
		Utils.logger.log(Level.INFO, "Loading icons...");
		this.iconRefresh = new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/refresh.png")), pictureButtonSize, pictureButtonSize));
		this.iconSearch = new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/search.png")), pictureButtonSize, pictureButtonSize));
		/************** FRAME INFOS ********************/
		Utils.logger.log(Level.INFO, "Setting frame options...");
		getFrame().setBackground(Utils.backColor);
		getFrame().setFocusable(true);
		getFrame().setVisible(false);
		getFrame().getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F5"), "getInfos");
		getFrame().getRootPane().getActionMap().put("getInfos", new ActionRefreshStats());
		getFrame().addWindowListener(new MainWindowListener());
		getFrame().setLayout(new GridBagLayout());
		getFrame().setMinimumSize(new Dimension(575, 725));
		getFrame().setPreferredSize(new Dimension(575, 725));
		getFrame().setAlwaysOnTop(false);
		getFrame().setIconImages(Utils.icons);
		getFrame().getContentPane().setBackground(Utils.backColor);
		getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/*************** FRMAE BAR ************************/
		Utils.logger.log(Level.INFO, "Creating frame bar...");
		Font menuBarFont = Utils.fontMain.deriveFont(Font.PLAIN, 13);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(menuBarFont);
		JMenu menuFile = new JMenu(Utils.resourceBundle.getString("menu_bar_file"));
		menuFile.setFont(menuBarFont);
		JMenu menuHelp = new JMenu(Utils.resourceBundle.getString("menu_bar_help"));
		menuHelp.setFont(menuBarFont);
		JMenuItem itemChart = new JMenuItem(Utils.resourceBundle.getString("graph"));
		itemChart.setFont(menuBarFont);
		itemChart.addActionListener(new ItemChartActionListener());
		JMenuItem itemSettings = new JMenuItem(Utils.resourceBundle.getString("settings"));
		itemSettings.setFont(menuBarFont);
		itemSettings.addActionListener(new ItemSettingsActionListener());
		JMenuItem itemAbout = new JMenuItem(Utils.resourceBundle.getString("menu_bar_help_about"));
		itemAbout.setFont(menuBarFont);
		itemAbout.addActionListener(new ItemAboutActionListener());
		menuFile.add(itemChart);
		menuFile.addSeparator();
		menuFile.add(itemSettings);
		menuHelp.add(itemAbout);
		menuBar.add(menuFile);
		menuBar.add(menuHelp);
		getFrame().setJMenuBar(menuBar);
		/*************** SEARCH PANEL **********************/
		Utils.logger.log(Level.INFO, "Creating search panel...");
		JPanel searchPanel = new JPanel(new GridBagLayout());
		searchPanel.setBackground(Utils.searchBarColor);
		searchPanel.setMaximumSize(new Dimension(9999, 36));
		searchPanel.addComponentListener(new SearchPanelComponentListener());
		JLabel usernameAsk = new JLabel(Utils.resourceBundle.getString("username") + " : ");
		usernameAsk.setFont(Utils.fontMain);
		usernameAsk.setHorizontalAlignment(JLabel.CENTER);
		usernameAsk.setVerticalAlignment(JLabel.CENTER);
		this.userNameField = new AutoComboBox(Utils.getTrackedUsers(), Utils.config.getBoolean("autoCompletion", false));
		this.userNameField.setFont(Utils.fontMain);
		this.userNameFieldModel = this.userNameField.getDefModel();
		this.userNameFieldModel.addElement(null);
		this.userNameField.setEditable(true);
		this.userNameField.setPreferredSize(new Dimension(200, 30));
		this.userNameField.setSelectedItem(null);
		this.userNameFieldTextComponent = (JTextComponent) this.userNameField.getEditor().getEditorComponent();
		this.userNameFieldTextComponent.getDocument().addDocumentListener(new UserNameFieldDocumentListener());
		new GhostText((JTextField) this.userNameField.getEditor().getEditorComponent(), Utils.resourceBundle.getString("ghost_username_field"));
		((JTextField) this.userNameField.getEditor().getEditorComponent()).addKeyListener(new UserNameTextFieldKeyListener());
		this.validButon = new JButton(this.iconSearch);
		this.validButon.setFont(Utils.fontMain);
		this.validButon.setToolTipText(Utils.resourceBundle.getString("button_search_tooltip_text"));
		this.validButon.addActionListener(new ValidButtonActionListener());
		JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
		separator.setPreferredSize(new Dimension(100, 100));
		// Construct panel
		GridBagConstraints constraint = new GridBagConstraints();
		constraint.anchor = GridBagConstraints.LINE_START;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.gridwidth = 1;
		constraint.weightx = 0.1;
		constraint.weighty = 1;
		constraint.gridx = 0;
		constraint.gridy = 0;
		searchPanel.add(usernameAsk, constraint);
		constraint.weightx = 0.7;
		constraint.gridx = 1;
		searchPanel.add(this.userNameField, constraint);
		constraint.gridx = 2;
		constraint.weightx = 0.1;
		searchPanel.add(this.validButon, constraint);
		constraint.insets = new Insets(1, 0, 0, 0);
		constraint.gridy = 1;
		constraint.gridx = 0;
		constraint.weightx = 1;
		constraint.gridwidth = 3;
		searchPanel.add(separator, constraint);
		/*************** MODE PANEL **********************/
		Utils.logger.log(Level.INFO, "Creating mode panel...");
		int iconSize = 16;
		Color colorButtonModeSelected = new Color(231, 228, 252);
		Color colorButtonModeUnselected = new Color(190, 168, 244);
		Color colorButtonBorder = new Color(151, 140, 208);
		Color colorTextSelected = new Color(55, 67, 166);
		Color colorTextUnselected = new Color(255, 255, 255);
		JPanel modePanel = new JPanel(new GridBagLayout());
		modePanel.setBackground(Utils.backColor);
		modePanel.addComponentListener(new ModesComponentListener());
		this.buttonStandard = new JButtonMode("osu!");
		this.buttonStandard.setBackground(colorButtonModeSelected);
		this.buttonStandard.setDisabledBackground(colorButtonModeUnselected);
		this.buttonStandard.setBorderColor(colorButtonBorder);
		this.buttonStandard.setDisabledTextColor(colorTextUnselected);
		this.buttonStandard.setForeground(colorTextSelected);
		this.buttonStandard.setUnselectedIconMode(new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/standard.png")), iconSize, iconSize)));
		this.buttonStandard.setIconMode(new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/dark_standard.png")), iconSize, iconSize)));
		this.buttonStandard.setFocusPainted(false);
		this.buttonStandard.addActionListener(new ModeStandardActionListener());
		this.buttonTaiko = new JButtonMode("Taiko");
		this.buttonTaiko.setBackground(colorButtonModeSelected);
		this.buttonTaiko.setDisabledBackground(colorButtonModeUnselected);
		this.buttonTaiko.setBorderColor(colorButtonBorder);
		this.buttonTaiko.setDisabledTextColor(colorTextUnselected);
		this.buttonTaiko.setForeground(colorTextSelected);
		this.buttonTaiko.setUnselectedIconMode(new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/taiko.png")), iconSize, iconSize)));
		this.buttonTaiko.setIconMode(new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/dark_taiko.png")), iconSize, iconSize)));
		this.buttonTaiko.setFocusPainted(false);
		this.buttonTaiko.addActionListener(new ModeTaikoActionListener());
		this.buttonCTB = new JButtonMode("Catch The Beat");
		this.buttonCTB.setBackground(colorButtonModeSelected);
		this.buttonCTB.setDisabledBackground(colorButtonModeUnselected);
		this.buttonCTB.setBorderColor(colorButtonBorder);
		this.buttonCTB.setDisabledTextColor(colorTextUnselected);
		this.buttonCTB.setForeground(colorTextSelected);
		this.buttonCTB.setUnselectedIconMode(new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/ctb.png")), iconSize, iconSize)));
		this.buttonCTB.setIconMode(new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/dark_ctb.png")), iconSize, iconSize)));
		this.buttonCTB.setFocusPainted(false);
		this.buttonCTB.addActionListener(new ModeCTBActionListener());
		this.buttonMania = new JButtonMode("osu!mania");
		this.buttonMania.setBackground(colorButtonModeSelected);
		this.buttonMania.setDisabledBackground(colorButtonModeUnselected);
		this.buttonMania.setBorderColor(colorButtonBorder);
		this.buttonMania.setDisabledTextColor(colorTextUnselected);
		this.buttonMania.setForeground(colorTextSelected);
		this.buttonMania.setUnselectedIconMode(new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/mania.png")), iconSize, iconSize)));
		this.buttonMania.setIconMode(new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/dark_mania.png")), iconSize, iconSize)));
		this.buttonMania.setFocusPainted(false);
		this.buttonMania.addActionListener(new ModeManiaActionListener());
		switchMode(0, false);
		// Construct
		constraint = new GridBagConstraints();
		constraint.anchor = GridBagConstraints.LINE_START;
		constraint.fill = GridBagConstraints.BOTH;
		constraint.gridwidth = 1;
		constraint.weightx = 1;
		constraint.weighty = 1;
		constraint.gridx = 0;
		constraint.gridy = 0;
		modePanel.add(this.buttonStandard, constraint);
		constraint.gridx = 1;
		modePanel.add(this.buttonTaiko, constraint);
		constraint.gridx = 2;
		modePanel.add(this.buttonCTB, constraint);
		constraint.gridx = 3;
		modePanel.add(this.buttonMania, constraint);
		/***************** LEVEL PANEL ********************/
		Utils.logger.log(Level.INFO, "Creating level panel...");
		JPanel levelUserPanel = new JPanel(new BorderLayout());
		levelUserPanel.setBackground(Utils.backColor);
		this.levelBar = new JProgressBar();
		Dimension dim = this.levelBar.getPreferredSize();
		dim.height += 3;
		this.levelBar.setPreferredSize(dim);
		this.levelBar.setMaximum(100);
		this.levelBar.setStringPainted(true);
		this.levelBar.setFont(Utils.fontMain.deriveFont(Font.BOLD, Utils.fontMain.getSize()));
		updateLevel(0D);
		// Construct
		levelUserPanel.add(this.levelBar, BorderLayout.CENTER);
		/***************** TRACK PANEL ********************/
		Utils.logger.log(Level.INFO, "Creating track panel...");
		JPanel trackUserPanel = new JPanel(new GridBagLayout());
		trackUserPanel.setBackground(Utils.backColor);
		this.track = new JCheckBox();
		this.track.setText(Utils.resourceBundle.getString("track_user"));
		this.track.setFont(Utils.fontMain);
		this.track.setEnabled(false);
		this.track.addActionListener(new TrackUserActionListener());
		this.lastStatsDate = new JLabel(Utils.resourceBundle.getString("last_stats_date"));
		this.lastStatsDate.setEnabled(this.track.isSelected());
		this.lastStatsDate.setFont(Utils.fontMain);
		this.statsDateModel = new DefaultComboBoxModel<String>(new String[] {});
		this.lastStatsDateBox = new JComboBox<String>(this.statsDateModel);
		this.lastStatsDateBox.setFont(Utils.fontMain);
		this.lastStatsDateBox.setEnabled(this.track.isEnabled());
		this.lastStatsDateBox.addActionListener(new StatsDateActionListener());
		this.autoUpdateCheck = new JCheckBox();
		this.autoUpdateCheck.setFont(Utils.fontMain);
		this.autoUpdateCheck.setText(Utils.resourceBundle.getString("settings_auto_update"));
		this.autoUpdateCheck.setEnabled(false);
		this.autoUpdateCheck.setSelected(false);
		this.autoUpdateCheck.addItemListener(new AutoUpdateItemListener());
		// Construct
		int lign = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = lign++;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 1;
		trackUserPanel.add(this.track, c);
		c.gridy = lign++;
		trackUserPanel.add(this.autoUpdateCheck, c);
		c.gridwidth = 1;
		c.gridy = lign++;
		c.weightx = 0.1;
		trackUserPanel.add(this.lastStatsDate, c);
		c.gridx = 1;
		c.weightx = 1;
		trackUserPanel.add(this.lastStatsDateBox, c);
		/***************** HITS PANEL ********************/
		Utils.logger.log(Level.INFO, "Creating hits panel...");
		JPanel hitCountPanel = new JPanel(new GridBagLayout());
		hitCountPanel.setBackground(Utils.noticeColor);
		TitledBorder borderHits = BorderFactory.createTitledBorder(Utils.noticeBorder, Utils.resourceBundle.getString("hits"));
		borderHits.setTitleJustification(TitledBorder.CENTER);
		borderHits.setTitlePosition(TitledBorder.CENTER);
		hitCountPanel.setBorder(borderHits);
		float picturesSize = 40f;
		// 300
		JPanel count300Panel = new JPanel();
		count300Panel.setBackground(hitCountPanel.getBackground());
		final ImagePanel count300Picture = new ImagePanel();
		count300Picture.setBackground(hitCountPanel.getBackground());
		count300Picture.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		count300Picture.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		count300Picture.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		count300Picture.setImage(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/hit300.png")), picturesSize, picturesSize));
		this.hitCount300 = new JLabel();
		this.hitCount300.setFont(Utils.fontMain);
		this.hitCount300.setHorizontalAlignment(JLabel.CENTER);
		this.hitCount300.setVerticalAlignment(JLabel.CENTER);
		count300Panel.add(count300Picture);
		count300Panel.add(this.hitCount300);
		// 100
		JPanel count100Panel = new JPanel();
		count100Panel.setBackground(hitCountPanel.getBackground());
		final ImagePanel count100Picture = new ImagePanel();
		count100Picture.setBackground(hitCountPanel.getBackground());
		count100Picture.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		count100Picture.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		count100Picture.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		count100Picture.setImage(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/hit100.png")), picturesSize, picturesSize));
		this.hitCount100 = new JLabel();
		this.hitCount100.setFont(Utils.fontMain);
		this.hitCount100.setHorizontalAlignment(JLabel.CENTER);
		this.hitCount100.setVerticalAlignment(JLabel.CENTER);
		count100Panel.add(count100Picture);
		count100Panel.add(this.hitCount100);
		// 50
		JPanel count50Panel = new JPanel();
		count50Panel.setBackground(hitCountPanel.getBackground());
		picturesSize = 30f;
		final ImagePanel count50Picture = new ImagePanel();
		count50Picture.setBackground(hitCountPanel.getBackground());
		count50Picture.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		count50Picture.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		count50Picture.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		count50Picture.setImage(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/hit50.png")), picturesSize, picturesSize));
		this.hitCount50 = new JLabel();
		this.hitCount50.setFont(Utils.fontMain);
		this.hitCount50.setHorizontalAlignment(JLabel.CENTER);
		this.hitCount50.setVerticalAlignment(JLabel.CENTER);
		count50Panel.add(count50Picture);
		count50Panel.add(this.hitCount50);
		// Construct
		constraint = new GridBagConstraints();
		constraint.anchor = GridBagConstraints.CENTER;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.gridwidth = 1;
		constraint.weightx = 1;
		constraint.weighty = 1;
		constraint.gridx = 0;
		constraint.gridy = 0;
		hitCountPanel.add(count300Panel, constraint);
		constraint.gridx = 1;
		hitCountPanel.add(count100Panel, constraint);
		constraint.gridx = 2;
		hitCountPanel.add(count50Panel, constraint);
		/***************** RANK PANEL ********************/
		Utils.logger.log(Level.INFO, "Creating rank panel...");
		JPanel ranksUserPanel = new JPanel(new GridBagLayout());
		ranksUserPanel.setBackground(Utils.noticeColor);
		TitledBorder borderRanks = BorderFactory.createTitledBorder(Utils.noticeBorder, Utils.resourceBundle.getString("ranks"));
		borderRanks.setTitleJustification(TitledBorder.CENTER);
		borderRanks.setTitlePosition(TitledBorder.CENTER);
		ranksUserPanel.setBorder(borderRanks);
		picturesSize = 40f;
		// SS
		JPanel ssPanel = new JPanel();
		ssPanel.setBackground(hitCountPanel.getBackground());
		final ImagePanel ssPicture = new ImagePanel();
		ssPicture.setBackground(hitCountPanel.getBackground());
		ssPicture.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		ssPicture.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		ssPicture.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		ssPicture.setImage(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/SS.png")), picturesSize, picturesSize));
		this.countSS = new JLabel();
		this.countSS.setFont(Utils.fontMain);
		this.countSS.setHorizontalAlignment(JLabel.CENTER);
		this.countSS.setVerticalAlignment(JLabel.CENTER);
		ssPanel.add(ssPicture);
		ssPanel.add(this.countSS);
		// S
		JPanel sPanel = new JPanel();
		sPanel.setBackground(hitCountPanel.getBackground());
		final ImagePanel sPicture = new ImagePanel();
		sPicture.setBackground(hitCountPanel.getBackground());
		sPicture.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		sPicture.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		sPicture.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		sPicture.setImage(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/S.png")), picturesSize, picturesSize));
		this.countS = new JLabel();
		this.countS.setFont(Utils.fontMain);
		this.countS.setHorizontalAlignment(JLabel.CENTER);
		this.countS.setVerticalAlignment(JLabel.CENTER);
		sPanel.add(sPicture);
		sPanel.add(this.countS);
		// A
		JPanel aPanel = new JPanel();
		aPanel.setBackground(hitCountPanel.getBackground());
		final ImagePanel aPicture = new ImagePanel();
		aPicture.setBackground(hitCountPanel.getBackground());
		aPicture.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		aPicture.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		aPicture.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		aPicture.setImage(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/A.png")), picturesSize, picturesSize));
		this.countA = new JLabel();
		this.countA.setFont(Utils.fontMain);
		this.countA.setHorizontalAlignment(JLabel.CENTER);
		this.countA.setVerticalAlignment(JLabel.CENTER);
		aPanel.add(aPicture);
		aPanel.add(this.countA);
		// Construct
		constraint = new GridBagConstraints();
		constraint.anchor = GridBagConstraints.LINE_START;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.gridwidth = 1;
		constraint.weightx = 1;
		constraint.weighty = 1;
		constraint.gridx = 0;
		constraint.gridy = 0;
		ranksUserPanel.add(ssPanel, constraint);
		constraint.gridx = 1;
		ranksUserPanel.add(sPanel, constraint);
		constraint.gridx = 2;
		ranksUserPanel.add(aPanel, constraint);
		/******************** USER PANEL *****************/
		Utils.logger.log(Level.INFO, "Creating user panel...");
		JPanel avatarPanel = new JPanel(new GridBagLayout());
		avatarPanel.setBackground(Utils.backColor);
		int avatarSize = 128;
		this.avatar = new ImagePanel(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/osu_logo.png")), avatarSize, avatarSize));
		this.avatar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.avatar.setBackground(Utils.backColor);
		this.avatar.setToolTipText(Utils.resourceBundle.getString("open_profile"));
		this.avatar.setMinimumSize(new Dimension(avatarSize, avatarSize));
		this.avatar.setPreferredSize(new Dimension(avatarSize, avatarSize));
		this.avatar.setMaximumSize(new Dimension(avatarSize, avatarSize));
		this.avatar.addMouseListener(new OpenProfileMouseListener());
		this.username = new JLabel(" ");
		this.username.setToolTipText(Utils.resourceBundle.getString("open_profile"));
		this.username.addMouseListener(new OpenProfileMouseListener());
		this.username.setOpaque(true);
		this.username.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.username.setBackground(Utils.backColor);
		this.username.setFont(Utils.fontMain.deriveFont(Font.PLAIN, 25));
		// Construct
		constraint = new GridBagConstraints();
		constraint.fill = GridBagConstraints.LINE_START;
		constraint.anchor = GridBagConstraints.CENTER;
		constraint.gridwidth = 3;
		constraint.weightx = 0.1;
		constraint.weighty = 1;
		constraint.gridx = 0;
		constraint.gridy = 0;
		avatarPanel.add(this.avatar, constraint);
		constraint.gridy = 1;
		constraint.insets = new Insets(5, 0, 3, 0);
		avatarPanel.add(this.username, constraint);
		/**************** OTHERS PANEL *********************/
		Utils.logger.log(Level.INFO, "Creating other panel...");
		JPanel otherPanel = new JPanel(new MigLayout());
		otherPanel.setBackground(Utils.noticeColor);
		otherPanel.setBackground(Utils.noticeColor);
		TitledBorder borderOther = BorderFactory.createTitledBorder(Utils.noticeBorder, Utils.resourceBundle.getString("stats"));
		borderOther.setTitleJustification(TitledBorder.CENTER);
		borderOther.setTitlePosition(TitledBorder.CENTER);
		otherPanel.setBorder(borderOther);
		// PlayCount
		JLabel playCountLabel = new JLabel(Utils.resourceBundle.getString("play_count") + " : ");
		playCountLabel.setFont(Utils.fontMain);
		playCountLabel.setHorizontalAlignment(JLabel.RIGHT);
		playCountLabel.setVerticalAlignment(JLabel.CENTER);
		this.playCount = new JLabel();
		this.playCount.setFont(Utils.fontMain);
		this.playCount.setHorizontalAlignment(JLabel.LEFT);
		this.playCount.setVerticalAlignment(JLabel.CENTER);
		// RankedScore
		JLabel rankedScoreLabel = new JLabel(Utils.resourceBundle.getString("ranked_score") + " : ");
		rankedScoreLabel.setFont(Utils.fontMain);
		rankedScoreLabel.setHorizontalAlignment(JLabel.RIGHT);
		rankedScoreLabel.setVerticalAlignment(JLabel.CENTER);
		this.rankedScore = new JLabel();
		this.rankedScore.setFont(Utils.fontMain);
		this.rankedScore.setHorizontalAlignment(JLabel.LEFT);
		this.rankedScore.setVerticalAlignment(JLabel.CENTER);
		// TotalScore
		JLabel totalScoreLabel = new JLabel(Utils.resourceBundle.getString("total_score") + " : ");
		totalScoreLabel.setFont(Utils.fontMain);
		totalScoreLabel.setHorizontalAlignment(JLabel.RIGHT);
		totalScoreLabel.setVerticalAlignment(JLabel.CENTER);
		this.totalScore = new JLabel();
		this.totalScore.setFont(Utils.fontMain);
		this.totalScore.setHorizontalAlignment(JLabel.LEFT);
		this.totalScore.setVerticalAlignment(JLabel.CENTER);
		// PP
		JLabel ppCountLabel = new JLabel("PP : ");
		ppCountLabel.setFont(Utils.fontMain);
		ppCountLabel.setHorizontalAlignment(JLabel.RIGHT);
		ppCountLabel.setVerticalAlignment(JLabel.CENTER);
		this.ppCount = new JLabel();
		this.ppCount.setFont(Utils.fontMain);
		this.ppCount.setHorizontalAlignment(JLabel.LEFT);
		this.ppCount.setVerticalAlignment(JLabel.CENTER);
		// Accuracy
		JLabel accuracyLabel = new JLabel(Utils.resourceBundle.getString("accuracy") + " : ");
		accuracyLabel.setFont(Utils.fontMain);
		accuracyLabel.setHorizontalAlignment(JLabel.RIGHT);
		accuracyLabel.setVerticalAlignment(JLabel.CENTER);
		this.accuracy = new JLabel();
		this.accuracy.setFont(Utils.fontMain);
		this.accuracy.setHorizontalAlignment(JLabel.LEFT);
		this.accuracy.setVerticalAlignment(JLabel.CENTER);
		// Country
		picturesSize = 16;
		JLabel countryLabel = new JLabel(Utils.resourceBundle.getString("country") + " : ");
		countryLabel.setFont(Utils.fontMain);
		countryLabel.setHorizontalAlignment(JLabel.RIGHT);
		countryLabel.setVerticalAlignment(JLabel.CENTER);
		this.country = new JLabel();
		this.country.setFont(Utils.fontMain);
		this.country.setHorizontalAlignment(JLabel.LEFT);
		this.country.setVerticalAlignment(JLabel.CENTER);
		this.countryFlag = new ImagePanel();
		this.countryFlag.setPrintLoading(false);
		this.countryFlag.setBackground(Utils.noticeColor);
		this.countryFlag.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		this.countryFlag.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		this.countryFlag.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		// Total hits
		JLabel totalHitsLabel = new JLabel(Utils.resourceBundle.getString("total_hits") + " : ");
		totalHitsLabel.setFont(Utils.fontMain);
		totalHitsLabel.setHorizontalAlignment(JLabel.RIGHT);
		totalHitsLabel.setVerticalAlignment(JLabel.CENTER);
		this.totalHits = new JLabel();
		this.totalHits.setFont(Utils.fontMain);
		this.totalHits.setHorizontalAlignment(JLabel.LEFT);
		this.totalHits.setVerticalAlignment(JLabel.CENTER);
		// Construct
		lign = 0;
		otherPanel.add(playCountLabel, new CC().cell(0, lign).alignX("right"));
		otherPanel.add(this.playCount, new CC().cell(1, lign++, 2, 1).alignX("left").gapLeft("5"));
		otherPanel.add(rankedScoreLabel, new CC().cell(0, lign).alignX("right"));
		otherPanel.add(this.rankedScore, new CC().cell(1, lign++, 2, 1).alignX("left").gapLeft("5"));
		otherPanel.add(totalScoreLabel, new CC().cell(0, lign).alignX("right"));
		otherPanel.add(this.totalScore, new CC().cell(1, lign++, 2, 1).alignX("left").gapLeft("5"));
		otherPanel.add(ppCountLabel, new CC().cell(0, lign).alignX("right"));
		otherPanel.add(this.ppCount, new CC().cell(1, lign++, 2, 1).alignX("left").gapLeft("5"));
		otherPanel.add(accuracyLabel, new CC().cell(0, lign).alignX("right"));
		otherPanel.add(this.accuracy, new CC().cell(1, lign++, 2, 1).alignX("left").gapLeft("5"));
		otherPanel.add(countryLabel, new CC().cell(0, lign).alignX("right"));
		otherPanel.add(this.countryFlag, new CC().cell(1, lign).alignX("left").gapLeft("5"));
		otherPanel.add(this.country, new CC().cell(2, lign++, 2, 1).alignX("left").gapLeft("2"));
		otherPanel.add(totalHitsLabel, new CC().cell(0, lign).alignX("right"));
		otherPanel.add(this.totalHits, new CC().cell(1, lign++, 2, 1).alignX("left").gapLeft("5"));
		/*************** FRAME CONSTRUCT ******************/
		Utils.logger.log(Level.INFO, "Creating frame panel...");
		constraint = new GridBagConstraints();
		constraint.anchor = GridBagConstraints.PAGE_START;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		int line = 0;
		constraint.gridwidth = GridBagConstraints.REMAINDER;
		constraint.weightx = 1;
		constraint.weighty = 1;
		constraint.gridx = 0;
		constraint.gridy = line++;
		getFrame().getContentPane().add(searchPanel, constraint);
		constraint.insets = new Insets(1, 0, 2, 0);
		constraint.gridy = line++;
		getFrame().getContentPane().add(modePanel, constraint);
		constraint.insets = new Insets(10, 0, 0, 0);
		constraint.gridy = line++;
		getFrame().getContentPane().add(avatarPanel, constraint);
		constraint.insets = new Insets(0, 0, 0, 0);
		constraint.gridy = line++;
		constraint.fill = GridBagConstraints.BOTH;
		getFrame().getContentPane().add(levelUserPanel, constraint);
		constraint.gridy = line++;
		constraint.insets = new Insets(2, 4, 2, 4);
		getFrame().getContentPane().add(otherPanel, constraint);
		constraint.gridy = line++;
		getFrame().getContentPane().add(hitCountPanel, constraint);
		constraint.gridy = line++;
		getFrame().getContentPane().add(ranksUserPanel, constraint);
		constraint.gridy = line++;
		getFrame().getContentPane().add(trackUserPanel, constraint);
		Utils.logger.log(Level.INFO, "Packing frame...");
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		getFrame().setLocation(new Point((dimension.width - 700) / 2, (dimension.height - 130) / 2));
		getFrame().pack();
		getFrame().setVisible(true);
		getFrame().toFront();
	}

	public void activateFrame()
	{
		getFrame().setFocusable(true);
		getFrame().setEnabled(true);
	}

	public void addTrackedUser(User user)
	{
		this.userNameFieldModel.addElement(user.getUsername());
		this.lastStatsDate.setEnabled(this.track.isSelected());
		this.lastStatsDateBox.setEnabled(this.track.isSelected());
		this.autoUpdateCheck.setEnabled(this.track.isSelected());
	}

	public void backFromTray()
	{
		getFrame().setState(JFrame.NORMAL);
		showFrame();
		getFrame().toFront();
	}

	public void desactivateFrame()
	{
		getFrame().setFocusable(false);
		getFrame().setEnabled(false);
	}

	public void displayStats(User user, Stats stats)
	{
		updateLevel(stats.getLevel());
		this.countSS.setText(String.valueOf(stats.getCountSS()));
		this.countS.setText(String.valueOf(stats.getCountS()));
		this.countA.setText(String.valueOf(stats.getCountA()));
		this.totalScore.setText(String.format(Utils.resourceBundle.getString("total_score_value"), NumberFormat.getInstance(Utils.locale).format(stats.getTotalScore()), NumberFormat.getInstance(Utils.locale).format(Utils.getScoreToNextLevel(Utils.getLevel(stats.getLevel()), stats.getTotalScore())), Utils.getLevel(stats.getLevel()) + 1));
		this.country.setText(CountryCode.getByCode(user.getCountry()).getName());
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setMaximumFractionDigits(2);
		this.hitCount300.setText(NumberFormat.getInstance(Utils.locale).format(stats.getCount300()) + " (" + decimalFormat.format(stats.getCount300() * 100f / stats.getTotalHits()) + "%)");
		this.hitCount100.setText(NumberFormat.getInstance(Utils.locale).format(stats.getCount100()) + " (" + decimalFormat.format(stats.getCount100() * 100f / stats.getTotalHits()) + "%)");
		this.hitCount50.setText(NumberFormat.getInstance(Utils.locale).format(stats.getCount50()) + " (" + decimalFormat.format(stats.getCount50() * 100f / stats.getTotalHits()) + "%)");
	}

	public Interface getFrame()
	{
		return this;
	}

	public long getSelectedDate()
	{
		String date = this.lastStatsDateBox.getSelectedItem().toString();
		if(date.equals(Utils.resourceBundle.getString("last_date_saved")))
			return -1;
		DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM);
		SimpleDateFormat simpleFormat = (SimpleDateFormat) format;
		DateTimeFormatter formatter = DateTimeFormat.forPattern(simpleFormat.toPattern());
		return formatter.parseDateTime(date).toDate().getTime();
	}

	public int getSelectedMode()
	{
		if(!this.buttonTaiko.isEnabled())
			return 1;
		if(!this.buttonCTB.isEnabled())
			return 2;
		if(!this.buttonMania.isEnabled())
			return 3;
		return 0;
	}

	public void hideFrame()
	{
		getFrame().setEnabled(false);
		getFrame().setFocusable(false);
	}

	public void removeTrackedUser(User user)
	{
		this.userNameFieldModel.removeElement(user.getUsername());
		this.userNameField.setSelectedItem(null);
		this.lastStatsDate.setEnabled(this.track.isSelected());
		this.lastStatsDateBox.setEnabled(this.track.isSelected());
		this.autoUpdateCheck.setEnabled(this.track.isSelected());
		this.autoUpdateCheck.setSelected(false);
	}

	public void setFlagAndAvatar(final User user)
	{
		this.avatar.setImage(null);
		this.countryFlag.setImage(null);
		this.autoUpdateCheck.setSelected(false);
		Runnable task = new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					Thread.currentThread().sleep(2500);
				}
				catch(InterruptedException e1)
				{
					e1.printStackTrace();
				}
				try
				{
					Interface.this.avatar.setImage(Utils.resizeBufferedImage(Utils.getAvatar(String.valueOf(user.getUserID())), 128, 128));
					Interface.this.countryFlag.setImage(Utils.resizeBufferedImage(Utils.getCountryFlag(user.getCountry()), 16, 16));
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		};
		new Thread(task, "ThreadImages").start();
	}

	public void setValidButonIcon(String string)
	{
		if(string.equalsIgnoreCase("R"))
			this.validButon.setIcon(this.iconRefresh);
		else
			this.validButon.setIcon(this.iconSearch);
	}

	public void showFrame()
	{
		getFrame().setEnabled(true);
		getFrame().setFocusable(true);
		getFrame().setVisible(true);
	}

	public void switchMode(int mode, boolean checkInfos)
	{
		this.buttonStandard.setEnabled(true);
		this.buttonTaiko.setEnabled(true);
		this.buttonCTB.setEnabled(true);
		this.buttonMania.setEnabled(true);
		switch(mode)
		{
			case 0:
				this.buttonStandard.setEnabled(false);
				break;
			case 1:
				this.buttonTaiko.setEnabled(false);
				break;
			case 2:
				this.buttonCTB.setEnabled(false);
				break;
			case 3:
				this.buttonMania.setEnabled(false);
				break;
		}
		if(checkInfos)
			Utils.getInfos(Utils.lastUser.getUsername(), false);
	}

	public void updateAutoCompletionStatus(boolean status)
	{
		this.userNameField.setAutoCompletion(status);
	}

	public void updateInfos(boolean showerror)
	{
		Utils.getInfos(Utils.lastUser.getUsername(), showerror);
	}

	private void updateLevel(double level)
	{
		Utils.logger.log(Level.INFO, "Setting level to " + level);
		double progress = Utils.round(Utils.getProgressLevel(level) * 100, 2);
		this.levelBar.setValue((int) progress);
		this.levelBar.setString(String.format(Utils.resourceBundle.getString("level"), Utils.getLevel(level), progress));
	}

	public void updateStatsDates(User user)
	{
		String lastDate = (String) this.statsDateModel.getSelectedItem();
		this.statsDateModel.removeAllElements();
		for(String date : user.getAvalidbleStatsDates(getSelectedMode()))
			this.statsDateModel.addElement(date);
		this.statsDateModel.addElement(Utils.resourceBundle.getString("last_date_saved"));
		if(Utils.config.getBoolean("keepDate", false) && lastDate != null && !lastDate.equalsIgnoreCase("") && !lastDate.equalsIgnoreCase("null"))
			this.lastStatsDateBox.setSelectedItem(lastDate);
		else
			this.lastStatsDateBox.setSelectedIndex(this.statsDateModel.getSize() - 1);
	}

	public void updateTrackedInfos()
	{
		updateTrackedInfos(Utils.lastUser.getUsername(), Utils.lastUser.getLastStats(Utils.mainFrame.getSelectedMode()), Utils.lastUser.getStatsByModeAndDate(Utils.mainFrame.getSelectedMode(), Utils.mainFrame.getSelectedDate(), Utils.lastStats), true);
	}

	public void updateTrackedInfos(String user, Stats currentStats, Stats previousStats, boolean showNotification)
	{
		Utils.logger.log(Level.INFO, "Updating tracked infos...");
		this.username.setText("<html><body><nobr>  " + user + " (#" + NumberFormat.getInstance(Utils.locale).format(currentStats.getRank()) + ")" + currentStats.compareRank(previousStats) + "  </nobr></body></html>");
		this.accuracy.setText(String.valueOf(Utils.round(currentStats.getAccuracy(), 2)) + "%" + currentStats.compareAccuracy(previousStats));
		this.playCount.setText(NumberFormat.getInstance(Utils.locale).format(currentStats.getPlayCount()) + currentStats.comparePlayCount(previousStats));
		this.rankedScore.setText(NumberFormat.getInstance(Utils.locale).format(currentStats.getRankedScore()) + currentStats.compareRankedScore(previousStats));
		this.totalHits.setText(NumberFormat.getInstance(Utils.locale).format(currentStats.getTotalHits()) + currentStats.compareTotalHits(previousStats));
		this.ppCount.setText(NumberFormat.getInstance(Utils.locale).format(currentStats.getPp()) + currentStats.comparePP(previousStats));
		if(Utils.config.getBoolean("showNotification", false) && showNotification && !(currentStats.getDiffRank(previousStats) == 0))
			new InterfaceNotification(String.format(Utils.resourceBundle.getString("notification_text"), user, currentStats.getDiffRank(previousStats) > 0 ? Utils.resourceBundle.getString("won") : Utils.resourceBundle.getString("lost"), Math.abs(currentStats.getDiffRank(previousStats)), currentStats.getDiffPP(previousStats), currentStats.getDiffPlayCount(previousStats), currentStats.getDiffTotalHits(previousStats)));
	}
}
