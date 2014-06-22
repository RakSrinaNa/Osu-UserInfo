package fr.mrcraftcod.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import fr.mrcraftcod.Main;
import fr.mrcraftcod.objects.TransparentPane;

/**
 * Used to fetch user infos in the background.
 *
 * @author MrCraftCod
 */
public class LoadingWorker extends SwingWorker<Boolean, String>
{
	private JFrame frame;
	private String user;
	private boolean hard;
	private boolean forceDisplay;
	private boolean forceFetch;

	/**
	 * Constructor.
	 *
	 * @param parent The parent frame.
	 * @param user The user to fetch (username).
	 * @param hard Fetch the infos in 'hard mode'.
	 * @param openFrame Open or not the loading frame.
	 * @param forceDisplay Force the function to update stats on screen even if they are the same.
	 * @param forceFetch Force the program to fetch datas even if the cooldown time isn't finished?
	 */
	public LoadingWorker(Frame parent, String user, boolean hard, boolean openFrame, boolean forceDisplay, boolean forceFetch)
	{
		this.user = user;
		this.hard = hard;
		this.forceDisplay = forceDisplay;
		this.forceFetch = forceFetch;
		if(openFrame)
			initFrame(parent);
	}

	/**
	 * Create and open the loading frame.
	 *
	 * @param parent The parent frame.
	 */
	private void initFrame(Frame parent)
	{
		this.frame = new JFrame();
		this.frame.setUndecorated(true);
		this.frame.setContentPane(new TransparentPane(new BorderLayout()));
		this.frame.setTitle(Utils.resourceBundle.getString("loading"));
		this.frame.setIconImages(Utils.icons);
		this.frame.setBackground(new Color(255, 255, 255, 0));
		ImageIcon icon = null;
		icon = new ImageIcon(Main.class.getClassLoader().getResource("resources/images/loading.gif"));
		JLabel label = new JLabel();
		label.setIcon(icon);
		icon.setImageObserver(label);
		this.frame.getContentPane().add(label, BorderLayout.CENTER);
		this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frame.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		Point p = parent.getLocation();
		p.setLocation(p.getX() + parent.getSize().getWidth() / 2 - icon.getIconWidth() / 2, p.getY() + parent.getSize().getHeight() / 2 - icon.getIconHeight() / 2);
		this.frame.setLocation(p);
		this.frame.setVisible(true);
		this.frame.pack();
		try
		{
			Utils.mainFrame.deactivateFrame();
		}
		catch(Exception e)
		{}
	}

	/**
	 * Fetch the infos.
	 *
	 * @see SwingWorker#doInBackground()
	 */
	@Override
	protected Boolean doInBackground() throws Exception
	{
		return Utils.getInfosServer(this.user, this.hard, this.forceDisplay, this.forceFetch);
	}

	/**
	 * Return to main frame when done.
	 *
	 * @see SwingWorker#done()
	 */
	@Override
	protected void done()
	{
		try
		{
			Utils.mainFrame.activateFrame();
		}
		catch(Exception e)
		{}
		if(this.frame != null)
			this.frame.dispose();
	}
}
