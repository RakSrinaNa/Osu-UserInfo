package fr.mrcraftcod.interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
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
import org.json.JSONObject;
import fr.mrcraftcod.Main;
import fr.mrcraftcod.actions.ActionRefreshStats;
import fr.mrcraftcod.listeners.AutoUpdateItemListener;
import fr.mrcraftcod.listeners.UserNameFieldDocumentListener;
import fr.mrcraftcod.listeners.UserNameTextFieldKeyListener;
import fr.mrcraftcod.listeners.actions.ItemAboutActionListener;
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
import fr.mrcraftcod.utils.Configuration;
import fr.mrcraftcod.utils.CountryCode;
import fr.mrcraftcod.utils.LoadingWorker;
import fr.mrcraftcod.utils.Utils;

public class Interface // TODO Javadoc
{
	private JFrame frame;
	private JTextComponent userNameFieldTextComponent;
	private ImageIcon iconRefresh, iconSearch;
	private BufferedImage avatarDefaultImage;
	private ImagePanel avatar, countryFlag;
	private AutoComboBox userNameField;
	private JComboBox<String> lastStatsDateBox;
	private DefaultComboBoxModel<String> statsDateModel, userNameFieldModel;
	private JButton validButon;
	private JButtonMode buttonStandard, buttonTaiko, buttonCTB, buttonMania;
	private JLabel lastStatsDate, totalHits, username, countSS, countS, countA, playCount, rankedScore, totalScore, ppCount, accuracy, country, hitCount300, hitCount100, hitCount50;
	private JProgressBar levelBar;
	private JCheckBox track, autoUpdateCheck;

