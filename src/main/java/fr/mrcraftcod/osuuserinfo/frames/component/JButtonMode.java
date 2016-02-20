package mrcraftcod.osuuserinfo.frames.component;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * The button used to select modes.
 *
 * @author MrCraftCod
 */
public class JButtonMode extends JButton
{
	private static final long serialVersionUID = -6514627861897727157L;
	private final int borderSize;
	private Color disabledBackgroundColor, disabledTextColor, borderColor;
	private BufferedImage iconMode, unselectedIconMode, unselectedIconModeDark;
	private boolean selected;

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

	@Override
	public boolean isSelected()
	{
		return this.selected;
	}

	@Override
	public void setSelected(boolean selected)
	{
		this.selected = selected;
		invalidate();
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
		g2.setColor(processEnabledColor(this.borderColor));
		int borderOffset = 3;
		int roundedFactor = 10;
		g2.fillRoundRect(borderOffset, borderOffset, getWidth() - borderOffset, getHeight() - borderOffset, roundedFactor, roundedFactor);
		if(isSelected())
			g2.setColor(processEnabledColor(getBackground()));
		else
			g2.setColor(processEnabledColor(this.disabledBackgroundColor));
		g2.fillRoundRect(borderOffset + this.borderSize, borderOffset + this.borderSize, getWidth() - (borderOffset + 2 * this.borderSize), getHeight() - (borderOffset + 2 * this.borderSize), roundedFactor, roundedFactor);
		if(isSelected())
		{
			g2.setFont(getFont());
			g2.setColor(processEnabledColor(getForeground()));
		}
		else
		{
			g2.setFont(getFont().deriveFont(Font.BOLD));
			g2.setColor(processEnabledColor(this.disabledTextColor));
		}
		FontMetrics fm = g2.getFontMetrics();
		int x = (d.width - fm.stringWidth(getText()) + this.iconMode.getWidth()) / 2;
		int y = fm.getAscent() + (d.height - (fm.getAscent() + fm.getDescent())) / 2;
		g2.drawString(getText(), x, y);
		if(isSelected())
			g2.drawImage(this.iconMode, (int) (x - 1.2 * this.iconMode.getWidth()), y - this.iconMode.getHeight() / 2 - 4, null);
		else
			g2.drawImage(processUnselectedImage(), (int) (x - 1.2 * this.iconMode.getWidth()), y - this.iconMode.getHeight() / 2 - 4, null);
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
	public void setIconMode(BufferedImage iconMode)
	{
		this.iconMode = iconMode;
	}

	/**
	 * Used to set the unselected icon.
	 *
	 * @param unselectedIconMode The unselected icon.
	 */
	public void setUnselectedIconMode(BufferedImage unselectedIconMode)
	{
		this.unselectedIconMode = unselectedIconMode;
		this.unselectedIconModeDark = getDarkerImage(unselectedIconMode);
	}

	private BufferedImage copyBufferedImage(BufferedImage img)
	{
		ColorModel colorModel = img.getColorModel();
		boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
		WritableRaster raster = img.copyData(null);
		return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
	}

	private BufferedImage getDarkerImage(BufferedImage unselectedIconMode)
	{
		int shift = 100;
		return shiftColor(copyBufferedImage(unselectedIconMode), shift, shift, shift);
	}

	private Color processEnabledColor(Color color)
	{
		if(isEnabled())
			return color;
		return color.darker();
	}

	private Image processUnselectedImage()
	{
		if(isEnabled())
			return this.unselectedIconMode;
		return this.unselectedIconModeDark;
	}

	private BufferedImage shiftColor(BufferedImage img, int rShift, int gShift, int bShift)
	{
		int width = img.getWidth();
		int height = img.getHeight();
		for(int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
			{
				int c = img.getRGB(j, i);
				int alpha = c >> 24 & 0xff;
				int red = (c >> 16 & 0xff) - rShift;
				int green = (c >> 8 & 0xff) - gShift;
				int blue = (c & 0xff) - bShift;
				red = red > 0 ? red : 0;
				green = green > 0 ? green : 0;
				blue = blue > 0 ? blue : 0;
				img.setRGB(j, i, new Color(red, green, blue, alpha).getRGB());
			}
		return img;
	}
}
