package fr.mrcraftcod.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import fr.mrcraftcod.Main;
import fr.mrcraftcod.objects.TransparentPane;
import fr.mrcraftcod.utils.Utils;

/**
 * A frame with a loading gif.
 *
 * @author MrCraftCod
 */
public class InterfaceLoading extends JDialog
{
	private static final long serialVersionUID = 6199248760028121570L;

	/**
	 * Constructor.
	 *
	 * @param parent The parent frame.
	 */
	public InterfaceLoading(JFrame parent)
	{
		super(parent);
		// setModal(true); //TODO
		setUndecorated(true);
		setContentPane(new TransparentPane(new BorderLayout()));
		setTitle(Utils.resourceBundle.getString("loading"));
		setIconImages(Utils.icons);
		setBackground(new Color(255, 255, 255, 0));
		ImageIcon icon = null;
		icon = new ImageIcon(Main.class.getClassLoader().getResource("resources/images/loading.gif"));
		JLabel label = new JLabel();
		label.setIcon(icon);
		icon.setImageObserver(label);
		getContentPane().add(label, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
		Point p = parent.getLocation();
		p.setLocation(p.getX() + parent.getSize().getWidth() / 2 - icon.getIconWidth() / 2, p.getY() + parent.getSize().getHeight() / 2 - icon.getIconHeight() / 2);
		setLocation(p);
		setVisible(true);
		pack();
		toFront();
	}
}
