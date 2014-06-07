package fr.mrcraftcod.interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import fr.mrcraftcod.Main;
import fr.mrcraftcod.objects.TransparentPane;

public class InterfaceLoading extends SwingWorker<Boolean, String>
{
	private JDialog frame;
	private String user;
	private boolean hard;

	public InterfaceLoading(Frame parent, String user, boolean hard)
	{
		this.user = user;
		this.hard = hard;
		initFrame(parent);
	}

	private void initFrame(Frame parent)
	{
		frame = new JDialog(parent);
		frame.setUndecorated(true);
		frame.setContentPane(new TransparentPane(new BorderLayout()));
		frame.setTitle(Main.resourceBundle.getString("loading"));
		frame.setIconImages(Main.icons);
		frame.setBackground(new Color(255, 255, 255, 0));
		ImageIcon icon = null;
		icon = new ImageIcon(Main.class.getClassLoader().getResource("resources/images/loading.gif"));
		JLabel label = new JLabel();
		label.setIcon(icon);
		icon.setImageObserver(label);
		frame.getContentPane().add(label, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		Point p = parent.getLocation();
		System.out.println(p);
		p.setLocation(p.getX() + (parent.getSize().getWidth() / 2) - (icon.getIconWidth() / 2), p.getY() + (parent.getSize().getHeight() / 2) - (icon.getIconHeight() / 2));
		frame.setLocation(p);
		frame.setVisible(true);
		frame.pack();
		try
		{
			Main.frame.desactivate();
		}
		catch(Exception e)
		{}
	}

	@Override
	protected Boolean doInBackground() throws Exception
	{
		return Main.frame.getInfosServer(user, hard);
	}

	@Override
	protected void done()
	{
		try
		{
			Main.frame.activate();
		}
		catch(Exception e)
		{}
		frame.dispose();
	}
}
