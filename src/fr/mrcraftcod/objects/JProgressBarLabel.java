package fr.mrcraftcod.objects;

import java.awt.BorderLayout;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import fr.mrcraftcod.utils.Utils;

public class JProgressBarLabel extends JProgressBar
{
	private static final long serialVersionUID = 2211280847787361982L;
	private ScheduledExecutorService reseter;
	private String defaultText;
	private JLabel text;
	private String message;
	private NumberFormat dFormater;
	private boolean canReset;

	public JProgressBarLabel(int min, int max, String defaultText)
	{
		this(min, max, defaultText, null);
	}

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

	@Override
	public void setValue(int n)
	{
		super.setValue(n);
		updateText();
	}

	private void updateText()
	{
		if(this.message != null && !this.message.equals(""))
			this.text.setText(this.dFormater.format(getValue() * 100f / getMaximum()) + "% - " + this.message);
		else
			this.text.setText(this.dFormater.format(getValue() * 100f / getMaximum()) + "%");
		this.text.repaint();
	}
}
