package mrcraftcod.osuuserinfo.frames.component;

import javax.swing.*;
import java.awt.*;

/**
 * A panel which is transparent.
 *
 * @author MrCraftCod
 */
public class TransparentPane extends JPanel
{
	private static final long serialVersionUID = -7146200793705779333L;

	/**
	 * Constructor.
	 *
	 * @param layout The LayoutManager of the panel.
	 * @see JPanel#JPanel(LayoutManager)
	 */
	public TransparentPane(LayoutManager layout)
	{
		this.setOpaque(false);
		this.setLayout(layout);
	}

	/**
	 * Used to draw the components with a transparent background.
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		g2d.setColor(getBackground());
		g2d.fill(getBounds());
		g2d.dispose();
	}
}
