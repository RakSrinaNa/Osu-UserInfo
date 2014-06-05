package fr.mrcraftcod.interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import fr.mrcraftcod.Main;
import fr.mrcraftcod.objects.TransparentPane;

public class InterfaceLoading
{
	private JFrame frame;

	public InterfaceLoading(Component parent)
	{
		System.out.println(new Date().getTime());
		frame = new JFrame();
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
		frame.setLocationRelativeTo(parent);
		frame.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		frame.setVisible(true);
		frame.toFront();
		frame.pack();
		Main.frame.desactivate();
	}

	public void close()
	{
		System.out.println(new Date().getTime());
		Main.frame.activate();
		frame.dispose();
	}
}
