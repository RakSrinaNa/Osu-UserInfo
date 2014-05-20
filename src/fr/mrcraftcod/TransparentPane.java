package fr.mrcraftcod;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import javax.swing.JPanel;

public class TransparentPane extends JPanel
{
	private static final long serialVersionUID = -7146200793705779333L;

	public TransparentPane(LayoutManager layout)
	{
		this.setOpaque(false);
		this.setLayout(layout);
	}

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
