package fr.mrcraftcod.osuuserinfo.frames.component;

import fr.mrcraftcod.osuuserinfo.utils.Utils;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel for the changelog. Show image difference with text.
 *
 * @author MrCraftCod
 */
public class JChangelogPanel extends JPanel
{
	private static final long serialVersionUID = 4398380577611424335L;

	/**
	 * Constructor.
	 *
	 * @param changes Difference texts.
	 */
	public JChangelogPanel(ArrayList<String> changes)
	{
		setLayout(new GridLayout(changes.size(), 1));
		for(String change : changes)
			this.add(processChange(change));
	}

	/**
	 * Used to get the icon of a difference.
	 *
	 * @param change The difference text.
	 * @return The corresponding icon.
	 */
	private Icon getIconForChange(String change)
	{
		if(change.contains("color=\"green\""))
			return Utils.iconChangelogAdd;
		if(change.contains("color=\"red\""))
			return Utils.iconChangelogRemove;
		if(change.contains("color=\"orange\""))
			return Utils.iconChangelogModify;
		return null;
	}

	/**
	 * Used to process a changes. Add the image with the text.
	 *
	 * @param change The change to process.
	 * @return A Label with the icon and text.
	 */
	private JLabel processChange(String change)
	{
		JLabel panel = new JLabel("<html>" + change + "</html>");
		panel.setIcon(getIconForChange(change));
		return panel;
	}
}
