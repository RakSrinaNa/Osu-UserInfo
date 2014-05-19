package fr.mrcraftcod;

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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import org.json.JSONException;
import org.json.JSONObject;

public class Interface extends JFrame
{
	private static final long serialVersionUID = 2629819156905465351L;
	private JFrame frame;
	private JTextField userNameField;
	private ImagePanel avatar;
	private JComboBox<String> mode;
	private String lastUser = "";
	private JLabel totalHits, username, countSS, countS, countA, playCount, rankedScore, totalScore, ppCount, accuracy, country, hitCount300, hitCount100, hitCount50;
	private JProgressBar levelBar;
	private Date lastPost = new Date(0);
	public static ResourceBundle resourceBundle;

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

	public Interface() throws IOException
	{
		resourceBundle = ResourceBundle.getBundle("resources/lang/lang", Locale.getDefault());
		/************** FRAME INFOS ********************/
		frame = new JFrame(Main.APPNAME);
		frame.setVisible(false);
		frame.setLayout(new GridBagLayout());
		frame.setMinimumSize(new Dimension(350, 450));
		frame.setPreferredSize(new Dimension(550, 550));
		frame.setAlwaysOnTop(false);
		frame.setIconImages(Main.icons);
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*************** SEARCH PANEL **********************/
		JPanel searchPanel = new JPanel(new GridBagLayout());
		// searchPanel.setBackground(Color.GRAY);
		JLabel usernameAsk = new JLabel(resourceBundle.getString("username") + " : ");
		usernameAsk.setHorizontalAlignment(JLabel.CENTER);
		usernameAsk.setVerticalAlignment(JLabel.CENTER);
		userNameField = new JTextField();
		userNameField.setPreferredSize(new Dimension(200, 30));
		new GhostText(userNameField, resourceBundle.getString("ghost_username_field"));
		userNameField.addKeyListener(new KeyListener()
		{
			@Override
			public void keyPressed(KeyEvent arg0)
			{
				if(KeyEvent.VK_ENTER == arg0.getExtendedKeyCode())
					getInfos(userNameField.getText());
			}

			@Override
			public void keyReleased(KeyEvent arg0)
			{}

			@Override
			public void keyTyped(KeyEvent arg0)
			{}
		});
		mode = new JComboBox<String>(new String[] {"Osu!", "Taiko", "CTB", "Osu!Mania"});
		mode.setSelectedIndex(0);
		JButton validButon = new JButton(new ImageIcon(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/loupe.png"))));
		validButon.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				getInfos(userNameField.getText());
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
		JPanel levelUserPanel = new JPanel(new GridBagLayout());
		levelUserPanel.setBackground(Color.GRAY);
		levelBar = new JProgressBar();
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
		levelUserPanel.add(levelBar, constraint);
		/***************** HITS PANEL ********************/
		JPanel hitCountPanel = new JPanel(new GridBagLayout());
		hitCountPanel.setBackground(Color.GRAY);
		float picturesSize = 40f;
		// 300
		JPanel count300Panel = new JPanel();
		count300Panel.setBackground(Color.GRAY);
		final ImagePanel count300Picture = new ImagePanel();
		count300Picture.setBackground(Color.GRAY);
		count300Picture.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		count300Picture.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		count300Picture.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		count300Picture.setImage(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/hit300.png")), picturesSize, picturesSize));
		hitCount300 = new JLabel("0");
		hitCount300.setHorizontalAlignment(JLabel.CENTER);
		hitCount300.setVerticalAlignment(JLabel.CENTER);
		count300Panel.add(count300Picture);
		count300Panel.add(hitCount300);
		// 100
		JPanel count100Panel = new JPanel();
		count100Panel.setBackground(Color.GRAY);
		final ImagePanel count100Picture = new ImagePanel();
		count100Picture.setBackground(Color.GRAY);
		count100Picture.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		count100Picture.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		count100Picture.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		count100Picture.setImage(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/hit100.png")), picturesSize, picturesSize));
		hitCount100 = new JLabel("0");
		hitCount100.setHorizontalAlignment(JLabel.CENTER);
		hitCount100.setVerticalAlignment(JLabel.CENTER);
		count100Panel.add(count100Picture);
		count100Panel.add(hitCount100);
		// 50
		JPanel count50Panel = new JPanel();
		count50Panel.setBackground(Color.GRAY);
		picturesSize = 30f;
		final ImagePanel count50Picture = new ImagePanel();
		count50Picture.setBackground(Color.GRAY);
		count50Picture.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		count50Picture.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		count50Picture.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		count50Picture.setImage(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/hit50.png")), picturesSize, picturesSize));
		hitCount50 = new JLabel("0");
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
		JPanel ranksUserPanel = new JPanel(new GridBagLayout());
		ranksUserPanel.setBackground(Color.GRAY);
		picturesSize = 40f;
		// SS
		JPanel ssPanel = new JPanel();
		ssPanel.setBackground(Color.GRAY);
		final ImagePanel ssPicture = new ImagePanel();
		ssPicture.setBackground(Color.GRAY);
		ssPicture.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		ssPicture.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		ssPicture.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		ssPicture.setImage(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/SS.png")), picturesSize, picturesSize));
		countSS = new JLabel("0");
		countSS.setHorizontalAlignment(JLabel.CENTER);
		countSS.setVerticalAlignment(JLabel.CENTER);
		ssPanel.add(ssPicture);
		ssPanel.add(countSS);
		// S
		JPanel sPanel = new JPanel();
		sPanel.setBackground(Color.GRAY);
		final ImagePanel sPicture = new ImagePanel();
		sPicture.setBackground(Color.GRAY);
		sPicture.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		sPicture.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		sPicture.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		sPicture.setImage(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/S.png")), picturesSize, picturesSize));
		countS = new JLabel("0");
		countS.setHorizontalAlignment(JLabel.CENTER);
		countS.setVerticalAlignment(JLabel.CENTER);
		sPanel.add(sPicture);
		sPanel.add(countS);
		// A
		JPanel aPanel = new JPanel();
		aPanel.setBackground(Color.GRAY);
		final ImagePanel aPicture = new ImagePanel();
		aPicture.setBackground(Color.GRAY);
		aPicture.setMinimumSize(new Dimension((int) picturesSize, (int) picturesSize));
		aPicture.setPreferredSize(new Dimension((int) picturesSize, (int) picturesSize));
		aPicture.setMaximumSize(new Dimension((int) picturesSize, (int) picturesSize));
		aPicture.setImage(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/A.png")), picturesSize, picturesSize));
		countA = new JLabel("0");
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
		JPanel avatarPanel = new JPanel(new GridBagLayout());
		avatarPanel.setBackground(Color.GRAY);
		int avatarSize = 128;
		avatar = new ImagePanel(resizeBufferedImage(ImageIO.read(Main.class.getClassLoader().getResource("resources/images/avatar.png")), 128, 128));
		avatar.setBackground(Color.GRAY);
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
				if(arg0.getClickCount() > 1)
				{
					final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
					if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
						try
						{
							String user_id = username.getText();
							if(user_id.equalsIgnoreCase(""))
								return;
							desktop.browse(new URL("https://osu.ppy.sh/u/" + user_id).toURI());
						}
						catch(final Exception e)
						{}
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0)
			{}
		});
		username = new JLabel(" ");
		username.setFont(new Font(username.getFont().getName(), Font.PLAIN, 25));
		// Construct
		constraint = new GridBagConstraints();
		constraint.fill = GridBagConstraints.NONE;
		constraint.anchor = GridBagConstraints.CENTER;
		constraint.gridwidth = 3;
		constraint.weightx = 0.1;
		constraint.weighty = 1;
		constraint.gridx = 0;
		constraint.gridy = 0;
		avatarPanel.add(avatar, constraint);
		constraint.gridy = 1;
		avatarPanel.add(username, constraint);
		/**************** OTHERS PANEL *********************/
		JPanel otherPanel = new JPanel(new GridBagLayout());
		otherPanel.setBackground(Color.GRAY);
		// PlayCount
		JLabel playCountLabel = new JLabel(resourceBundle.getString("play_count") + " : ");
		playCountLabel.setHorizontalAlignment(JLabel.RIGHT);
		playCountLabel.setVerticalAlignment(JLabel.CENTER);
		playCount = new JLabel("          ");
		playCount.setHorizontalAlignment(JLabel.LEFT);
		playCount.setVerticalAlignment(JLabel.CENTER);
		// RankedScore
		JLabel rankedScoreLabel = new JLabel(resourceBundle.getString("ranked_score") + " : ");
		rankedScoreLabel.setHorizontalAlignment(JLabel.RIGHT);
		rankedScoreLabel.setVerticalAlignment(JLabel.CENTER);
		rankedScore = new JLabel("          ");
		rankedScore.setHorizontalAlignment(JLabel.LEFT);
		rankedScore.setVerticalAlignment(JLabel.CENTER);
		// TotalScore
		JLabel totalScoreLabel = new JLabel(resourceBundle.getString("total_score") + " : ");
		totalScoreLabel.setHorizontalAlignment(JLabel.RIGHT);
		totalScoreLabel.setVerticalAlignment(JLabel.CENTER);
		totalScore = new JLabel("          ");
		totalScore.setHorizontalAlignment(JLabel.LEFT);
		totalScore.setVerticalAlignment(JLabel.CENTER);
		// PP
		JLabel ppCountLabel = new JLabel("PP : ");
		ppCountLabel.setHorizontalAlignment(JLabel.RIGHT);
		ppCountLabel.setVerticalAlignment(JLabel.CENTER);
		ppCount = new JLabel("          ");
		ppCount.setHorizontalAlignment(JLabel.LEFT);
		ppCount.setVerticalAlignment(JLabel.CENTER);
		// Accuracy
		JLabel accuracyLabel = new JLabel(resourceBundle.getString("accuracy") + " : ");
		accuracyLabel.setHorizontalAlignment(JLabel.RIGHT);
		accuracyLabel.setVerticalAlignment(JLabel.CENTER);
		accuracy = new JLabel("          ");
		accuracy.setHorizontalAlignment(JLabel.LEFT);
		accuracy.setVerticalAlignment(JLabel.CENTER);
		// Country
		JLabel countryLabel = new JLabel(resourceBundle.getString("country") + " : ");
		countryLabel.setHorizontalAlignment(JLabel.RIGHT);
		countryLabel.setVerticalAlignment(JLabel.CENTER);
		country = new JLabel("          ");
		country.setHorizontalAlignment(JLabel.LEFT);
		country.setVerticalAlignment(JLabel.CENTER);
		// Country
		JLabel totalHitsLabel = new JLabel(resourceBundle.getString("total_hits") + " : ");
		totalHitsLabel.setHorizontalAlignment(JLabel.RIGHT);
		totalHitsLabel.setVerticalAlignment(JLabel.CENTER);
		totalHits = new JLabel("          ");
		totalHits.setHorizontalAlignment(JLabel.LEFT);
		totalHits.setVerticalAlignment(JLabel.CENTER);
		// Construct
		constraint = new GridBagConstraints();
		constraint.fill = GridBagConstraints.NONE;
		constraint.anchor = GridBagConstraints.BASELINE;
		constraint.gridwidth = 1;
		constraint.weightx = 1;
		constraint.weighty = 1;
		constraint.gridx = 0;
		constraint.gridy = 0;
		constraint.anchor = GridBagConstraints.EAST;
		otherPanel.add(playCountLabel, constraint);
		constraint.gridx = 1;
		constraint.anchor = GridBagConstraints.WEST;
		otherPanel.add(playCount, constraint);
		constraint.gridx = 0;
		constraint.gridy = 1;
		constraint.anchor = GridBagConstraints.EAST;
		otherPanel.add(rankedScoreLabel, constraint);
		constraint.gridx = 1;
		constraint.anchor = GridBagConstraints.WEST;
		otherPanel.add(rankedScore, constraint);
		constraint.gridx = 0;
		constraint.gridy = 2;
		constraint.anchor = GridBagConstraints.EAST;
		otherPanel.add(totalScoreLabel, constraint);
		constraint.gridx = 1;
		constraint.anchor = GridBagConstraints.WEST;
		otherPanel.add(totalScore, constraint);
		constraint.gridx = 0;
		constraint.gridy = 3;
		constraint.anchor = GridBagConstraints.EAST;
		otherPanel.add(ppCountLabel, constraint);
		constraint.gridx = 1;
		constraint.anchor = GridBagConstraints.WEST;
		otherPanel.add(ppCount, constraint);
		constraint.gridx = 0;
		constraint.gridy = 4;
		constraint.anchor = GridBagConstraints.EAST;
		otherPanel.add(accuracyLabel, constraint);
		constraint.gridx = 1;
		constraint.anchor = GridBagConstraints.WEST;
		otherPanel.add(accuracy, constraint);
		constraint.gridx = 0;
		constraint.gridy = 5;
		constraint.anchor = GridBagConstraints.EAST;
		otherPanel.add(countryLabel, constraint);
		constraint.gridx = 1;
		constraint.anchor = GridBagConstraints.WEST;
		otherPanel.add(country, constraint);
		constraint.insets = new Insets(20, 0, 0, 0);
		constraint.gridx = 0;
		constraint.gridy = 6;
		constraint.anchor = GridBagConstraints.EAST;
		otherPanel.add(totalHitsLabel, constraint);
		constraint.gridx = 1;
		constraint.anchor = GridBagConstraints.WEST;
		otherPanel.add(totalHits, constraint);
		/*************** FRAME CONSTRUCT ******************/
		constraint = new GridBagConstraints();
		constraint.anchor = GridBagConstraints.PAGE_START;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		int line = 0;
		constraint.gridwidth = GridBagConstraints.REMAINDER;
		constraint.weightx = 1;
		constraint.weighty = 1;
		constraint.gridx = 0;
		constraint.gridy = line++;
		frame.add(searchPanel, constraint);
		constraint.insets = new Insets(10, 0, 0, 0);
		constraint.gridy = line++;
		frame.add(avatarPanel, constraint);
		constraint.insets = new Insets(0, 0, 0, 0);
		constraint.gridy = line++;
		frame.add(levelUserPanel, constraint);
		constraint.gridy = line++;
		frame.add(otherPanel, constraint);
		constraint.gridy = line++;
		frame.add(hitCountPanel, constraint);
		constraint.gridy = line++;
		frame.add(ranksUserPanel, constraint);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(new Point((dimension.width - 700) / 2, (dimension.height - 130) / 2));
		frame.pack();
		frame.setVisible(true);
		frame.toFront();
	}

	private BufferedImage resizeBufferedImage(BufferedImage image, float width, float height)
	{
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

	private void getInfos(String user)
	{
		if(new Date().getTime() - lastPost.getTime() < 2500)
			return;
		lastPost = new Date();
		userNameField.setBackground(null);
		try
		{
			final JSONObject obj = new JSONObject(sendPost(Main.API_KEY, user, mode.getSelectedIndex()));
			if(!lastUser.equals(obj.get("username")))
				avatar.setImage(null);
			Random r = new Random();
			username.setText(obj.getString("username") + " (#" + NumberFormat.getInstance(Locale.getDefault()).format(obj.getDouble("pp_rank")) + ")");
			username.setForeground(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
			updateLevel(obj.getDouble("level"));
			countSS.setText(String.valueOf(obj.getInt("count_rank_ss")));
			countS.setText(String.valueOf(obj.getInt("count_rank_s")));
			countA.setText(String.valueOf(obj.getInt("count_rank_a")));
			playCount.setText(NumberFormat.getInstance(Locale.getDefault()).format(obj.getInt("playcount")));
			rankedScore.setText(NumberFormat.getInstance(Locale.getDefault()).format(obj.getLong("ranked_score")));
			totalScore.setText(String.format(resourceBundle.getString("total_score_value"), NumberFormat.getInstance(Locale.getDefault()).format(obj.getDouble("total_score")), NumberFormat.getInstance(Locale.getDefault()).format(getScoreToNextLevel(getLevel(obj.getDouble("level")), obj.getDouble("total_score"))), getLevel(obj.getDouble("level")) + 1));
			ppCount.setText(NumberFormat.getInstance(Locale.getDefault()).format(obj.getDouble("pp_raw")));
			accuracy.setText(String.valueOf(round(obj.getDouble("accuracy"), 2)) + "%");
			country.setText(CountryCode.getByCode(obj.getString("country")).getName());
			long totalHit = obj.getLong("count300") + obj.getLong("count100") + obj.getLong("count50");
			totalHits.setText(NumberFormat.getInstance(Locale.getDefault()).format(totalHit));
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setMaximumFractionDigits(2);
			hitCount300.setText(NumberFormat.getInstance(Locale.getDefault()).format(obj.getLong("count300")) + " (" + decimalFormat.format((obj.getLong("count300") * 100f) / totalHit) + "%)");
			hitCount100.setText(NumberFormat.getInstance(Locale.getDefault()).format(obj.getLong("count100")) + " (" + decimalFormat.format((obj.getLong("count100") * 100f) / totalHit) + "%)");
			hitCount50.setText(NumberFormat.getInstance(Locale.getDefault()).format(obj.getLong("count50")) + " (" + decimalFormat.format((obj.getLong("count50") * 100f) / totalHit) + "%)");
			if(!lastUser.equals(obj.get("username")))
			{
				lastUser = obj.getString("username");
				SwingUtilities.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							avatar.setImage(resizeBufferedImage(getAvatar(obj.getString("user_id")), 128, 128));
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				});
			}
		}
		catch(JSONException | IOException e)
		{
			e.printStackTrace();
			userNameField.setBackground(Color.RED);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private synchronized BufferedImage getAvatar(String user_id) throws MalformedURLException, IOException, Exception
	{
		return ImageIO.read(new URL("https:" + cutLine(getLineCodeFromLink("https://osu.ppy.sh/u/" + user_id, "<div class=\"avatar-holder\">"), true, "\" alt=\"User avatar\"", "<div class=\"avatar-holder\"><img src=\"")));
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
				double temp = 4 * Math.pow(currentLevel, 3) - 3 * Math.pow(currentLevel, 2) - currentLevel;
				result = 5000 / 3 * temp + 1.25 * Math.pow(1.8, currentLevel - 60);
			}
			else
			{
				result = 0;
			}
		}
		else if(currentLevel >= 101)
		{
			int temp = currentLevel - 100;
			result = 26931190829D + 100000000000D * temp;
		}
		return (int) (result - currentScore);
	}

	private int getLevel(double d)
	{
		return (int) d;
	}

	private double getProgressLevel(double d)
	{
		return d - ((int) d);
	}

	private void updateLevel(double d)
	{
		double progress = round(getProgressLevel(d) * 100, 2);
		levelBar.setValue((int) progress);
		levelBar.setString(String.format(resourceBundle.getString("level"), getLevel(d), progress));
	}

	synchronized public static String sendPost(String key, String user, int selectedMode) throws Exception
	{
		String urlParameters = "k=" + key + "&u=" + user + "&m=" + selectedMode + "&type=string&event_days=1";
		URL url = new URL("https://osu.ppy.sh/api/get_user?" + urlParameters);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
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

	private double round(double value, int places)
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
}
