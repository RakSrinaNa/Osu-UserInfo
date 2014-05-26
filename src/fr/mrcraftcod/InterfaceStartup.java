package fr.mrcraftcod;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * Used to show a startup frame with a progress bar.
 * 
 * @author MrCraftCod
 */
public class InterfaceStartup
{
	private JFrame frame;
	private JLabel label;
	private JProgressBar progressBar;
	private int step = 0;

	/**
	 * Constructor.
	 * 
	 * @param maxStep How many steps are in the startup.
	 */
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
		refreshProgressBarStep();
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

	/**
	 * Used to refresh the current step showed by the progress bar.
	 */
	private void refreshProgressBarStep()
	{
		progressBar.setValue(step);
	}

	/**
	 * Used to reset the current step.
	 */
	public void reset()
	{
		step = 0;
		label.setText("");
		refreshProgressBarStep();
	}

	/**
	 * Used to get to a new step.
	 * 
	 * @param text The text printed to indicate what step is currently processed.
	 */
	public void setStartupText(String text)
	{
		if(label != null)
		{
			label.setText(text);
			step++;
			refreshProgressBarStep();
		}
	}

	/**
	 * Used to skip a step.
	 */
	public void skipStep()
	{
		step++;
	}

	/**
	 * Used to close the frame
	 */
	public void exit()
	{
		frame.dispose();
	}
}
