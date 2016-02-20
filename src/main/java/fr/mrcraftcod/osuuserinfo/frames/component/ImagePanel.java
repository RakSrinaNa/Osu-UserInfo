package mrcraftcod.osuuserinfo.frames.component;

import mrcraftcod.osuuserinfo.interfaces.ImageChangeListener;
import mrcraftcod.osuuserinfo.objects.ImageEvent;
import mrcraftcod.osuuserinfo.utils.Utils;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * A panel containing a buffered image.
 *
 * @author MrCraftCod
 */
public class ImagePanel extends JPanel
{
	private static final long serialVersionUID = -6952599309580686281L;
	private final List<ImageChangeListener> listeners = new ArrayList<>();
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
	 * @param printLoading Print or not the loading text
	 */
	public ImagePanel(BufferedImage image, boolean printLoading)
	{
		this.image = image;
		this.printLoading = printLoading;
	}

	public void addImageChangeListener(ImageChangeListener toAdd)
	{
		this.listeners.add(toAdd);
	}

	/**
	 * Used to know if the loading text is activated.
	 *
	 * @return True if the loading text is activated, false if not.
	 */
	private boolean isPrintLoading()
	{
		return this.printLoading;
	}

	/**
	 * Used to activate or not the loading text.
	 *
	 * @param printLoading The stats of the text.
	 */
	public void setPrintLoading(boolean printLoading)
	{
		this.printLoading = printLoading;
	}

	/**
	 * Used to modify the current printed image.
	 *
	 * @param image The BufferedImage to show.
	 */
	public void setImage(BufferedImage image)
	{
		for(ImageChangeListener l : this.listeners)
			l.onImageChanged(new ImageEvent(this, image));
		this.image = Utils.resizeBufferedImage(image, getPreferredSize());
		this.repaint();
		invalidate();
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