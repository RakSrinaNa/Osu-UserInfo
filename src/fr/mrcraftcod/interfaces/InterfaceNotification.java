package fr.mrcraftcod.interfaces;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import fr.mrcraftcod.listeners.actions.CloseNotificationActionListener;
import fr.mrcraftcod.utils.Utils;

public class InterfaceNotification extends JFrame implements Runnable
{
	private static final long serialVersionUID = 8870236400957609469L;
	private final static int cooldown = 7000;
	private JFrame frame;

	public InterfaceNotification(String text)
	{
		int offset = 0, arc = 15;
		frame = new JFrame();
		frame.setTitle("New stats!");
		frame.setSize(400, 75);
		frame.setUndecorated(true);
		frame.setShape(new RoundRectangle2D.Double(offset, offset, offset + frame.getSize().getWidth(), offset + frame.getSize().getHeight(), arc, arc));
		frame.setLayout(new GridBagLayout());
		frame.setIconImages(Utils.icons);
		frame.setBackground(Utils.backColor);
		frame.getContentPane().setBackground(Utils.backColor);
		JButton closeButton = new JButton("X");
		closeButton.addActionListener(new CloseNotificationActionListener());
		closeButton.setFocusable(false);
		JTextArea messageLabel = new JTextArea(text);
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
		frame.add(closeButton, constraints);
		constraints.gridx = 0;
		constraints.gridy++;
		constraints.gridheight = 2;
		constraints.weightx = 5;
		constraints.weighty = 5;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(1, 1, 1, 2);
		frame.add(messageLabel, constraints);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.toFront();
		new Thread(this).start();
	}

	@Override
	public void run()
	{
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets toolHeight = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
		int x = scrSize.width - frame.getWidth() - 2;
		int y = scrSize.height;
		for(int i = 0; i < frame.getHeight() + toolHeight.bottom; i++)
		{
			frame.setLocation(x, y - i);
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
		for(int i = frame.getHeight() + toolHeight.bottom; i > 0; i--)
		{
			frame.setLocation(x, y - i);
			try
			{
				Thread.sleep(5);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		frame.dispose();
	}
}
