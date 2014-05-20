package fr.mrcraftcod;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class ImagePanel extends JPanel
{
	private static final long serialVersionUID = -6952599309580686281L;
	private BufferedImage image;

	public ImagePanel(BufferedImage image)
	{
		this.image = image;
	}

	public ImagePanel()
	{}

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

	public void setImage(BufferedImage image)
	{
		this.image = image;
		this.repaint();
		this.invalidate();
	}
}