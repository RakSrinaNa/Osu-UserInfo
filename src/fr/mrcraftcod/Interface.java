package fr.mrcraftcod;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	private HashMap<String, JLabel> showingArea;
	private ImagePanel avatar;
	private JLabel level, username;
	private JProgressBar levelBar;

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
		showingArea = new HashMap<String, JLabel>();
		String[] fields = {"user_id", "count300", "count100", "count50", "playcount", "ranked_score", "total_score", "pp_rank", "level", "pp_raw", "accuracy", "count_rank_ss", "count_rank_s", "count_rank_a", "country"};
		/************** FRAME INFOS ********************/
		frame = new JFrame(Main.APPNAME);
		frame.setLayout(new GridBagLayout());
		frame.setPreferredSize(new Dimension(500, 500));
		frame.setAlwaysOnTop(false);
		frame.setIconImages(Main.icons);
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*************** TOP PANEL **********************/
		JPanel topUserPanel = new JPanel(new GridBagLayout());
		JLabel usernameAsk = new JLabel("Username : ");
		usernameAsk.setHorizontalAlignment(JLabel.CENTER);
		usernameAsk.setVerticalAlignment(JLabel.CENTER);
		userNameField = new JTextField();
		userNameField.setPreferredSize(new Dimension(200, 30));
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
		topUserPanel.add(usernameAsk, constraint);
		constraint.weightx = 0.8;
		constraint.gridx = 1;
		topUserPanel.add(userNameField, constraint);
		constraint.gridx = 2;
		constraint.weightx = 0.1;
		topUserPanel.add(validButon, constraint);
		/***************** LEVEL PANEL ********************/
		JPanel levelUserPanel = new JPanel(new GridBagLayout());
		level = new JLabel(" ");
		level.setHorizontalAlignment(JLabel.CENTER);
		level.setVerticalAlignment(JLabel.CENTER);
		levelBar = new JProgressBar();
		levelBar.setMaximum(100);
		levelBar.setStringPainted(true);
		// Construct
		constraint = new GridBagConstraints();
		constraint.anchor = GridBagConstraints.CENTER;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.gridwidth = 1;
		constraint.weightx = 0.1;
		constraint.weighty = 1;
		constraint.gridx = 0;
		constraint.gridy = 0;
		levelUserPanel.add(level, constraint);
		constraint.weightx = 0.9;
		constraint.gridwidth = 2;
		constraint.gridx = 1;
		levelUserPanel.add(levelBar, constraint);
		/******************** AVATAR PANEL *****************/
		JPanel avatarPanel = new JPanel(new GridBagLayout());
		int avatarSize = 128;
		avatar = new ImagePanel();
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
							String user_id = showingArea.get("user_id").getText();
							if(user_id.equalsIgnoreCase(""))
								return;
							desktop.browse(new URL("https://osu.ppy.sh/u/" + user_id).toURI());
						}
						catch(final Exception e)
						{
							Main.logger.log(Level.WARNING, "Problem when trying to open link in web browser!", e);
						}
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
		frame.add(topUserPanel, constraint);
		constraint.insets = new Insets(10, 0, 0, 0);
		constraint.gridy = line++;
		frame.add(avatarPanel, constraint);
		constraint.insets = new Insets(0, 0, 0, 0);
		constraint.gridy = line++;
		frame.add(levelUserPanel, constraint);
		JLabel tmp;
		constraint.gridy = line++;
		constraint.fill = GridBagConstraints.HORIZONTAL;
		for(String param : fields)
		{
			constraint.gridwidth = 1;
			constraint.gridy = line++;
			constraint.gridx = 0;
			constraint.weightx = 0.1;
			frame.add(new JLabel(param), constraint);
			constraint.gridwidth = 2;
			constraint.gridx = 1;
			constraint.weightx = 0.9;
			tmp = new JLabel();
			showingArea.put(param, tmp);
			frame.add(tmp, constraint);
		}
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(new Point((dimension.width - 700) / 2, (dimension.height - 130) / 2));
		frame.pack();
		frame.toFront();
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
		try
		{
			final JSONObject obj = new JSONObject(getJSONText(user));
			Random r = new Random();
			username.setText(obj.getString("username"));
			username.setForeground(new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
			updateLevel(obj.getDouble("level"));
			SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						avatar.setImage(getAvatar(obj.getString("user_id")));
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			});
			/******************/
			@SuppressWarnings("unchecked")
			Iterator<String> keys = obj.keys();
			while(keys.hasNext())
			{
				String key = keys.next();
				if(showingArea.containsKey(key))
				{
					showingArea.get(key).setText(obj.getString(key));
				}
			}
		}
		catch(JSONException | IOException e)
		{
			e.printStackTrace();
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

	private void updateLevel(double d)
	{
		int baseLevel = (int) d;
		double progress = round((d - baseLevel) * 100, 2);
		level.setText("Level " + baseLevel + "(" + progress + "%)");
		levelBar.setValue((int) progress);
		levelBar.setString(progress + "%");
	}

	private synchronized String getJSONText(String user) throws IOException
	{
		String str = null, urlParameters = "k=" + Main.API_KEY + "&u=" + user + "&m=" + "0" + "&type=string&event_days=1";
		StringBuilder page = new StringBuilder();
		if(true)
		{
			final BufferedReader in = new BufferedReader(new FileReader(new File(".", "test.json")));
			while((str = in.readLine()) != null)
				page.append(str);
			in.close();
			return page.toString();
		}
		URL url = new URL(" https://osu.ppy.sh/p/api/get_user");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("User-Agent", "Mozilla/5.0");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("charset", "utf-8");
		connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
		connection.setUseCaches(false);
		connection.setConnectTimeout(30000);
		connection.setReadTimeout(30000);
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		writer.write(urlParameters);
		writer.flush();
		writer.close();
		final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
		while((str = in.readLine()) != null)
			page.append(str);
		in.close();
		return page.toString();
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