	@SuppressWarnings("unchecked")
	public Interface() throws IOException
	{
		int pictureButtonSize = 20;
		Utils.logger.log(Level.INFO, "Loading icons...");
		iconRefresh = new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/refresh.png")), pictureButtonSize, pictureButtonSize));
		iconSearch = new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/search.png")), pictureButtonSize, pictureButtonSize));
		avatarDefaultImage = ImageIO.read(Main.class.getClassLoader().getResource("resources/images/avatar.png"));
		/************** FRAME INFOS ********************/
		Utils.logger.log(Level.INFO, "Setting frame options...");
		setFrame(new JFrame(Main.APPNAME + " v" + Main.VERSION));
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
		JMenuItem itemSettings = new JMenuItem(Utils.resourceBundle.getString("settings"));
		itemSettings.setFont(menuBarFont);
		itemSettings.addActionListener(new ItemSettingsActionListener());
		JMenuItem itemAbout = new JMenuItem(Utils.resourceBundle.getString("menu_bar_help_about"));
		itemAbout.setFont(menuBarFont);
		itemAbout.addActionListener(new ItemAboutActionListener());
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
		userNameField = new AutoComboBox(Utils.getTrackedUsers(), Utils.config.getBoolean("autoCompletion", false));
		userNameField.setFont(Utils.fontMain);
		userNameFieldModel = userNameField.getDefModel();
		userNameField.setEditable(true);
		userNameField.setPreferredSize(new Dimension(200, 30));
		userNameField.setSelectedItem(null);
		userNameFieldTextComponent = (JTextComponent) userNameField.getEditor().getEditorComponent();
		userNameFieldTextComponent.getDocument().addDocumentListener(new UserNameFieldDocumentListener());
		new GhostText(((JTextField) userNameField.getEditor().getEditorComponent()), Utils.resourceBundle.getString("ghost_username_field"));
		((JTextField) userNameField.getEditor().getEditorComponent()).addKeyListener(new UserNameTextFieldKeyListener());
		validButon = new JButton(iconSearch);
		validButon.setFont(Utils.fontMain);
		validButon.setToolTipText(Utils.resourceBundle.getString("button_search_tooltip_text"));
		validButon.addActionListener(new ValidButtonActionListener());
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
		searchPanel.add(userNameField, constraint);
		constraint.gridx = 2;
		constraint.weightx = 0.1;
		searchPanel.add(validButon, constraint);
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
		buttonStandard = new JButtonMode("osu!");
		buttonStandard.setBackground(colorButtonModeSelected);
		buttonStandard.setDisabledBackground(colorButtonModeUnselected);
		buttonStandard.setBorderColor(colorButtonBorder);
		buttonStandard.setDisabledTextColor(colorTextUnselected);
		buttonStandard.setForeground(colorTextSelected);
		buttonStandard.setUnselectedIconMode(new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/standard.png")), iconSize, iconSize)));
		buttonStandard.setIconMode(new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/dark_standard.png")), iconSize, iconSize)));
		buttonStandard.setFocusPainted(false);
		buttonStandard.addActionListener(new ModeStandardActionListener());
		buttonTaiko = new JButtonMode("Taiko");
		buttonTaiko.setBackground(colorButtonModeSelected);
		buttonTaiko.setDisabledBackground(colorButtonModeUnselected);
		buttonTaiko.setBorderColor(colorButtonBorder);
		buttonTaiko.setDisabledTextColor(colorTextUnselected);
		buttonTaiko.setForeground(colorTextSelected);
		buttonTaiko.setUnselectedIconMode(new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/taiko.png")), iconSize, iconSize)));
		buttonTaiko.setIconMode(new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/dark_taiko.png")), iconSize, iconSize)));
		buttonTaiko.setFocusPainted(false);
		buttonTaiko.addActionListener(new ModeTaikoActionListener());
		buttonCTB = new JButtonMode("Catch The Beat");
		buttonCTB.setBackground(colorButtonModeSelected);
		buttonCTB.setDisabledBackground(colorButtonModeUnselected);
		buttonCTB.setBorderColor(colorButtonBorder);
		buttonCTB.setDisabledTextColor(colorTextUnselected);
		buttonCTB.setForeground(colorTextSelected);
		buttonCTB.setUnselectedIconMode(new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/ctb.png")), iconSize, iconSize)));
		buttonCTB.setIconMode(new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/dark_ctb.png")), iconSize, iconSize)));
		buttonCTB.setFocusPainted(false);
		buttonCTB.addActionListener(new ModeCTBActionListener());
		buttonMania = new JButtonMode("osu!mania");
		buttonMania.setBackground(colorButtonModeSelected);
		buttonMania.setDisabledBackground(colorButtonModeUnselected);
		buttonMania.setBorderColor(colorButtonBorder);
		buttonMania.setDisabledTextColor(colorTextUnselected);
		buttonMania.setForeground(colorTextSelected);
		buttonMania.setUnselectedIconMode(new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/mania.png")), iconSize, iconSize)));
		buttonMania.setIconMode(new ImageIcon(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/dark_mania.png")), iconSize, iconSize)));
		buttonMania.setFocusPainted(false);
		buttonMania.addActionListener(new ModeManiaActionListener());
		switchMode(0, false);
		// Construct
		constraint = new GridBagConstraints();
		constraint.anchor = GridBagConstraints.LINE_START;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.gridwidth = 1;
		constraint.weightx = 1;
		constraint.weighty = 1;
		constraint.gridx = 0;
		constraint.gridy = 0;
		modePanel.add(buttonStandard, constraint);
		constraint.gridx = 1;
		modePanel.add(buttonTaiko, constraint);
		constraint.gridx = 2;
		modePanel.add(buttonCTB, constraint);
		constraint.gridx = 3;
		modePanel.add(buttonMania, constraint);
		/***************** LEVEL PANEL ********************/
		Utils.logger.log(Level.INFO, "Creating level panel...");
		JPanel levelUserPanel = new JPanel(new BorderLayout());
		levelUserPanel.setBackground(Utils.backColor);
		levelBar = new JProgressBar();
		Dimension dim = levelBar.getPreferredSize();
		dim.height += 3;
		levelBar.setPreferredSize(dim);
		levelBar.setMaximum(100);
		levelBar.setStringPainted(true);
		levelBar.setFont(Utils.fontMain.deriveFont(Font.BOLD, Utils.fontMain.getSize()));
		updateLevel(0D);
		// Construct
		levelUserPanel.add(levelBar, BorderLayout.CENTER);
		/***************** TRACK PANEL ********************/
		Utils.logger.log(Level.INFO, "Creating track panel...");
		JPanel trackUserPanel = new JPanel(new GridBagLayout());
		trackUserPanel.setBackground(Utils.backColor);
		track = new JCheckBox();
		track.setText(Utils.resourceBundle.getString("track_user"));
		track.setFont(Utils.fontMain);
		track.setEnabled(false);
		track.addActionListener(new TrackUserActionListener());
		lastStatsDate = new JLabel(Utils.resourceBundle.getString("last_stats_date"));
		lastStatsDate.setEnabled(track.isSelected());
		lastStatsDate.setFont(Utils.fontMain);
		statsDateModel = new DefaultComboBoxModel<String>(new String[] {});
		lastStatsDateBox = new JComboBox<String>(statsDateModel);
		lastStatsDateBox.setFont(Utils.fontMain);
		lastStatsDateBox.setEnabled(track.isEnabled());
		lastStatsDateBox.addActionListener(new StatsDateActionListener());
		autoUpdateCheck = new JCheckBox();
		autoUpdateCheck.setFont(Utils.fontMain);
		autoUpdateCheck.setText(Utils.resourceBundle.getString("settings_auto_update"));
		autoUpdateCheck.setEnabled(false);
		autoUpdateCheck.setSelected(false);
		autoUpdateCheck.addItemListener(new AutoUpdateItemListener());
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
		trackUserPanel.add(track, c);
		c.gridy = lign++;
		trackUserPanel.add(autoUpdateCheck, c);
		c.gridwidth = 1;
		c.gridy = lign++;
		c.weightx = 0.1;
		trackUserPanel.add(lastStatsDate, c);
		c.gridx = 1;
		c.weightx = 1;
		trackUserPanel.add(lastStatsDateBox, c);
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
		hitCount300 = new JLabel();
		hitCount300.setFont(Utils.fontMain);
		hitCount300.setHorizontalAlignment(JLabel.CENTER);
		hitCount300.setVerticalAlignment(JLabel.CENTER);
		count300Panel.add(count300Picture);
		count300Panel.add(hitCount300);
		// 100
		JPanel count100Panel = new JPanel();
		count100Panel.setBackground(hitCountPanel.getBackground());
		final ImagePanel count100Picture = new ImagePanel();
		count100Picture.setBackground(hitCountPanel.getBackground());
		count100Picture.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		count100Picture.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		count100Picture.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		count100Picture.setImage(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/hit100.png")), picturesSize, picturesSize));
		hitCount100 = new JLabel();
		hitCount100.setFont(Utils.fontMain);
		hitCount100.setHorizontalAlignment(JLabel.CENTER);
		hitCount100.setVerticalAlignment(JLabel.CENTER);
		count100Panel.add(count100Picture);
		count100Panel.add(hitCount100);
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
		hitCount50 = new JLabel();
		hitCount50.setFont(Utils.fontMain);
		hitCount50.setHorizontalAlignment(JLabel.CENTER);
		hitCount50.setVerticalAlignment(JLabel.CENTER);
		count50Panel.add(count50Picture);
		count50Panel.add(hitCount50);
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
		countSS = new JLabel();
		countSS.setFont(Utils.fontMain);
		countSS.setHorizontalAlignment(JLabel.CENTER);
		countSS.setVerticalAlignment(JLabel.CENTER);
		ssPanel.add(ssPicture);
		ssPanel.add(countSS);
		// S
		JPanel sPanel = new JPanel();
		sPanel.setBackground(hitCountPanel.getBackground());
		final ImagePanel sPicture = new ImagePanel();
		sPicture.setBackground(hitCountPanel.getBackground());
		sPicture.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		sPicture.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		sPicture.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		sPicture.setImage(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/S.png")), picturesSize, picturesSize));
		countS = new JLabel();
		countS.setFont(Utils.fontMain);
		countS.setHorizontalAlignment(JLabel.CENTER);
		countS.setVerticalAlignment(JLabel.CENTER);
		sPanel.add(sPicture);
		sPanel.add(countS);
		// A
		JPanel aPanel = new JPanel();
		aPanel.setBackground(hitCountPanel.getBackground());
		final ImagePanel aPicture = new ImagePanel();
		aPicture.setBackground(hitCountPanel.getBackground());
		aPicture.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		aPicture.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		aPicture.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		aPicture.setImage(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/A.png")), picturesSize, picturesSize));
		countA = new JLabel();
		countA.setFont(Utils.fontMain);
		countA.setHorizontalAlignment(JLabel.CENTER);
		countA.setVerticalAlignment(JLabel.CENTER);
		aPanel.add(aPicture);
		aPanel.add(countA);
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
		avatar = new ImagePanel(Utils.resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/osu_logo.png")), avatarSize, avatarSize));
		avatar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		avatar.setBackground(Utils.backColor);
		avatar.setToolTipText(Utils.resourceBundle.getString("open_profile"));
		avatar.setMinimumSize(new Dimension(avatarSize, avatarSize));
		avatar.setPreferredSize(new Dimension(avatarSize, avatarSize));
		avatar.setMaximumSize(new Dimension(avatarSize, avatarSize));
		avatar.addMouseListener(new OpenProfileMouseListener());
		username = new JLabel(" ");
		username.setToolTipText(Utils.resourceBundle.getString("open_profile"));
		username.addMouseListener(new OpenProfileMouseListener());
		username.setOpaque(true);
		username.setCursor(new Cursor(Cursor.HAND_CURSOR));
		username.setBackground(Utils.backColor);
		username.setFont(Utils.fontMain.deriveFont(Font.PLAIN, 25));
		// Construct
		constraint = new GridBagConstraints();
		constraint.fill = GridBagConstraints.LINE_START;
		constraint.anchor = GridBagConstraints.CENTER;
		constraint.gridwidth = 3;
		constraint.weightx = 0.1;
		constraint.weighty = 1;
		constraint.gridx = 0;
		constraint.gridy = 0;
		avatarPanel.add(avatar, constraint);
		constraint.gridy = 1;
		constraint.insets = new Insets(5, 0, 3, 0);
		avatarPanel.add(username, constraint);
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
		playCount = new JLabel();
		playCount.setFont(Utils.fontMain);
		playCount.setHorizontalAlignment(JLabel.LEFT);
		playCount.setVerticalAlignment(JLabel.CENTER);
		// RankedScore
		JLabel rankedScoreLabel = new JLabel(Utils.resourceBundle.getString("ranked_score") + " : ");
		rankedScoreLabel.setFont(Utils.fontMain);
		rankedScoreLabel.setHorizontalAlignment(JLabel.RIGHT);
		rankedScoreLabel.setVerticalAlignment(JLabel.CENTER);
		rankedScore = new JLabel();
		rankedScore.setFont(Utils.fontMain);
		rankedScore.setHorizontalAlignment(JLabel.LEFT);
		rankedScore.setVerticalAlignment(JLabel.CENTER);
		// TotalScore
		JLabel totalScoreLabel = new JLabel(Utils.resourceBundle.getString("total_score") + " : ");
		totalScoreLabel.setFont(Utils.fontMain);
		totalScoreLabel.setHorizontalAlignment(JLabel.RIGHT);
		totalScoreLabel.setVerticalAlignment(JLabel.CENTER);
		totalScore = new JLabel();
		totalScore.setFont(Utils.fontMain);
		totalScore.setHorizontalAlignment(JLabel.LEFT);
		totalScore.setVerticalAlignment(JLabel.CENTER);
		// PP
		JLabel ppCountLabel = new JLabel("PP : ");
		ppCountLabel.setFont(Utils.fontMain);
		ppCountLabel.setHorizontalAlignment(JLabel.RIGHT);
		ppCountLabel.setVerticalAlignment(JLabel.CENTER);
		ppCount = new JLabel();
		ppCount.setFont(Utils.fontMain);
		ppCount.setHorizontalAlignment(JLabel.LEFT);
		ppCount.setVerticalAlignment(JLabel.CENTER);
		// Accuracy
		JLabel accuracyLabel = new JLabel(Utils.resourceBundle.getString("accuracy") + " : ");
		accuracyLabel.setFont(Utils.fontMain);
		accuracyLabel.setHorizontalAlignment(JLabel.RIGHT);
		accuracyLabel.setVerticalAlignment(JLabel.CENTER);
		accuracy = new JLabel();
		accuracy.setFont(Utils.fontMain);
		accuracy.setHorizontalAlignment(JLabel.LEFT);
		accuracy.setVerticalAlignment(JLabel.CENTER);
		// Country
		picturesSize = 16;
		JLabel countryLabel = new JLabel(Utils.resourceBundle.getString("country") + " : ");
		countryLabel.setFont(Utils.fontMain);
		countryLabel.setHorizontalAlignment(JLabel.RIGHT);
		countryLabel.setVerticalAlignment(JLabel.CENTER);
		country = new JLabel();
		country.setFont(Utils.fontMain);
		country.setHorizontalAlignment(JLabel.LEFT);
		country.setVerticalAlignment(JLabel.CENTER);
		countryFlag = new ImagePanel();
		countryFlag.setPrintLoading(false);
		countryFlag.setBackground(Utils.noticeColor);
		countryFlag.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		countryFlag.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		countryFlag.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		// Total hits
		JLabel totalHitsLabel = new JLabel(Utils.resourceBundle.getString("total_hits") + " : ");
		totalHitsLabel.setFont(Utils.fontMain);
		totalHitsLabel.setHorizontalAlignment(JLabel.RIGHT);
		totalHitsLabel.setVerticalAlignment(JLabel.CENTER);
		totalHits = new JLabel();
		totalHits.setFont(Utils.fontMain);
		totalHits.setHorizontalAlignment(JLabel.LEFT);
		totalHits.setVerticalAlignment(JLabel.CENTER);
		// Construct
		lign = 0;
		otherPanel.add(playCountLabel, new CC().cell(0, lign).alignX("right"));
		otherPanel.add(playCount, new CC().cell(1, lign++, 2, 1).alignX("left").gapLeft("5"));
		otherPanel.add(rankedScoreLabel, new CC().cell(0, lign).alignX("right"));
		otherPanel.add(rankedScore, new CC().cell(1, lign++, 2, 1).alignX("left").gapLeft("5"));
		otherPanel.add(totalScoreLabel, new CC().cell(0, lign).alignX("right"));
		otherPanel.add(totalScore, new CC().cell(1, lign++, 2, 1).alignX("left").gapLeft("5"));
		otherPanel.add(ppCountLabel, new CC().cell(0, lign).alignX("right"));
		otherPanel.add(ppCount, new CC().cell(1, lign++, 2, 1).alignX("left").gapLeft("5"));
		otherPanel.add(accuracyLabel, new CC().cell(0, lign).alignX("right"));
		otherPanel.add(accuracy, new CC().cell(1, lign++, 2, 1).alignX("left").gapLeft("5"));
		otherPanel.add(countryLabel, new CC().cell(0, lign).alignX("right"));
		otherPanel.add(countryFlag, new CC().cell(1, lign).alignX("left").gapLeft("5"));
		otherPanel.add(country, new CC().cell(2, lign++, 2, 1).alignX("left").gapLeft("2"));
		otherPanel.add(totalHitsLabel, new CC().cell(0, lign).alignX("right"));
		otherPanel.add(totalHits, new CC().cell(1, lign++, 2, 1).alignX("left").gapLeft("5"));
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

	public void openUserProfile()
	{
		final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
			try
			{
				String user_id = username.getText();
				if(user_id.equalsIgnoreCase(""))
					return;
				desktop.browse(new URL("https://osu.ppy.sh/u/" + Utils.lastUser.getUserID()).toURI());
			}
			catch(final Exception e)
			{
				e.printStackTrace();
			}
	}

	public void trackNewUser(User user) throws IOException
	{
		Utils.logger.log(Level.INFO, "Trcking user " + user.getUsername());
		ArrayList<String> users = Utils.getTrackedUsers();
		users.add(user.getUsername());
		userNameFieldModel.addElement(user.getUsername());
		user.serialize(new File(Configuration.appData, user.getUsername()));
		lastStatsDate.setEnabled(track.isSelected());
		lastStatsDateBox.setEnabled(track.isSelected());
		autoUpdateCheck.setEnabled(track.isSelected());
		Utils.setTrackedUser(users);
	}

	public void unTrackUser(User user)
	{
		Utils.logger.log(Level.INFO, "Untrcking user " + user.getUsername());
		ArrayList<String> users = Utils.getTrackedUsers();
		users.remove(user.getUsername());
		userNameFieldModel.removeElement(user.getUsername());
		userNameField.setSelectedItem(null);
		new File(Configuration.appData, user.getUsername()).delete();
		lastStatsDate.setEnabled(track.isSelected());
		lastStatsDateBox.setEnabled(track.isSelected());
		autoUpdateCheck.setEnabled(track.isSelected());
		autoUpdateCheck.setSelected(false);
		Utils.setTrackedUser(users);
	}

	private Color getColorUser()
	{
		Color[] colors = new Color[] {Color.BLACK, Color.BLUE, Color.GRAY, Color.RED, Color.DARK_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK};
		return colors[new Random().nextInt(colors.length)];
	}

	public void getInfos(boolean showerror)
	{
		getInfos(userNameFieldTextComponent.getText(), showerror);
	}

	public void updateInfos(boolean showerror)
	{
		getInfos(Utils.lastUser.getUsername(), showerror);
	}

	private boolean getInfos(String user, boolean showerror)
	{
		LoadingWorker load = new LoadingWorker(frame, user, showerror, Utils.config.getBoolean("loadingScreen", true));
		load.execute();
		return true;
	}

	public boolean getInfosServer(String user, boolean showerror)
	{
		if(!isValidTime() || !isValidUser(user))
			return false;
		Utils.logger.log(Level.INFO, "Getting user infos " + user);
		Utils.lastPost = new Date();
		userNameField.setBackground(null);
		userNameFieldTextComponent.setBackground(null);
		try
		{
			User currentUser = new User();
			Stats currentStats = new Stats();
			currentStats.setDate(new Date().getTime());
			final JSONObject jsonResponse = new JSONObject(Utils.sendPost("get_user", Utils.API_KEY, user, getSelectedMode()));
			username.setBackground(Utils.noticeColor);
			username.setBorder(Utils.noticeBorder);
			boolean tracked = Utils.isUserTracked(jsonResponse.getString("username"));
			if(tracked)
				try
				{
					currentUser = User.deserialize(new File(Configuration.appData, jsonResponse.getString("username")));
				}
				catch(Exception e)
				{}
			Stats previousStats = currentUser.getLastStats(getSelectedMode());
			track.setEnabled(true);
			track.setSelected(tracked);
			autoUpdateCheck.setEnabled(tracked);
			currentUser.setUsername(jsonResponse.getString("username"));
			currentUser.setUserID(jsonResponse.getInt("user_id"));
			currentUser.setCountry(jsonResponse.getString("country"));
			currentStats.setRank(jsonResponse.getDouble("pp_rank"));
			currentStats.setPlaycount(jsonResponse.getInt("playcount"));
			currentStats.setRankedScore(jsonResponse.getLong("ranked_score"));
			currentStats.setTotalScore(jsonResponse.getLong("total_score"));
			currentStats.setAccuracy(jsonResponse.getDouble("accuracy"));
			currentStats.setPp(jsonResponse.getDouble("pp_raw"));
			currentStats.setLevel(jsonResponse.getDouble("level"));
			currentStats.setCountSS(jsonResponse.getInt("count_rank_ss"));
			currentStats.setCountS(jsonResponse.getInt("count_rank_s"));
			currentStats.setCountA(jsonResponse.getInt("count_rank_a"));
			currentStats.setCount300(jsonResponse.getLong("count300"));
			currentStats.setCount100(jsonResponse.getLong("count100"));
			currentStats.setCount50(jsonResponse.getLong("count50"));
			currentStats.updateTotalHits();
			if(currentStats.equals(Utils.lastStats))
				return false;
			if(!Utils.lastUser.getUsername().equals(jsonResponse.getString("username")))
			{
				avatar.setImage(null);
				countryFlag.setImage(null);
				autoUpdateCheck.setSelected(false);
				Runnable task = new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							avatar.setImage(Utils.resizeBufferedImage(getAvatar(jsonResponse.getString("user_id")), 128, 128));
							countryFlag.setImage(Utils.resizeBufferedImage(getCountryFlag(jsonResponse.getString("country")), 16, 16));
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				};
				new Thread(task, "ThreadImages").start();
			}
			username.setForeground(getColorUser());
			updateStatsDates(currentUser);
			displayStats(currentUser, currentStats);
			updateTrackedInfos(currentUser.getUsername(), currentStats, previousStats, true);
			setValidButonIcon("R");
			userNameFieldTextComponent.setText(currentUser.getUsername());
			currentUser.setStats(!showerror, currentStats, getSelectedMode());
			if(tracked)
			{
				currentUser.serialize(new File(Configuration.appData, currentUser.getUsername()));
				lastStatsDate.setEnabled(track.isSelected());
				lastStatsDateBox.setEnabled(track.isSelected());
			}
			Utils.lastStats = currentStats;
			Utils.lastUser = currentUser;
		}
		catch(Exception e)
		{
			if(showerror)
			{
				Utils.logger.log(Level.SEVERE, "Error reading infos!", e);
				userNameField.setBackground(Color.RED);
				userNameFieldTextComponent.setBackground(Color.RED);
			}
			return false;
		}
		return true;
	}

	public void displayStats(User user, Stats stats)
	{
		updateLevel(stats.getLevel());
		countSS.setText(String.valueOf(stats.getCountSS()));
		countS.setText(String.valueOf(stats.getCountS()));
		countA.setText(String.valueOf(stats.getCountA()));
		totalScore.setText(String.format(Utils.resourceBundle.getString("total_score_value"), NumberFormat.getInstance(Locale.getDefault()).format(stats.getTotalScore()), NumberFormat.getInstance(Locale.getDefault()).format(Utils.getScoreToNextLevel(Utils.getLevel(stats.getLevel()), stats.getTotalScore())), Utils.getLevel(stats.getLevel()) + 1));
		country.setText(CountryCode.getByCode(user.getCountry()).getName());
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setMaximumFractionDigits(2);
		hitCount300.setText(NumberFormat.getInstance(Locale.getDefault()).format(stats.getCount300()) + " (" + decimalFormat.format((stats.getCount300() * 100f) / stats.getTotalHits()) + "%)");
		hitCount100.setText(NumberFormat.getInstance(Locale.getDefault()).format(stats.getCount100()) + " (" + decimalFormat.format((stats.getCount100() * 100f) / stats.getTotalHits()) + "%)");
		hitCount50.setText(NumberFormat.getInstance(Locale.getDefault()).format(stats.getCount50()) + " (" + decimalFormat.format((stats.getCount50() * 100f) / stats.getTotalHits()) + "%)");
	}

	public void updateTrackedInfos()
	{
		updateTrackedInfos(Utils.lastUser.getUsername(), Utils.lastUser.getLastStats(Utils.mainFrame.getSelectedMode()), Utils.lastUser.getStatsByModeAndDate(Utils.mainFrame.getSelectedMode(), Utils.mainFrame.getSelectedDate()), true);
	}

	public void updateTrackedInfos(String user, Stats currentStats, Stats previousStats, boolean showNotification)
	{
		Utils.logger.log(Level.INFO, "Updating tracked infos...");
		username.setText("<html><div>  " + user + " (#" + NumberFormat.getInstance(Locale.getDefault()).format(currentStats.getRank()) + ")" + currentStats.compareRank(previousStats) + "  </div></html>");
		accuracy.setText(String.valueOf(Utils.round(currentStats.getAccuracy(), 2)) + "%" + currentStats.compareAccuracy(previousStats));
		playCount.setText(NumberFormat.getInstance(Locale.getDefault()).format(currentStats.getPlaycount()) + currentStats.comparePlayCount(previousStats));
		rankedScore.setText(NumberFormat.getInstance(Locale.getDefault()).format(currentStats.getRankedScore()) + currentStats.compareRankedScore(previousStats));
		totalHits.setText(NumberFormat.getInstance(Locale.getDefault()).format(currentStats.getTotalHits()) + currentStats.compareTotalHits(previousStats));
		ppCount.setText(NumberFormat.getInstance(Locale.getDefault()).format(currentStats.getPp()) + currentStats.comparePP(previousStats));
		if(Utils.config.getBoolean("showNotification", false) && showNotification && !(currentStats.getDiffRank(previousStats) == 0))
		{
			new InterfaceNotification(String.format(Utils.resourceBundle.getString("notification_text"), currentStats.getDiffRank(previousStats) > 0 ? Utils.resourceBundle.getString("won") : Utils.resourceBundle.getString("lost"), currentStats.getDiffRank(previousStats), currentStats.getDiffPlayCount(previousStats), currentStats.getDiffTotalHits(previousStats)));
		}
	}

	private void updateLevel(double level)
	{
		Utils.logger.log(Level.INFO, "Setting level to " + level);
		double progress = Utils.round(Utils.getProgressLevel(level) * 100, 2);
		levelBar.setValue((int) progress);
		levelBar.setString(String.format(Utils.resourceBundle.getString("level"), Utils.getLevel(level), progress));
	}

	private void updateStatsDates(User user)
	{
		String lastDate = (String) statsDateModel.getSelectedItem();
		statsDateModel.removeAllElements();
		for(String date : user.getAvalidbleStatsDates(getSelectedMode()))
			statsDateModel.addElement(date);
		if(Utils.config.getBoolean("keepDate", false) && lastDate != null && !lastDate.equalsIgnoreCase("") && !lastDate.equalsIgnoreCase("null"))
			lastStatsDateBox.setSelectedItem(lastDate);
		else
			lastStatsDateBox.setSelectedIndex(statsDateModel.getSize() - 1);
	}

	private boolean isValidUser(String username)
	{
		return username.length() > 1;
	}

	private boolean isValidTime()
	{
		return new Date().getTime() - Utils.lastPost.getTime() > 1000;
	}

	private synchronized BufferedImage getAvatar(String userID) throws Exception
	{
		try
		{
			return ImageIO.read(new URL("https:" + Utils.cutLine(Utils.getLineCodeFromLink("https://osu.ppy.sh/u/" + userID, "<div class=\"avatar-holder\">"), true, "\" alt=\"User avatar\"", "<div class=\"avatar-holder\"><img src=\"")));
		}
		catch(Exception e)
		{
			Utils.logger.log(Level.WARNING, "Error getting avatar for " + userID, e);
		}
		return avatarDefaultImage;
	}

	private synchronized BufferedImage getCountryFlag(String country) throws Exception
	{
		try
		{
			return ImageIO.read(new URL("http://s.ppy.sh/images/flags/" + country.toLowerCase() + ".gif"));
		}
		catch(Exception e)
		{}
		return avatarDefaultImage;
	}

	public void hideFrame()
	{
		getFrame().setEnabled(false);
		getFrame().setFocusable(false);
	}

	public void showFrame()
	{
		getFrame().setEnabled(true);
		getFrame().setFocusable(true);
		getFrame().setVisible(true);
	}

	public void backFromTray()
	{
		getFrame().setState(JFrame.NORMAL);
		showFrame();
		getFrame().toFront();
	}

	public JFrame getFrame()
	{
		return frame;
	}

	public void setFrame(JFrame frame)
	{
		this.frame = frame;
	}

	public void updateAutoCompletionStatus(boolean status)
	{
		userNameField.setAutoCompletion(status);
	}

	public void switchMode(int mode, boolean checkInfos)
	{
		buttonStandard.setEnabled(true);
		buttonTaiko.setEnabled(true);
		buttonCTB.setEnabled(true);
		buttonMania.setEnabled(true);
		switch(mode)
		{
			case 0:
				buttonStandard.setEnabled(false);
			break;
			case 1:
				buttonTaiko.setEnabled(false);
			break;
			case 2:
				buttonCTB.setEnabled(false);
			break;
			case 3:
				buttonMania.setEnabled(false);
			break;
		}
		if(checkInfos)
			getInfos(Utils.lastUser.getUsername(), false);
	}

	public int getSelectedMode()
	{
		if(!buttonTaiko.isEnabled())
			return 1;
		if(!buttonCTB.isEnabled())
			return 2;
		if(!buttonMania.isEnabled())
			return 3;
		return 0;
	}

	public void activateFrame()
	{
		frame.setFocusable(true);
		frame.setEnabled(true);
	}

	public void desactivateFrame()
	{
		frame.setFocusable(false);
		frame.setEnabled(false);
	}

	public void setValidButonIcon(String string)
	{
		if(string.equalsIgnoreCase("R"))
			this.validButon.setIcon(iconRefresh);
		else
			this.validButon.setIcon(iconSearch);
	}

	public long getSelectedDate()
	{
		DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM);
		SimpleDateFormat simpleFormat = (SimpleDateFormat) format;
		DateTimeFormatter formatter = DateTimeFormat.forPattern(simpleFormat.toPattern());
		return formatter.parseDateTime(lastStatsDateBox.getSelectedItem().toString()).toDate().getTime();
	}
}
