package fr.mrcraftcod.objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import fr.mrcraftcod.utils.Utils;

/**
 * A panel containing a buffered image
 *
 * @author MrCraftCod
 */
public class ImagePanel extends JPanel
{
	private static final long serialVersionUID = -6952599309580686281L;
	private BufferedImage image;
	private boolean printLoading;

	/**
	 * Constructor.
	 * Will show nothing.
	 */
	public ImagePanel()
	{
		this(null, false);
	}

	/**
	 * Constructor.
	 *
	 * @param printLoading Print or not the loading text
	 */
	public ImagePanel(boolean printLoading)
	{
		this(null, printLoading);
	}

	/**
	 * Constructor.
	 *
	 * @param image The BufferedImage to show.
	 */
	public ImagePanel(BufferedImage image)
	{
		this(image, false);
	}

	/**
	 * Constructor.
	 *
	 * @param image The BufferedImage to show.
	 * @param printLoading Print or not the loading text
	 */
	public ImagePanel(BufferedImage image, boolean printLoading)
	{
		this.image = image;
		this.printLoading = printLoading;
	}

	public boolean isPrintLoading()
	{
		return this.printLoading;
	}

	/**
	 * Used to modify the current printed image.
	 *
	 * @param image The BufferedImage to show.
	 */
	public void setImage(BufferedImage image)
	{
		this.image = image;
		this.repaint();
		invalidate();
	}

	public void setPrintLoading(boolean printLoading)
	{
		this.printLoading = printLoading;
	}

	/**
	 * Paint the image if there is one, if not this will print the "loading" string found in the ResourceBundle
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(this.image == null)
		{
			if(!isPrintLoading())
				return;
			String string = Utils.resourceBundle.getString("loading");
			g.drawString(string, getWidth() / 2 - g.getFontMetrics().stringWidth(string) / 2, getHeight() / 2);
			return;
		}
		int baseY = (getHeight() - this.image.getHeight()) / 2, baseX = (getWidth() - this.image.getWidth()) / 2;
		g.drawImage(this.image, baseX, baseY, null);
	}
}