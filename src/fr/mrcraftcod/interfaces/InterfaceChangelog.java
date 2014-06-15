package fr.mrcraftcod.interfaces;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import fr.mrcraftcod.utils.Utils;

public class InterfaceChangelog extends JFrame
{
	private static final long serialVersionUID = -8709993783125141424L;

	public InterfaceChangelog(String version, String changelog)
	{
		super("Changelog");
		if(changelog == null)
		{
			dispose();
			return;
		}
		setTitle("Changelog for version " + version);
		this.setSize(400, 75);
		setIconImages(Utils.icons);
		setBackground(Utils.backColor);
		getContentPane().setBackground(Utils.backColor);
		getContentPane().setLayout(new BorderLayout());
		JLabel changelogLabel = new JLabel(processText(changelog));
		changelogLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		changelogLabel.setBackground(Utils.backColor);
		changelogLabel.setFocusable(false);
		this.add(changelogLabel, BorderLayout.CENTER);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		toFront();
	}

	private String processText(String changelog)
	{
		StringBuilder sb = new StringBuilder("<html>");
		String[] changes = changelog.split("[{]{1}\\d+[}]{1}");
		for(String change : changes)
			if(!change.equals(""))
				sb.append(change + "<br>");
		return sb.append("</html>").toString();
	}
}
