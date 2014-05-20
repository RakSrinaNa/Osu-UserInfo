package fr.mrcraftcod;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class InterfaceStartup
{
	private JFrame frame;
	private JLabel label;
	private JProgressBar progressBar;
	private int step = 0;

	public InterfaceStartup(int maxStep)
	{
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setContentPane(new TransparentPane(new BorderLayout()));
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setTitle("Starting " + Main.APPNAME);
		frame.setIconImages(Main.icons);
		frame.setBackground(new Color(0, 255, 0, 0));
		label = new JLabel();
		label.setForeground(new Color(191, 98, 4));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		progressBar = new JProgressBar(0, maxStep);
		progressBar.setStringPainted(true);
		setProgressBar();
		frame.getContentPane().add(progressBar, BorderLayout.NORTH);
		frame.getContentPane().add(label, BorderLayout.SOUTH);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocation((dim.width - 500) / 2, (dim.height - 50) / 2);
		frame.setPreferredSize(new Dimension(500, 50));
		frame.setVisible(true);
		frame.toFront();
		frame.pack();
	}

	private void setProgressBar()
	{
		progressBar.setValue(step);
	}

	public void reset()
	{
		step = 0;
	}

	public void setStartupText(String text)
	{
		if(label != null)
		{
			label.setText(text);
			step++;
			setProgressBar();
		}
	}

	public void skipStep()
	{
		step++;
	}

	public void exit()
	{
		frame.dispose();
	}
}
