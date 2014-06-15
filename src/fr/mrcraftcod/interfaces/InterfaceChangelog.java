package fr.mrcraftcod.interfaces;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import fr.mrcraftcod.utils.Utils;

public class InterfaceChangelog extends JFrame
{
	private static final long serialVersionUID = -8709993783125141424L;

	public InterfaceChangelog(String changelog)
	{
		super("Changelog");
		setTitle("Changelog");
		this.setSize(400, 75);
		setIconImages(Utils.icons);
		setBackground(Utils.backColor);
		getContentPane().setBackground(Utils.backColor);
		getContentPane().setLayout(new BorderLayout());
		JTextArea messageLabel = new JTextArea(changelog);
		messageLabel.setBackground(Utils.backColor);
		messageLabel.setEditable(false);
		messageLabel.setBorder(null);
		messageLabel.setLineWrap(true);
		messageLabel.setWrapStyleWord(true);
		messageLabel.setFocusable(false);
		this.add(messageLabel, BorderLayout.CENTER);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
		toFront();
	}
}
