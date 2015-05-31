package mrcraftcod.osuuserinfo.frames;

import mrcraftcod.osuuserinfo.Main;
import mrcraftcod.osuuserinfo.frames.component.TransparentPane;
import mrcraftcod.osuuserinfo.utils.Utils;
import javax.swing.*;
import java.awt.*;

/**
 * A frame with a loading gif.
 *
 * @author MrCraftCod
 */
public class LoadingFrame extends JDialog
{
	private static final long serialVersionUID = 6199248760028121570L;
	private final ImageIcon icon;

	/**
	 * Constructor.
	 *
	 * @param parent The parent frame.
	 */
	public LoadingFrame(JFrame parent)
	{
		super(parent);
		setUndecorated(true);
		setContentPane(new TransparentPane(new BorderLayout()));
		setTitle(Utils.resourceBundle.getString("loading"));
		setIconImages(Utils.icons);
		setBackground(new Color(255, 255, 255, 0));
		this.icon = new ImageIcon(Main.class.getClassLoader().getResource("images/loading.gif"));
		JLabel label = new JLabel();
		label.setIcon(this.icon);
		this.icon.setImageObserver(label);
		getContentPane().add(label, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setPreferredSize(new Dimension(this.icon.getIconWidth(), this.icon.getIconHeight()));
		Point p = parent.getLocation();
		p.setLocation(p.getX() + parent.getSize().getWidth() / 2 - this.icon.getIconWidth() / 2, p.getY() + parent.getSize().getHeight() / 2 - this.icon.getIconHeight() / 2);
		setLocation(p);
		setVisible(true);
		pack();
		toFront();
	}

	/**
	 * Used to get the icon.
	 *
	 * @return The icon.
	 */
	public ImageIcon getIcon()
	{
		return this.icon;
	}
}
