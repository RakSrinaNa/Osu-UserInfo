package fr.mrcraftcod.frames;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import fr.mrcraftcod.listeners.actions.CloseNotificationActionListener;
import fr.mrcraftcod.utils.Utils;

/**
 * Interface to show a notification.
 *
 * @author MrCraftCod
 */
public class InterfaceNotification extends JFrame implements Runnable
{
	private static final long serialVersionUID = 8870236400957609469L;
	private final static int cooldown = 7000;

	/**
	 * Constructor.
	 *
	 * @param name The name of the notification.
	 * @param textRank The text of the notification.
	 * @param rank The rank to show.
	 * @param pp The PP to show.
	 * @param playCount The play count to show.
	 * @param totalScore The total score to show.
	 * @param rankedScore The ranked score to show.
	 */
	public InterfaceNotification(String name, String textRank, double rank, double pp, int playCount, long totalScore, long rankedScore)
	{
		super();
		int offset = 0, arc = 15;
		setTitle("New stats!");
		setSize(400, 75);
		setUndecorated(true);
		setShape(new RoundRectangle2D.Double(offset, offset, offset + getSize().getWidth(), offset + getSize().getHeight(), arc, arc));
		setLayout(new GridBagLayout());
		setIconImages(Utils.icons);
		setBackground(Utils.backColor);
		getContentPane().setBackground(Utils.backColor);
		setAlwaysOnTop(true);
		JButton closeButton = new JButton("X");
		closeButton.addActionListener(new CloseNotificationActionListener());
		closeButton.setFocusable(false);
		JTextArea messageLabel = new JTextArea(processText(name, textRank, rank, pp, playCount, totalScore, rankedScore));
		messageLabel.setBackground(Utils.backColor);
		messageLabel.setEditable(false);
		messageLabel.setBorder(null);
		messageLabel.setLineWrap(true);
		messageLabel.setWrapStyleWord(true);
		messageLabel.setFocusable(false);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.insets = new Insets(-offset, 1, 1, -offset);
		add(closeButton, constraints);
		constraints.gridx = 0;
		constraints.gridy++;
		constraints.gridheight = 2;
		constraints.weightx = 5;
		constraints.weighty = 5;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(1, 1, 1, 2);
		add(messageLabel, constraints);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
		toFront();
		new Thread(this).start();
	}

	/**
	 * Used to make the frame showing up and down.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run()
	{
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		int x = scrSize.width - getWidth() - 2;
		int y = scrSize.height;
		for(int i = 0; i < getHeight() + toolHeight.bottom; i++)
		{
			setLocation(x, y - i);
			try
			{
				Thread.sleep(5);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		try
		{
			Thread.sleep(cooldown);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		for(int i = getHeight() + toolHeight.bottom; i > 0; i--)
		{
			setLocation(x, y - i);
			try
			{
				Thread.sleep(5);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		dispose();
	}

	/**
	 * Used to process the text to show.
	 *
	 * @param name The name of the notification.
	 * @param textRank The text of the notification.
	 * @param rank The rank to show.
	 * @param pp The PP to show.
	 * @param playCount The play count to show.
	 * @param totalScore The total score to show.
	 * @param rankedScore The ranked score to show.
	 * @return the formatted text.
	 */
	private String processText(String name, String textRank, double rank, double pp, int playCount, long totalScore, long rankedScore)
	{
		NumberFormat format = NumberFormat.getInstance(Utils.locale);
		NumberFormat formatt = NumberFormat.getInstance(Utils.locale);
		formatt.setMaximumFractionDigits(2);
		return String.format(Utils.resourceBundle.getString("notification_text"), name, textRank, format.format(rank), formatt.format(pp), format.format(playCount), format.format(totalScore), format.format(rankedScore));
	}
}
