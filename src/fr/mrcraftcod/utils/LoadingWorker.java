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

public class LoadingWorker extends SwingWorker<Boolean, String>
{
	private JFrame frame;
	private String user;
	private boolean hard;

	public LoadingWorker(Frame parent, String user, boolean hard, boolean openFrame)
	{
		this.user = user;
		this.hard = hard;
		if(openFrame)
			initFrame(parent);
	}

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
			Utils.mainFrame.desactivateFrame();
		}
		catch(Exception e)
		{}
	}

	@Override
	protected Boolean doInBackground() throws Exception
	{
		return Utils.getInfosServer(this.user, this.hard);
	}

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
