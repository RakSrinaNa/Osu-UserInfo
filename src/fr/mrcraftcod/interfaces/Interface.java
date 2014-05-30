package fr.mrcraftcod.interfaces;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;
import fr.mrcraftcod.Main;
import fr.mrcraftcod.objects.AutoComboBox;
import fr.mrcraftcod.objects.ComboModeRenderer;
import fr.mrcraftcod.objects.GhostText;
import fr.mrcraftcod.objects.ImagePanel;
import fr.mrcraftcod.objects.Stats;
import fr.mrcraftcod.objects.SystemTrayOsuStats;
import fr.mrcraftcod.objects.User;
import fr.mrcraftcod.utils.Configuration;
import fr.mrcraftcod.utils.CountryCode;

public class Interface extends JFrame // TODO Javadoc
{
	private static final long serialVersionUID = 2629819156905465351L;
	private static JFrame frame;
	private JTextComponent userNameFieldTextComponent;
	private DefaultComboBoxModel<String> userNameFieldModel;
	private ImageIcon iconRefresh, iconSearch;
	private BufferedImage avatarDefaultImage;
	private static AutoComboBox userNameField;
	private ImagePanel avatar, countryFlag;
	private JComboBox<String> mode;
	private JButton validButon;
	private User lastUser = new User();
	private JComboBox<String> lastStatsDateBox;
	private JLabel totalHits, username, countSS, countS, countA, playCount, rankedScore, totalScore, ppCount, accuracy, country, hitCount300, hitCount100, hitCount50;
	private JProgressBar levelBar;
	private JCheckBox track;
	private Date lastPost = new Date(0);
	private JLabel lastStatsDate;
	private DefaultComboBoxModel<String> statsDateModel;

	public enum Mods
	{
		None(0), NoFail(1), Easy(2), NoVideo(4), Hidden(8), HardRock(16), SuddenDeath(32), DoubleTime(64), Relax(128), HalfTime(256), Nightcore(512), Flashlight(1024), Autoplay(2048), SpunOut(4096), Relax2(8192), Perfect(16384), Key4(32768), Key5(5536), Key6(131072), Key7(262144), Key8(524288), keyMod(Key4.getKey() | Key5.getKey() | Key6.getKey() | Key7.getKey() | Key8.getKey()), FadeIn(1048576), Random(2097152), LastMod(4194304), FreeModAllowed(NoFail.getKey() | Easy.getKey() | Hidden.getKey() | HardRock.getKey() | SuddenDeath.getKey() | Flashlight.getKey() | FadeIn.getKey() | Relax.getKey() | Relax2.getKey() | SpunOut.getKey() | keyMod.getKey());
		private long key;

		Mods(long key)
		{
			this.key = key;
		}

		private long getKey()
		{
			return this.key;
		}
	}

