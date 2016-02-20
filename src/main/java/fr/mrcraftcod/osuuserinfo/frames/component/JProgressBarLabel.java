package mrcraftcod.osuuserinfo.frames.component;

import mrcraftcod.osuuserinfo.utils.Utils;
import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.concurrent.ScheduledExecutorService;

/**
 * JProgressBar with a label.
 *
 * @author MrCraftCod
 */
public class JProgressBarLabel extends JProgressBar
{
	private static final long serialVersionUID = 2211280847787361982L;
	private final String defaultText;
	private final JLabel text;
	private final NumberFormat dFormater;
	private ScheduledExecutorService reseter;
	private String message;
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
	private JProgressBarLabel(int min, int max, String defaultText, Font font)
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
