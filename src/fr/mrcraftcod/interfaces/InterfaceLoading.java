package fr.mrcraftcod.interfaces;

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
import fr.mrcraftcod.utils.Utils;

public class InterfaceLoading extends SwingWorker<Boolean, String>
{
	private JFrame frame;
	private String user;
	private boolean hard;

	public InterfaceLoading(Frame parent, String user, boolean hard, boolean openFrame)
	{
		this.user = user;
		this.hard = hard;
		if(openFrame)
			initFrame(parent);
	}

	private void initFrame(Frame parent)
	{
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setContentPane(new TransparentPane(new BorderLayout()));
		frame.setTitle(Utils.resourceBundle.getString("loading"));
		frame.setIconImages(Utils.icons);
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
		p.setLocation(p.getX() + (parent.getSize().getWidth() / 2) - (icon.getIconWidth() / 2), p.getY() + (parent.getSize().getHeight() / 2) - (icon.getIconHeight() / 2));
		frame.setLocation(p);
		frame.setVisible(true);
		frame.pack();
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
		return Utils.mainFrame.getInfosServer(user, hard);
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
		if(frame != null)
			frame.dispose();
	}
}
