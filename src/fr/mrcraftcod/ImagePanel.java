package fr.mrcraftcod;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * A panel containing a buffered image
 * 
 * @author MrCraftCod
 */
public class ImagePanel extends JPanel
{
	private static final long serialVersionUID = -6952599309580686281L;
	private BufferedImage image;

	/**
	 * Constructor.
	 * 
	 * @param image The BufferedImage to show.
	 */
	public ImagePanel(BufferedImage image)
	{
		this.image = image;
	}

	/**
	 * Constructor.
	 * Will show nothing.
	 */
	public ImagePanel()
	{}

	/**
	 * Paint the image if there is one, if not this will print the "loading" string found in the ResourceBundle
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(image == null)
		{
			String string = Main.resourceBundle.getString("loading");
			g.drawString(string, (this.getWidth() / 2) - (g.getFontMetrics().stringWidth(string) / 2), this.getHeight() / 2);
			return;
		}
		int baseY = (this.getHeight() - image.getHeight()) / 2, baseX = (this.getWidth() - image.getWidth()) / 2;
		g.drawImage(image, baseX, baseY, null);
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
		this.invalidate();
	}
}