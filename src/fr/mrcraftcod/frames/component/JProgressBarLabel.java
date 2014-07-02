package fr.mrcraftcod.frames.component;

import java.awt.BorderLayout;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import fr.mrcraftcod.utils.Utils;

/**
 * JProgressBar with a label.
 *
 * @author MrCraftCod
 */
public class JProgressBarLabel extends JProgressBar
{
	private static final long serialVersionUID = 2211280847787361982L;
	private ScheduledExecutorService reseter;
	private String defaultText;
	private JLabel text;
	private String message;
	private NumberFormat dFormater;
	private boolean canReset;

	/**
	 * Constructor.
	 *
	 * @param min Minimum value.
	 * @param max Maximum value.
	 * @param defaultText The default text.
	 */
	public JProgressBarLabel(int min, int max, String defaultText)
	{
		this(min, max, defaultText, null);
	}

	/**
	 * Constructor.
	 *
	 * @param min Minimum value.
	 * @param max Maximum value.
	 * @param defaultText The default text.
	 * @param font The font to use.
	 */
	public JProgressBarLabel(int min, int max, String defaultText, Font font)
	{
		super(min, max);
		setLayout(new BorderLayout());
		this.defaultText = defaultText;
		this.text = new JLabel(defaultText);
		this.text.setHorizontalAlignment(JLabel.CENTER);
		this.text.setVerticalAlignment(JLabel.CENTER);
		if(font != null)
			this.text.setFont(font);
		this.add(this.text, BorderLayout.CENTER);
		this.canReset = false;
		this.dFormater = NumberFormat.getInstance(Utils.locale);
		this.dFormater.setMaximumFractionDigits(2);
	}

	/**
	 * Used to set the label text.
	 *
	 * @param text The text to set.
	 * @param canReset True if the text doesn't disappear after a short time.
	 */
	public void setText(String text, boolean canReset)
	{
		if(this.reseter != null)
			this.reseter.shutdownNow();
		this.canReset = canReset;
		this.message = text;
		updateText();
		if(this.canReset)
		{
			this.reseter = Executors.newSingleThreadScheduledExecutor();
			this.reseter.schedule(new Runnable()
			{
				@Override
				public void run()
				{
					setText(JProgressBarLabel.this.defaultText, false);
					setValue(0);
				}
			}, 7500, TimeUnit.MILLISECONDS);
		}
	}

	/**
	 * Used to set the progress bar value.
	 *
	 * @see JProgressBar#setValue(int)
	 */
	@Override
	public void setValue(int n)
	{
		super.setValue(n);
		updateText();
	}

	/**
	 * Update the printed text.
	 */
	private void updateText()
	{
		if(this.message != null && !this.message.equals(""))
			this.text.setText(this.dFormater.format(getValue() * 100f / getMaximum()) + "% - " + this.message);
		else
			this.text.setText(this.dFormater.format(getValue() * 100f / getMaximum()) + "%");
		this.text.repaint();
	}
}
