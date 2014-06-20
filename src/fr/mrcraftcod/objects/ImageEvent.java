package fr.mrcraftcod.objects;

import java.awt.Event;
import java.awt.image.BufferedImage;

public class ImageEvent extends Event
{
	private static final long serialVersionUID = -8105186001581072187L;
	private BufferedImage image;

	public ImageEvent(Object source, BufferedImage image)
	{
		super(source, 0, image);
		this.image = image;
	}

	/**
	 * @return the image
	 */
	public BufferedImage getImage()
	{
		return this.image;
	}
}
