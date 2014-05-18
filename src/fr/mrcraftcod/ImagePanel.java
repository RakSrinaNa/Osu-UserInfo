package fr.mrcraftcod;

import java.awt.Color;
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
	{
		this.setBackground(Color.RED);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	public void setImage(BufferedImage image)
	{
		this.image = image;
		this.repaint();
		this.invalidate();
	}
}