package fr.mrcraftcod.osuuserinfo.frames;

import fr.mrcraftcod.osuuserinfo.Main;
import fr.mrcraftcod.osuuserinfo.frames.component.JProgressBarLabel;
import fr.mrcraftcod.osuuserinfo.frames.component.TransparentPane;
import fr.mrcraftcod.osuuserinfo.utils.Utils;
import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;

/**
 * Used to show a startup frame with a progress bar.
 *
 * @author MrCraftCod
 */
public class StartupFrame extends JFrame
{
	private static final long serialVersionUID = -5316219219270463412L;
	private final JLabel label;
	private final JProgressBarLabel progressBar;
	private int step = 0;

	/**
	 * Constructor.
	 *
	 * @param maxStep How many steps are in the startup.
	 */
	public StartupFrame(int maxStep)
	{
		super();
		boolean percentMode = maxStep == 100;
		Utils.logger.log(Level.INFO, "Creating startup frame...");
		setUndecorated(true);
		setContentPane(new TransparentPane(new BorderLayout()));
		getContentPane().setBackground(Color.BLACK);
		setTitle("Starting " + Main.APPNAME);
		setIconImages(Utils.icons);
		setBackground(new Color(0, 255, 0, 0));
		this.label = new JLabel();
		this.label.setFont(Utils.fontMain);
		this.label.setForeground(new Color(255, 255, 255));
		this.label.setHorizontalAlignment(JLabel.CENTER);
		this.label.setVerticalAlignment(JLabel.CENTER);
		this.progressBar = new JProgressBarLabel(0, maxStep, "");
		this.progressBar.setFont(Utils.fontMain);
		refreshProgressBarStep();
		getContentPane().add(this.progressBar, BorderLayout.NORTH);
		getContentPane().add(this.label, BorderLayout.SOUTH);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocation((dim.width - 500) / 2, (dim.height - 50) / 2);
		setPreferredSize(new Dimension(500, 50));
		setVisible(true);
		toFront();
		pack();
	}

	/**
	 * Used to close the frame
	 */
	public void exit()
	{
		Utils.logger.log(Level.INFO, "Exitting startup frame...");
		dispose();
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
	 * Used to refresh the current step showed by the progress bar.
	 */
	private void refreshProgressBarStep()
	{
		this.progressBar.setValue(this.step);
	}
}
