package fr.mrcraftcod.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.JButton;

public class JButtonMode extends JButton
{
	private static final long serialVersionUID = -6514627861897727157L;
	private Color disabledBackgroundColor, disabledTextColor, borderColor;
	private Icon iconMode, unselectedIconMode;
	private int borderSize;
	private int roundedFactor = 10, borderOffset = 3;

	public JButtonMode(String string)
	{
		super(string);
		Dimension dim = this.getPreferredSize();
		dim.setSize(dim.getWidth(), dim.getHeight() + 3);
		this.setSize(dim);
		this.setMinimumSize(dim);
		this.setPreferredSize(dim);
		this.setMaximumSize(dim);
		borderSize = 2;
	}

	public Color getBorderColor()
	{
		return borderColor;
	}

	public Color getDisabledTextColor()
	{
		return disabledTextColor;
	}

	public Icon getIconMode()
	{
		return iconMode;
	}

	public Icon getUnselectedIconMode()
	{
		return unselectedIconMode;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g.create();
		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHints(qualityHints);
		Dimension d = this.getSize();
		g2.setColor(this.borderColor);
		g2.fillRoundRect(borderOffset, borderOffset, this.getWidth() - borderOffset, this.getHeight() - borderOffset, roundedFactor, roundedFactor);
		if(this.isEnabled())
			g2.setColor(this.getBackground());
		else
			g2.setColor(this.disabledBackgroundColor);
		g2.fillRoundRect(borderOffset + borderSize, borderOffset + borderSize, this.getWidth() - (borderOffset + 2 * borderSize), this.getHeight() - (borderOffset + 2 * borderSize), roundedFactor, roundedFactor);
		if(this.isEnabled())
		{
			g2.setFont(this.getFont());
			g2.setColor(this.getForeground());
		}
		else
		{
			g2.setFont(this.getFont().deriveFont(Font.BOLD));
			g2.setColor(this.disabledTextColor);
		}
		FontMetrics fm = g2.getFontMetrics();
		int x = (d.width - fm.stringWidth(this.getText()) + iconMode.getIconWidth()) / 2;
		int y = (fm.getAscent() + (d.height - (fm.getAscent() + fm.getDescent())) / 2);
		g2.drawString(this.getText(), x, y);
		if(this.isEnabled())
			iconMode.paintIcon(this, g2, (int) (x - 1.2 * iconMode.getIconWidth()), y - (iconMode.getIconHeight() / 2) - 4);
		else
			unselectedIconMode.paintIcon(this, g2, (int) (x - 1.2 * iconMode.getIconWidth()), y - (iconMode.getIconHeight() / 2) - 4);
	}

	public void setBorderColor(Color borderColor)
	{
		this.borderColor = borderColor;
	}

	public void setDisabledBackground(Color color)
	{
		disabledBackgroundColor = color;
	}

	public void setDisabledTextColor(Color disabledTextColor)
	{
		this.disabledTextColor = disabledTextColor;
	}

	public void setIconMode(Icon iconMode)
	{
		this.iconMode = iconMode;
	}

	public void setUnselectedIconMode(Icon unselectedIconMode)
	{
		this.unselectedIconMode = unselectedIconMode;
	}
}
