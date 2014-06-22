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
import javax.swing.JComponent;

/**
 * The button used to select modes.
 *
 * @author MrCraftCod
 */
public class JButtonMode extends JButton
{
	private static final long serialVersionUID = -6514627861897727157L;
	private Color disabledBackgroundColor, disabledTextColor, borderColor;
	private Icon iconMode, unselectedIconMode;
	private int borderSize;
	private int roundedFactor = 10, borderOffset = 3;

	/**
	 * Constructor.
	 *
	 * @param text The text printed on the button.
	 */
	public JButtonMode(String text)
	{
		super(text);
		Dimension dim = getPreferredSize();
		dim.setSize(dim.getWidth(), dim.getHeight() + 3);
		this.setSize(dim);
		setMinimumSize(dim);
		setPreferredSize(dim);
		setMaximumSize(dim);
		this.borderSize = 2;
	}

	/**
	 * Used to get the border colour.
	 *
	 * @return The border colour.
	 */
	public Color getBorderColor()
	{
		return this.borderColor;
	}

	/**
	 * Used to get the disabled text colour.
	 *
	 * @return The disabled text colour.
	 */
	public Color getDisabledTextColor()
	{
		return this.disabledTextColor;
	}

	/**
	 * Used to get the icon.
	 *
	 * @return The icon.
	 */
	public Icon getIconMode()
	{
		return this.iconMode;
	}

	/**
	 * Used to get the unselected icon.
	 *
	 * @return The unselected icon.
	 */
	public Icon getUnselectedIconMode()
	{
		return this.unselectedIconMode;
	}

	/**
	 * Draw the button.
	 *
	 * @see JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g.create();
		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHints(qualityHints);
		Dimension d = this.getSize();
		g2.setColor(this.borderColor);
		g2.fillRoundRect(this.borderOffset, this.borderOffset, getWidth() - this.borderOffset, getHeight() - this.borderOffset, this.roundedFactor, this.roundedFactor);
		if(isEnabled())
			g2.setColor(getBackground());
		else
			g2.setColor(this.disabledBackgroundColor);
		g2.fillRoundRect(this.borderOffset + this.borderSize, this.borderOffset + this.borderSize, getWidth() - (this.borderOffset + 2 * this.borderSize), getHeight() - (this.borderOffset + 2 * this.borderSize), this.roundedFactor, this.roundedFactor);
		if(isEnabled())
		{
			g2.setFont(getFont());
			g2.setColor(getForeground());
		}
		else
		{
			g2.setFont(getFont().deriveFont(Font.BOLD));
			g2.setColor(this.disabledTextColor);
		}
		FontMetrics fm = g2.getFontMetrics();
		int x = (d.width - fm.stringWidth(getText()) + this.iconMode.getIconWidth()) / 2;
		int y = fm.getAscent() + (d.height - (fm.getAscent() + fm.getDescent())) / 2;
		g2.drawString(getText(), x, y);
		if(isEnabled())
			this.iconMode.paintIcon(this, g2, (int) (x - 1.2 * this.iconMode.getIconWidth()), y - this.iconMode.getIconHeight() / 2 - 4);
		else
			this.unselectedIconMode.paintIcon(this, g2, (int) (x - 1.2 * this.iconMode.getIconWidth()), y - this.iconMode.getIconHeight() / 2 - 4);
	}

	/**
	 * Used to set the border colour.
	 *
	 * @param borderColor The border colour.
	 */
	public void setBorderColor(Color borderColor)
	{
		this.borderColor = borderColor;
	}

	/**
	 * Used to set the disabled background colour.
	 *
	 * @param color The disabled background colour.
	 */
	public void setDisabledBackground(Color color)
	{
		this.disabledBackgroundColor = color;
	}

	/**
	 * Used to set the disabled text colour.
	 *
	 * @param disabledTextColor The disabled text colour.
	 */
	public void setDisabledTextColor(Color disabledTextColor)
	{
		this.disabledTextColor = disabledTextColor;
	}

	/**
	 * Used to set the icon.
	 *
	 * @param iconMode The icon.
	 */
	public void setIconMode(Icon iconMode)
	{
		this.iconMode = iconMode;
	}

	/**
	 * Used to set the unselected icon.
	 *
	 * @param unselectedIconMode The unselected icon.
	 */
	public void setUnselectedIconMode(Icon unselectedIconMode)
	{
		this.unselectedIconMode = unselectedIconMode;
	}
}
