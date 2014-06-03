package fr.mrcraftcod.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JButton;

public class JButtonMode extends JButton
{
	private static final long serialVersionUID = -6514627861897727157L;
	private Color disabledBackgroundColor, borderColor;
	private Icon iconMode;

	public JButtonMode(String string)
	{
		super(string);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		// super.paintComponent(g);
		Dimension d = this.getSize();
		g.setColor(this.borderColor);
		g.fill3DRect(2, 2, this.getWidth() - 2, this.getHeight() - 2, true);
		if(this.isEnabled())
			g.setColor(this.getBackground());
		else
			g.setColor(this.disabledBackgroundColor);
		g.fill3DRect(4, 4, this.getWidth() - 6, this.getHeight() - 6, true);
		g.setFont(this.getFont());
		g.setColor(this.getForeground());
		FontMetrics fm = g.getFontMetrics();
		int x = (d.width - fm.stringWidth(this.getText())) / 2;
		int y = (fm.getAscent() + (d.height - (fm.getAscent() + fm.getDescent())) / 2);
		g.drawString(this.getText(), x, y);
		iconMode.paintIcon(this, g, (int) (x - 1.2 * iconMode.getIconWidth()), y - (iconMode.getIconHeight() / 2) - 4);
	}

	public void setDisabledBackground(Color color)
	{
		disabledBackgroundColor = color;
	}

	public Icon getIconMode()
	{
		return iconMode;
	}

	public void setIconMode(Icon iconMode)
	{
		this.iconMode = iconMode;
	}

	public Color getBorderColor()
	{
		return borderColor;
	}

	public void setBorderColor(Color borderColor)
	{
		this.borderColor = borderColor;
	}
}
