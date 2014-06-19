package fr.mrcraftcod.objects;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class JHoverLabel extends JLabel
{
	private static final long serialVersionUID = 2021404415046675113L;
	private String text;
	private boolean mouseEntered = false;
	private boolean underlinedWhenHovered = true;
	private boolean handCursorWhenHovered = true;
	private Color colorBeforeClick = Color.blue;
	private Color colorAfterClick = new Color(102, 14, 122);

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

	public String getLabel()
	{
		return this.text;
	}

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

	public void setColorAfterClick(Color color)
	{
		this.colorAfterClick = color;
	}

	public void setColorBeforeClick(Color color)
	{
		this.colorBeforeClick = color;
		setForeground(color);
	}

	public void setHandCursorWhenHovered(boolean flag)
	{
		this.handCursorWhenHovered = flag;
	}

	public void setUnderlinedWhenHovered(boolean flag)
	{
		this.underlinedWhenHovered = flag;
	}
}
