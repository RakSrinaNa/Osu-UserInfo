package fr.mrcraftcod.osuuserinfo.frames.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Used to create a Label as a link.
 *
 * @author MrCraftCod
 */
public class JHoverLabel extends JLabel
{
	private static final long serialVersionUID = 2021404415046675113L;
	private final String text;
	private final boolean handCursorWhenHovered = true;
	private final Color colorAfterClick = new Color(102, 14, 122);
	private boolean mouseEntered = false;

	/**
	 * Constructor.
	 *
	 * @param text The text to print.
	 * @see JLabel#JLabel(String)
	 */
	public JHoverLabel(final String text)
	{
		super();
		this.text = text;
		setText(text);
		Color colorBeforeClick = Color.blue;
		setForeground(colorBeforeClick);
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
		boolean underlinedWhenHovered = true;
		if(underlinedWhenHovered && this.mouseEntered)
		{
			Rectangle r = g.getClipBounds();
			g.drawLine(0, r.height - getFontMetrics(getFont()).getDescent(), getFontMetrics(getFont()).stringWidth(getText()), r.height - getFontMetrics(getFont()).getDescent());
		}
	}
}
