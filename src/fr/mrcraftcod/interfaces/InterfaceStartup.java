package fr.mrcraftcod.interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import javax.swing.JFrame;
import javax.swing.JLabel;
import fr.mrcraftcod.Main;
import fr.mrcraftcod.objects.JProgressBarLabel;
import fr.mrcraftcod.objects.TransparentPane;
import fr.mrcraftcod.utils.Utils;

/**
 * Used to show a startup frame with a progress bar.
 *
 * @author MrCraftCod
 */
public class InterfaceStartup extends JFrame
{
	private static final long serialVersionUID = -5316219219270463412L;
	private JLabel label;
	private JProgressBarLabel progressBar;
	private int step = 0;
	private boolean percentMode;

	/**
	 * Constructor.
	 *
	 * @param maxStep How many steps are in the startup.
	 */
	public InterfaceStartup(int maxStep)
	{
		super();
		this.percentMode = maxStep == 100;
		Utils.logger.log(Level.INFO, "Creating startup frame...");
		getFrame().setUndecorated(true);
		getFrame().setContentPane(new TransparentPane(new BorderLayout()));
		getFrame().getContentPane().setBackground(Color.BLACK);
		getFrame().setTitle("Starting " + Main.APPNAME);
		getFrame().setIconImages(Utils.icons);
		getFrame().setBackground(new Color(0, 255, 0, 0));
		this.label = new JLabel();
		this.label.setFont(Utils.fontMain);
		this.label.setForeground(new Color(255, 255, 255));
		this.label.setHorizontalAlignment(JLabel.CENTER);
		this.label.setVerticalAlignment(JLabel.CENTER);
		this.progressBar = new JProgressBarLabel(0, maxStep, "");
		this.progressBar.setFont(Utils.fontMain);
		refreshProgressBarStep();
		getFrame().getContentPane().add(this.progressBar, BorderLayout.NORTH);
		getFrame().getContentPane().add(this.label, BorderLayout.SOUTH);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getFrame().setLocation((dim.width - 500) / 2, (dim.height - 50) / 2);
		getFrame().setPreferredSize(new Dimension(500, 50));
		getFrame().setVisible(true);
		getFrame().toFront();
		getFrame().pack();
	}

	/**
	 * Used to add a new step.
	 *
	 * @param text The text printed to indicate what step is currently processed.
	 */
	public void addStartupText(String text)
	{
		Utils.logger.log(Level.INFO, "Add startup text to " + text);
		if(this.label != null)
		{
			this.label.setText(text);
			this.step++;
			refreshProgressBarStep();
		}
	}

	/**
	 * Used to close the frame
	 */
	public void exit()
	{
		Utils.logger.log(Level.INFO, "Exitting startup frame...");
		getFrame().dispose();
	}

	public InterfaceStartup getFrame()
	{
		return this;
	}

	/**
	 * Used to refresh the current step showed by the progress bar.
	 */
	private void refreshProgressBarStep()
	{
		this.progressBar.setValue(this.step);
	}

	/**
	 * Used to reset the current step.
	 */
	public void reset()
	{
		Utils.logger.log(Level.INFO, "Resetting startup frame...");
		this.step = 0;
		this.label.setText("");
		refreshProgressBarStep();
	}

	public void setBarPercent(float percent)
	{
		if(!this.percentMode)
			this.progressBar.setMaximum(100);
		this.progressBar.setValue((int) percent);
	}

	public void setBarPercentWithText(float percent, String text)
	{
		setBarPercent(percent);
		setBarText(text);
	}

	public void setBarText(String text)
	{
		this.progressBar.setText(text, false);
	}

	public void setPercent(int i)
	{
		float percent = i / 100f;
		this.step = (int) (percent * this.progressBar.getMaximum());
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
		Utils.logger.log(Level.INFO, "Setting startup (" + step + ") text to " + text);
		if(this.label != null)
		{
			this.label.setText(text);
			this.step = step;
			refreshProgressBarStep();
		}
	}

	/**
	 * Used to skip a step.
	 */
	public void skipStep()
	{
		Utils.logger.log(Level.INFO, "Skipping startup step...");
		this.step++;
	}
}