	@SuppressWarnings("unchecked")
	public Interface() throws IOException
	{
		int pictureButtonSize = 24;
		Main.logger.log(Level.FINE, "Loading icons...");
		try
		{
			iconRefresh = new ImageIcon(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/refresh.png")), pictureButtonSize, pictureButtonSize));
		}
		catch(Exception e)
		{
			Main.logger.log(Level.SEVERE, "", e);
		}
		try
		{
			iconSearch = new ImageIcon(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/search.png")), pictureButtonSize, pictureButtonSize));
		}
		catch(Exception e)
		{
			Main.logger.log(Level.SEVERE, "", e);
		}
		try
		{
			avatarDefaultImage = ImageIO.read(Main.class.getClassLoader().getResource("resources/images/avatar.png"));
		}
		catch(Exception e)
		{
			Main.logger.log(Level.SEVERE, "", e);
		}
		/************** FRAME INFOS ********************/
		Main.logger.log(Level.FINE, "Setting frame options...");
		setFrame(new JFrame(Main.APPNAME + " v" + Main.VERSION));
		getFrame().setFocusable(true);
		getFrame().setVisible(false);
		getFrame().addWindowListener(new WindowListener()
		{
			@Override
			public void windowActivated(final WindowEvent event)
			{}

			@Override
			public void windowClosed(final WindowEvent event)
			{}

			@Override
			public void windowClosing(final WindowEvent event)
			{
				exit();
			}

			@Override
			public void windowDeactivated(final WindowEvent event)
			{}

			@Override
			public void windowDeiconified(final WindowEvent event)
			{}

			@Override
			public void windowIconified(final WindowEvent event)
			{
				try
				{
					SystemTrayOsuStats.add();
					hideFrame();
					getFrame().setVisible(false);
				}
				catch(final AWTException exception)
				{}
			}

			@Override
			public void windowOpened(final WindowEvent event)
			{}
		});
		getFrame().setLayout(new GridBagLayout());
		getFrame().setMinimumSize(new Dimension(550, 700));
		getFrame().setPreferredSize(new Dimension(550, 700));
		getFrame().setAlwaysOnTop(false);
		getFrame().setIconImages(Main.icons);
		getFrame().getContentPane().setBackground(Main.backColor);
		getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		/*************** FRMAE BAR ************************/
		Main.logger.log(Level.FINE, "Creating frame bar...");
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu(Main.resourceBundle.getString("menu_bar_file"));
		JMenu menuHelp = new JMenu(Main.resourceBundle.getString("menu_bar_help"));
		JMenuItem itemSettings = new JMenuItem(Main.resourceBundle.getString("settings"));
		itemSettings.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				new InterfaceSettings();
			}
		});
		JMenuItem itemAbout = new JMenuItem(Main.resourceBundle.getString("menu_bar_help_about"));
		itemAbout.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				new InterfaceAbout(getFrame());
			}
		});
		menuFile.add(itemSettings);
		menuHelp.add(itemAbout);
		menuBar.add(menuFile);
		menuBar.add(menuHelp);
		getFrame().setJMenuBar(menuBar);
		/*************** SEARCH PANEL **********************/
		Main.logger.log(Level.FINE, "Creating search panel...");
		JPanel searchPanel = new JPanel(new GridBagLayout());
		searchPanel.setBackground(Main.searchBarColor);
		// searchPanel.setBackground(Main.backColor);
		JLabel usernameAsk = new JLabel(Main.resourceBundle.getString("username") + " : ");
		usernameAsk.setHorizontalAlignment(JLabel.CENTER);
		usernameAsk.setVerticalAlignment(JLabel.CENTER);
		userNameField = new AutoComboBox(getTrackedUsers(), Main.config.getBoolean("autoCompletion", false));
		userNameFieldModel = userNameField.getDefModel();
		userNameField.setEditable(true);
		userNameField.setPreferredSize(new Dimension(200, 30));
		userNameField.setSelectedItem(null);
		userNameField.setToolTipText(Main.resourceBundle.getString("ghost_username_field"));
		userNameFieldTextComponent = (JTextComponent) userNameField.getEditor().getEditorComponent();
		userNameFieldTextComponent.getDocument().addDocumentListener(new DocumentListener()
		{
			public void changedUpdate(DocumentEvent e)
			{
				update();
			}

			public void removeUpdate(DocumentEvent e)
			{
				update();
			}

			public void insertUpdate(DocumentEvent e)
			{
				update();
			}

			public void update()
			{
				try
				{
					if(userNameFieldTextComponent.getText().equalsIgnoreCase(lastUser.getUsername()) && !userNameFieldTextComponent.getText().equalsIgnoreCase(""))
						validButon.setIcon(iconRefresh);
					else
						validButon.setIcon(iconSearch);
				}
				catch(Exception e)
				{}
			}
		});
		new GhostText(((JTextField) userNameField.getEditor().getEditorComponent()), Main.resourceBundle.getString("ghost_username_field"));
		((JTextField) userNameField.getEditor().getEditorComponent()).addKeyListener(new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent arg0)
			{
				if(KeyEvent.VK_ENTER == arg0.getExtendedKeyCode())
					getInfos(userNameFieldTextComponent.getText());
			}

			@Override
			public void keyReleased(KeyEvent arg0)
			{}

			@Override
			public void keyTyped(KeyEvent arg0)
			{}
		});
		mode = new JComboBox<String>(new String[] {"osu!", "Taiko", "CTB", "Osu!Mania"});
		mode.setSelectedIndex(0);
		mode.setBackground(Main.searchBarColor);
		ImageIcon iconStandard = null, iconTaiko = null, iconCTB = null, iconMania = null;
		pictureButtonSize = 24;
		try
		{
			iconStandard = new ImageIcon(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/standard.png")), pictureButtonSize, pictureButtonSize));
		}
		catch(Exception e)
		{
			Main.logger.log(Level.WARNING, "Failed to load mode image", e);
		}
		try
		{
			iconTaiko = new ImageIcon(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/taiko.png")), pictureButtonSize, pictureButtonSize));
		}
		catch(Exception e)
		{
			Main.logger.log(Level.WARNING, "Failed to load mode image", e);
		}
		try
		{
			iconCTB = new ImageIcon(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/ctb.png")), pictureButtonSize, pictureButtonSize));
		}
		catch(Exception e)
		{
			Main.logger.log(Level.WARNING, "Failed to load mode image", e);
		}
		try
		{
			iconMania = new ImageIcon(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/mania.png")), pictureButtonSize, pictureButtonSize));
		}
		catch(Exception e)
		{
			Main.logger.log(Level.WARNING, "Failed to load mode image", e);
		}
		mode.setRenderer(new ComboModeRenderer(new ImageIcon[] {iconStandard, iconTaiko, iconCTB, iconMania}));
		validButon = new JButton(iconSearch);
		validButon.setToolTipText(Main.resourceBundle.getString("button_search_tooltip_text"));
		validButon.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				getInfos(userNameFieldTextComponent.getText());
			}
		});
		// Construct panel
		GridBagConstraints constraint = new GridBagConstraints();
		constraint.anchor = GridBagConstraints.CENTER;
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
		constraint.weightx = 0.2;
		searchPanel.add(mode, constraint);
		constraint.gridx = 3;
		constraint.weightx = 0.1;
		searchPanel.add(validButon, constraint);
		/***************** LEVEL PANEL ********************/
		Main.logger.log(Level.FINE, "Creating level panel...");
		JPanel levelUserPanel = new JPanel(new GridBagLayout());
		levelUserPanel.setBackground(Main.backColor);
		levelBar = new JProgressBar();
		Dimension dim = levelBar.getPreferredSize();
		dim.width += 50;
		levelBar.setPreferredSize(dim);
		levelBar.setMaximum(100);
		levelBar.setStringPainted(true);
		updateLevel(0D);
		// Construct
		constraint = new GridBagConstraints();
		constraint.anchor = GridBagConstraints.CENTER;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.gridwidth = 3;
		constraint.weightx = 1;
		constraint.weighty = 1;
		constraint.gridx = 0;
		constraint.gridy = 0;
		constraint.insets = new Insets(0, 2, 0, 2);
		levelUserPanel.add(levelBar, constraint);
		/***************** TRACK PANEL ********************/
		Main.logger.log(Level.FINE, "Creating track panel...");
		JPanel trackUserPanel = new JPanel(new MigLayout());
		trackUserPanel.setBackground(Main.backColor);
		track = new JCheckBox();
		track.setText(Main.resourceBundle.getString("track_user"));
		track.setEnabled(false);
		track.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(track.isSelected())
					try
					{
						trackNewUser(lastUser);
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				else
					unTrackUser(lastUser);
			}
		});
		lastStatsDate = new JLabel(Main.resourceBundle.getString("last_stats_date"));
		lastStatsDate.setEnabled(track.isSelected());
		statsDateModel = new DefaultComboBoxModel<String>(new String[] {});
		lastStatsDateBox = new JComboBox<String>(statsDateModel);
		lastStatsDateBox.setEnabled(track.isEnabled());
		lastStatsDateBox.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				if(e.getStateChange() != 1)
					return;
				DateFormat format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.MEDIUM);
				SimpleDateFormat simpleFormat = (SimpleDateFormat) format;
				DateTimeFormatter formatter = DateTimeFormat.forPattern(simpleFormat.toPattern());
				updateInfos(lastUser.getUsername(), lastUser.getStats(mode.getSelectedIndex()), lastUser.getStatsByModeAndDate(mode.getSelectedIndex(), formatter.parseDateTime(lastStatsDateBox.getSelectedItem().toString()).toDate().getTime()));
			}
		});
		// Construct
		trackUserPanel.add(track, new CC().cell(0, 0).alignX("left"));
		trackUserPanel.add(lastStatsDate, new CC().cell(0, 1).alignX("right"));
		trackUserPanel.add(lastStatsDateBox, new CC().cell(1, 1).alignX("right"));
		/***************** HITS PANEL ********************/
		Main.logger.log(Level.FINE, "Creating hits panel...");
		JPanel hitCountPanel = new JPanel(new GridBagLayout());
		hitCountPanel.setBackground(Main.noticeColor);
		TitledBorder borderHits = BorderFactory.createTitledBorder(Main.noticeBorder, Main.resourceBundle.getString("hits"));
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
		count300Picture.setImage(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/hit300.png")), picturesSize, picturesSize));
		hitCount300 = new JLabel();
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
		count100Picture.setImage(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/hit100.png")), picturesSize, picturesSize));
		hitCount100 = new JLabel();
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
		count50Picture.setImage(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/hit50.png")), picturesSize, picturesSize));
		hitCount50 = new JLabel();
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
		Main.logger.log(Level.FINE, "Creating rank panel...");
		JPanel ranksUserPanel = new JPanel(new GridBagLayout());
		ranksUserPanel.setBackground(Main.noticeColor);
		TitledBorder borderRanks = BorderFactory.createTitledBorder(Main.noticeBorder, Main.resourceBundle.getString("ranks"));
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
		ssPicture.setImage(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/SS.png")), picturesSize, picturesSize));
		countSS = new JLabel();
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
		sPicture.setImage(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/S.png")), picturesSize, picturesSize));
		countS = new JLabel();
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
		aPicture.setImage(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/A.png")), picturesSize, picturesSize));
		countA = new JLabel();
		countA.setHorizontalAlignment(JLabel.CENTER);
		countA.setVerticalAlignment(JLabel.CENTER);
		aPanel.add(aPicture);
		aPanel.add(countA);
		// Construct
		constraint = new GridBagConstraints();
		constraint.anchor = GridBagConstraints.CENTER;
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
		Main.logger.log(Level.FINE, "Creating user panel...");
		JPanel avatarPanel = new JPanel(new GridBagLayout());
		avatarPanel.setBackground(Main.backColor);
		int avatarSize = 128;
		avatar = new ImagePanel(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/osu_logo.png")), 128, 128));
		avatar.setBackground(Main.backColor);
		avatar.setMinimumSize(new Dimension(avatarSize, avatarSize));
		avatar.setPreferredSize(new Dimension(avatarSize, avatarSize));
		avatar.setMaximumSize(new Dimension(avatarSize, avatarSize));
		avatar.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{}

			@Override
			public void mouseExited(MouseEvent arg0)
			{}

			@Override
			public void mousePressed(MouseEvent arg0)
			{
				System.out.println(arg0.getClickCount());
				if(arg0.getClickCount() > 1)
				{
					final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
					if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
						try
						{
							String user_id = username.getText();
							if(user_id.equalsIgnoreCase(""))
								return;
							desktop.browse(new URL("https://osu.ppy.sh/u/" + lastUser.getUserID()).toURI());
						}
						catch(final Exception e)
						{
							e.printStackTrace();
						}
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0)
			{}
		});
		username = new JLabel(" ");
		username.setOpaque(true);
		username.setBackground(Main.backColor);
		username.setFont(new Font(username.getFont().getName(), Font.PLAIN, 25));
		// Construct
		constraint = new GridBagConstraints();
		constraint.fill = GridBagConstraints.PAGE_START;
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
		Main.logger.log(Level.FINE, "Creating other panel...");
		JPanel otherPanel = new JPanel(new MigLayout());
		otherPanel.setBackground(Main.noticeColor);
		otherPanel.setBackground(Main.noticeColor);
		TitledBorder borderOther = BorderFactory.createTitledBorder(Main.noticeBorder, Main.resourceBundle.getString("stats"));
		borderOther.setTitleJustification(TitledBorder.CENTER);
		borderOther.setTitlePosition(TitledBorder.CENTER);
		otherPanel.setBorder(borderOther);
		// PlayCount
		JLabel playCountLabel = new JLabel(Main.resourceBundle.getString("play_count") + " : ");
		playCountLabel.setHorizontalAlignment(JLabel.RIGHT);
		playCountLabel.setVerticalAlignment(JLabel.CENTER);
		playCount = new JLabel();
		playCount.setHorizontalAlignment(JLabel.LEFT);
		playCount.setVerticalAlignment(JLabel.CENTER);
		// RankedScore
		JLabel rankedScoreLabel = new JLabel(Main.resourceBundle.getString("ranked_score") + " : ");
		rankedScoreLabel.setHorizontalAlignment(JLabel.RIGHT);
		rankedScoreLabel.setVerticalAlignment(JLabel.CENTER);
		rankedScore = new JLabel();
		rankedScore.setHorizontalAlignment(JLabel.LEFT);
		rankedScore.setVerticalAlignment(JLabel.CENTER);
		// TotalScore
		JLabel totalScoreLabel = new JLabel(Main.resourceBundle.getString("total_score") + " : ");
		totalScoreLabel.setHorizontalAlignment(JLabel.RIGHT);
		totalScoreLabel.setVerticalAlignment(JLabel.CENTER);
		totalScore = new JLabel();
		totalScore.setHorizontalAlignment(JLabel.LEFT);
		totalScore.setVerticalAlignment(JLabel.CENTER);
		// PP
		JLabel ppCountLabel = new JLabel("PP : ");
		ppCountLabel.setHorizontalAlignment(JLabel.RIGHT);
		ppCountLabel.setVerticalAlignment(JLabel.CENTER);
		ppCount = new JLabel();
		ppCount.setHorizontalAlignment(JLabel.LEFT);
		ppCount.setVerticalAlignment(JLabel.CENTER);
		// Accuracy
		JLabel accuracyLabel = new JLabel(Main.resourceBundle.getString("accuracy") + " : ");
		accuracyLabel.setHorizontalAlignment(JLabel.RIGHT);
		accuracyLabel.setVerticalAlignment(JLabel.CENTER);
		accuracy = new JLabel();
		accuracy.setHorizontalAlignment(JLabel.LEFT);
		accuracy.setVerticalAlignment(JLabel.CENTER);
		// Country
		picturesSize = 16;
		JLabel countryLabel = new JLabel(Main.resourceBundle.getString("country") + " : ");
		countryLabel.setHorizontalAlignment(JLabel.RIGHT);
		countryLabel.setVerticalAlignment(JLabel.CENTER);
		country = new JLabel();
		country.setHorizontalAlignment(JLabel.LEFT);
		country.setVerticalAlignment(JLabel.CENTER);
		countryFlag = new ImagePanel();
		countryFlag.setPrintLoading(false);
		countryFlag.setBackground(Main.noticeColor);
		countryFlag.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		countryFlag.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		countryFlag.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		country = new JLabel();
		country.setHorizontalAlignment(JLabel.LEFT);
		country.setVerticalAlignment(JLabel.CENTER);
		// Total hits
		JLabel totalHitsLabel = new JLabel(Main.resourceBundle.getString("total_hits") + " : ");
		totalHitsLabel.setHorizontalAlignment(JLabel.RIGHT);
		totalHitsLabel.setVerticalAlignment(JLabel.CENTER);
		totalHits = new JLabel();
		totalHits.setHorizontalAlignment(JLabel.LEFT);
		totalHits.setVerticalAlignment(JLabel.CENTER);
		// Construct
		int lign = 0;
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
		Main.logger.log(Level.FINE, "Creating frame panel...");
		constraint = new GridBagConstraints();
		constraint.anchor = GridBagConstraints.PAGE_START;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		int line = 0;
		constraint.gridwidth = GridBagConstraints.REMAINDER;
		constraint.weightx = 1;
		constraint.weighty = 1;
		constraint.gridx = 0;
		constraint.gridy = line++;
		getFrame().add(searchPanel, constraint);
		constraint.insets = new Insets(10, 0, 0, 0);
		constraint.gridy = line++;
		getFrame().add(avatarPanel, constraint);
		constraint.insets = new Insets(0, 0, 0, 0);
		constraint.gridy = line++;
		getFrame().add(levelUserPanel, constraint);
		constraint.gridy = line++;
		constraint.insets = new Insets(2, 4, 2, 4);
		getFrame().add(otherPanel, constraint);
		constraint.gridy = line++;
		getFrame().add(hitCountPanel, constraint);
		constraint.gridy = line++;
		getFrame().add(ranksUserPanel, constraint);
		constraint.gridy = line++;
		constraint.insets = new Insets(0, 0, 0, 0);
		getFrame().add(trackUserPanel, constraint);
		Main.logger.log(Level.FINE, "Packing frame...");
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		getFrame().setLocation(new Point((dimension.width - 700) / 2, (dimension.height - 130) / 2));
		getFrame().pack();
		getFrame().setVisible(true);
		getFrame().toFront();
	}

	private void trackNewUser(User user) throws IOException
	{
		Main.logger.log(Level.FINE, "Trcking user " + user.getUsername());
		ArrayList<String> users = getTrackedUsers();
		users.add(user.getUsername());
		userNameFieldModel.addElement(user.getUsername());
		user.serialize(new File(Configuration.appData, user.getUsername()));
		lastStatsDate.setEnabled(track.isSelected());
		lastStatsDateBox.setEnabled(track.isSelected());
		setTrackedUser(users);
	}

	private void unTrackUser(User user)
	{
		Main.logger.log(Level.FINE, "Untrcking user " + user.getUsername());
		ArrayList<String> users = getTrackedUsers();
		users.remove(user.getUsername());
		userNameFieldModel.removeElement(user.getUsername());
		userNameField.setSelectedItem(null);
		new File(Configuration.appData, user.getUsername()).delete();
		lastStatsDate.setEnabled(track.isSelected());
		lastStatsDateBox.setEnabled(track.isSelected());
		setTrackedUser(users);
	}

	private void setTrackedUser(ArrayList<String> users)
	{
		StringBuilder sb = new StringBuilder();
		for(String user : users)
			if(!user.equals(""))
				sb.append(user).append(",");
		sb.deleteCharAt(sb.length() - 1);
		Main.config.writeVar("tracked_users", sb.toString());
	}

	private boolean isUserTracked(String user)
	{
		return getTrackedUsers().contains(user);
	}

	private ArrayList<String> getTrackedUsers()
	{
		ArrayList<String> trackedList = new ArrayList<String>();
		String tracked = Main.config.getString("tracked_users", "");
		for(String user : tracked.split(","))
			trackedList.add(user);
		return trackedList;
	}

	private BufferedImage resizeBufferedImage(BufferedImage image, float width, float height)
	{
		if(image == null)
			return image;
		int baseWidth = image.getWidth(), baseHeight = image.getHeight();
		float ratio = (baseWidth > baseHeight) ? (width / baseWidth) : (height / baseHeight);
		Image tmp = image.getScaledInstance((int) (ratio * baseWidth), (int) (ratio * baseHeight), BufferedImage.SCALE_SMOOTH);
		BufferedImage buffered = new BufferedImage((int) (ratio * baseWidth), (int) (ratio * baseHeight), BufferedImage.TYPE_INT_ARGB);
		buffered.getGraphics().drawImage(tmp, 0, 0, null);
		return buffered;
	}

	private static String[] getCode(String link) throws IOException
	{
		final URL url = new URL(link);
		StringBuilder page = new StringBuilder();
		String str = null;
		final URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0");
		connection.setRequestProperty("Accept-Language", "fr-FR");
		connection.setConnectTimeout(30000);
		connection.setReadTimeout(30000);
		final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
		while((str = in.readLine()) != null)
			page.append(str + "\n");
		in.close();
		return page.toString().split("\n");
	}

	public static String getLineCodeFromLink(final String link, final String... gets) throws Exception
	{
		final String[] lines = getCode(link);
		for(final String get : gets)
			for(final String tempLine : lines)
				if(tempLine.contains(get))
					return tempLine;
		throw new Exception("Cannot get code from link");
	}

	private Color getColorUser()
	{
		Color[] colors = new Color[] {Color.BLACK, Color.BLUE, Color.GRAY, Color.RED, Color.DARK_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK};
		return colors[new Random().nextInt(colors.length)];
	}

	private void getInfos(String user)
	{
		if(new Date().getTime() - lastPost.getTime() < 1000)
			return;
		Main.logger.log(Level.FINE, "TGetting user infos " + user);
		lastPost = new Date();
		userNameField.setBackground(null);
		userNameFieldTextComponent.setBackground(null);
		try
		{
			User currentUser = new User();
			Stats statsUser = new Stats();
			statsUser.setDate(new Date().getTime());
			username.setBackground(Main.noticeColor);
			username.setBorder(Main.noticeBorder);
			final JSONObject jsonResponse = new JSONObject(sendPost("get_user", Main.API_KEY, user, mode.getSelectedIndex()));
			boolean tracked = isUserTracked(jsonResponse.getString("username"));
			if(tracked)
				try
				{
					currentUser = User.deserialize(new File(Configuration.appData, jsonResponse.getString("username")));
				}
				catch(Exception e)
				{}
			Stats previousStats = currentUser.getStats(mode.getSelectedIndex());
			track.setEnabled(true);
			track.setSelected(tracked);
			if(!lastUser.getUsername().equals(jsonResponse.getString("username")))
			{
				avatar.setImage(null);
				countryFlag.setImage(null);
			}
			currentUser.setUsername(jsonResponse.getString("username"));
			currentUser.setUserID(jsonResponse.getInt("user_id"));
			statsUser.setRank(jsonResponse.getDouble("pp_rank"));
			statsUser.setPlaycount(jsonResponse.getInt("playcount"));
			statsUser.setRankedScore(jsonResponse.getLong("ranked_score"));
			statsUser.setTotalScore(jsonResponse.getLong("total_score"));
			statsUser.setAccuracy(jsonResponse.getDouble("accuracy"));
			statsUser.setPp(jsonResponse.getDouble("pp_raw"));
			statsUser.setTotalHits(jsonResponse.getLong("count300") + jsonResponse.getLong("count100") + jsonResponse.getLong("count50"));
			username.setForeground(getColorUser());
			updateLevel(jsonResponse.getDouble("level"));
			countSS.setText(String.valueOf(jsonResponse.getInt("count_rank_ss")));
			countS.setText(String.valueOf(jsonResponse.getInt("count_rank_s")));
			countA.setText(String.valueOf(jsonResponse.getInt("count_rank_a")));
			totalScore.setText(String.format(Main.resourceBundle.getString("total_score_value"), NumberFormat.getInstance(Locale.getDefault()).format(statsUser.getTotalScore()), NumberFormat.getInstance(Locale.getDefault()).format(getScoreToNextLevel(getLevel(jsonResponse.getDouble("level")), statsUser.getTotalScore())), getLevel(jsonResponse.getDouble("level")) + 1));
			country.setText(CountryCode.getByCode(jsonResponse.getString("country")).getName());
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setMaximumFractionDigits(2);
			hitCount300.setText(NumberFormat.getInstance(Locale.getDefault()).format(jsonResponse.getLong("count300")) + " (" + decimalFormat.format((jsonResponse.getLong("count300") * 100f) / statsUser.getTotalHits()) + "%)");
			hitCount100.setText(NumberFormat.getInstance(Locale.getDefault()).format(jsonResponse.getLong("count100")) + " (" + decimalFormat.format((jsonResponse.getLong("count100") * 100f) / statsUser.getTotalHits()) + "%)");
			hitCount50.setText(NumberFormat.getInstance(Locale.getDefault()).format(jsonResponse.getLong("count50")) + " (" + decimalFormat.format((jsonResponse.getLong("count50") * 100f) / statsUser.getTotalHits()) + "%)");
			updateInfos(currentUser.getUsername(), statsUser, previousStats);
			if(!lastUser.getUsername().equals(jsonResponse.get("username")))
			{
				SwingUtilities.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							avatar.setImage(resizeBufferedImage(getAvatar(jsonResponse.getString("user_id")), 128, 128));
							countryFlag.setImage(resizeBufferedImage(getFlag(jsonResponse.getString("country")), 16, 16));
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				});
			}
			userNameFieldTextComponent.setText(currentUser.getUsername());
			validButon.setIcon(iconRefresh);
			currentUser.setStats(statsUser, mode.getSelectedIndex());
			if(tracked)
			{
				currentUser.serialize(new File(Configuration.appData, currentUser.getUsername()));
				lastStatsDate.setEnabled(track.isSelected());
				lastStatsDateBox.setEnabled(track.isSelected());
			}
			lastUser = currentUser;
			statsDateModel.removeAllElements();
			for(String date : currentUser.getAvalidbleStatsDates(mode.getSelectedIndex()))
				statsDateModel.addElement(date);
			lastStatsDateBox.setSelectedIndex(statsDateModel.getSize() - 1);
		}
		catch(JSONException | IOException e)
		{
			Main.logger.log(Level.SEVERE, "Error reading infos!", e);
			userNameField.setBackground(Color.RED);
			userNameFieldTextComponent.setBackground(Color.RED);
		}
		catch(Exception e)
		{
			Main.logger.log(Level.SEVERE, "Error reading infos!", e);
		}
	}

	private void updateInfos(String user, Stats currentStats, Stats previousStats)
	{
		Main.logger.log(Level.INFO, "Updating tracked infos...");
		username.setText("  " + user + " (#" + NumberFormat.getInstance(Locale.getDefault()).format(currentStats.getRank()) + ")" + currentStats.compareRank(previousStats) + "  ");
		accuracy.setText(String.valueOf(round(currentStats.getAccuracy(), 2)) + "%" + currentStats.compareAccuracy(previousStats));
		playCount.setText(NumberFormat.getInstance(Locale.getDefault()).format(currentStats.getPlaycount()) + currentStats.comparePlayCount(previousStats));
		rankedScore.setText(NumberFormat.getInstance(Locale.getDefault()).format(currentStats.getRankedScore()) + currentStats.compareRankedScore(previousStats));
		totalHits.setText(NumberFormat.getInstance(Locale.getDefault()).format(currentStats.getTotalHits()) + currentStats.compareTotalHits(previousStats));
		ppCount.setText(NumberFormat.getInstance(Locale.getDefault()).format(currentStats.getPp()) + currentStats.comparePP(previousStats));
	}

	private synchronized BufferedImage getAvatar(String userID) throws Exception
	{
		try
		{
			return ImageIO.read(new URL("https:" + cutLine(getLineCodeFromLink("https://osu.ppy.sh/u/" + userID, "<div class=\"avatar-holder\">"), true, "\" alt=\"User avatar\"", "<div class=\"avatar-holder\"><img src=\"")));
		}
		catch(Exception e)
		{
			Main.logger.log(Level.WARNING, "Error getting avatar for " + userID, e);
		}
		return avatarDefaultImage;
	}

	private synchronized BufferedImage getFlag(String country) throws Exception
	{
		try
		{
			return ImageIO.read(new URL("http://s.ppy.sh/images/flags/" + country.toLowerCase() + ".gif"));
		}
		catch(Exception e)
		{}
		return avatarDefaultImage;
	}

	private double getScoreToNextLevel(int currentLevel, double currentScore)
	{
		currentLevel++;
		double result = -1;
		// 5,000 / 3 * (4n^3 - 3n^2 - n) + 1.25 * 1.8^(n - 60), where n <= 100
		// 26,931,190,829 + 100,000,000,000 * (n - 100), where n >= 101
		if(currentLevel <= 100)
		{
			if(currentLevel >= 2)
			{
				double temp = 4 * round(Math.pow(currentLevel, 3), 0) - 3 * round(Math.pow(currentLevel, 2), 0) - currentLevel;
				result = (5000D / 3D) * temp + 1.25 * round(Math.pow(1.8, currentLevel - 60), 0);
			}
			else
				result = 0;
		}
		else if(currentLevel >= 101)
		{
			int temp = currentLevel - 100;
			result = 26931190829D + 100000000000D * temp;
		}
		return round(result - currentScore, 0);
	}

	private int getLevel(double level)
	{
		return (int) level;
	}

	private double getProgressLevel(double level)
	{
		return level - ((int) level);
	}

	private void updateLevel(double level)
	{
		Main.logger.log(Level.FINE, "Setting level to " + level);
		double progress = round(getProgressLevel(level) * 100, 2);
		levelBar.setValue((int) progress);
		levelBar.setString(String.format(Main.resourceBundle.getString("level"), getLevel(level), progress));
	}

	synchronized public static String sendPost(String type, String key, String user, int selectedMode) throws Exception
	{
		Main.logger.log(Level.INFO, "Sending post request...");
		String urlParameters = "k=" + key + "&u=" + user + "&m=" + selectedMode + "&type=string&event_days=1";
		URL url = new URL("https://osu.ppy.sh/api/" + type + "?" + urlParameters);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setConnectTimeout(15000);
		connection.setReadTimeout(15000);
		connection.setRequestProperty("User-Agent", "Mozilla/5.0");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("charset", "utf-8");
		connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
		BufferedReader inputSream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while((inputLine = inputSream.readLine()) != null)
			response.append(inputLine);
		inputSream.close();
		response.deleteCharAt(0);
		response.deleteCharAt(response.length() - 1);
		return response.toString();
	}

	public static double round(double value, int places)
	{
		if(places < 0)
			throw new IllegalArgumentException();
		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	private String cutLine(final String string, final boolean deleteDelimiters, final String ending, final String... begining) throws Exception
	{
		if(!string.contains(ending))
			throw new Exception();
		boolean exists = false;
		for(final String temp : begining)
			if(string.contains(temp))
				exists = true;
		if(!exists)
			throw new Exception();
		int beginingIndex = 0;
		for(final String temp : begining)
			if((beginingIndex = string.indexOf(temp)) > -1)
				break;
		String result = string.substring(beginingIndex, string.indexOf(ending) + ending.length());
		if(deleteDelimiters)
		{
			result = result.replace(ending, "");
			for(final String temp : begining)
				result = result.replace(temp, "");
		}
		return result;
	}

	public static void hideFrame()
	{
		getFrame().setEnabled(false);
		getFrame().setFocusable(false);
	}

	public static void showFrame()
	{
		getFrame().setEnabled(true);
		getFrame().setFocusable(true);
		getFrame().setVisible(true);
	}

	public static void exit()
	{
		Main.logger.log(Level.INFO, "Exiting main frame...");
		getFrame().dispose();
	}

	public static void backFromTray()
	{
		getFrame().setState(JFrame.NORMAL);
		showFrame();
		getFrame().toFront();
	}

	public static JFrame getFrame()
	{
		return frame;
	}

	public static void setFrame(JFrame frame)
	{
		Interface.frame = frame;
	}

	public static void updateAutoCompletion(boolean status)
	{
		userNameField.setAutoCompletion(status);
	}
}
