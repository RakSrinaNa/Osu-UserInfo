package fr.mrcraftcod.interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import fr.mrcraftcod.Main;
import fr.mrcraftcod.objects.TransparentPane;

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
		setFrame(new JFrame());
		getFrame().setUndecorated(true);
		getFrame().setContentPane(new TransparentPane(new BorderLayout()));
		getFrame().getContentPane().setBackground(Color.BLACK);
		getFrame().setTitle("Starting " + Main.APPNAME);
		getFrame().setIconImages(Main.icons);
		getFrame().setBackground(new Color(0, 255, 0, 0));
		label = new JLabel();
		label.setForeground(new Color(191, 98, 4));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		progressBar = new JProgressBar(0, maxStep);
		progressBar.setStringPainted(true);
		refreshProgressBarStep();
		getFrame().getContentPane().add(progressBar, BorderLayout.NORTH);
		getFrame().getContentPane().add(label, BorderLayout.SOUTH);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getFrame().setLocation((dim.width - 500) / 2, (dim.height - 50) / 2);
		getFrame().setPreferredSize(new Dimension(500, 50));
		getFrame().setVisible(true);
		getFrame().toFront();
		getFrame().pack();
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
	 * Used to set a new step.
	 * 
	 * @param step The current step.
	 * @param text The text printed to indicate what step is currently processed.
	 */
	public void setStartupText(int step, String text)
	{
		if(label != null)
		{
			label.setText(text);
			this.step = step;
			refreshProgressBarStep();
		}
	}

	/**
	 * Used to add a new step.
	 * 
	 * @param text The text printed to indicate what step is currently processed.
	 */
	public void addStartupText(String text)
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
		getFrame().dispose();
	}

	public JFrame getFrame()
	{
		return frame;
	}

	public void setFrame(JFrame frame)
	{
		this.frame = frame;
	}
}
