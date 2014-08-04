package fr.mrcraftcod.frames.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

/**
 * Used to create a Label as a link.
 *
 * @author MrCraftCod
 */
public class JHoverLabel extends JLabel
{
	private static final long serialVersionUID = 2021404415046675113L;
	private String text;
	private boolean mouseEntered = false;
	private boolean underlinedWhenHovered = true;
	private boolean handCursorWhenHovered = true;
	private Color colorBeforeClick = Color.blue;
	private Color colorAfterClick = new Color(102, 14, 122);

	/**
	 * Constructor.
	 *
	 * @param text The text to print.
	 *
	 * @see JLabel#JLabel(String)
	 */
	public JHoverLabel(final String text)
	{
		super();
		this.text = text;
		setText(text);
		setForeground(this.colorBeforeClick);
		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				setForeground(JHoverLabel.this.colorAfterClick);
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				setText(text);
				JHoverLabel.this.mouseEntered = true;
				repaint();
				if(JHoverLabel.this.handCursorWhenHovered)
					setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				setText(text);
				JHoverLabel.this.mouseEntered = false;
				repaint();
				if(JHoverLabel.this.handCursorWhenHovered)
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * Used to get the printed text.
	 *
	 * @return The printed text.
	 */
	@Override
	public String getText()
	{
		return this.text;
	}

	/**
	 * Used to draw component.
	 *
	 * @see JLabel#paint(Graphics)
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		if(this.underlinedWhenHovered && this.mouseEntered)
		{
			Rectangle r = g.getClipBounds();
			g.drawLine(0, r.height - getFontMetrics(getFont()).getDescent(), getFontMetrics(getFont()).stringWidth(getText()), r.height - getFontMetrics(getFont()).getDescent());
		}
	}

	/**
	 * Used to set the colour when the text has been clicked.
	 *
	 * @param color The colour to set.
	 */
	public void setColorAfterClick(Color color)
	{
		this.colorAfterClick = color;
	}

	/**
	 * Used to set the colour when the text hasn't been clicked.
	 *
	 * @param color The colour to set.
	 */
	public void setColorBeforeClick(Color color)
	{
		this.colorBeforeClick = color;
		setForeground(color);
	}

	/**
	 * Used to activate or not the hand when mouse is on the text.
	 *
	 * @param flag The status of this function.
	 */
	public void setHandCursorWhenHovered(boolean flag)
	{
		this.handCursorWhenHovered = flag;
	}

	/**
	 * Used to activate or not the underline of the text when mouse is on it.
	 *
	 * @param flag The status of this function.
	 */
	public void setUnderlinedWhenHovered(boolean flag)
	{
		this.underlinedWhenHovered = flag;
	}
}
