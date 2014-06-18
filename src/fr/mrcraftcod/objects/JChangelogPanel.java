package fr.mrcraftcod.objects;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import fr.mrcraftcod.utils.Utils;

public class JChangelogPanel extends JPanel
{
	private static final long serialVersionUID = 4398380577611424335L;

	public JChangelogPanel(ArrayList<String> arrayList)
	{
		setLayout(new GridLayout(arrayList.size(), 1));
		for(String change : arrayList)
			this.add(processChange(change));
	}

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

	private JLabel processChange(String change)
	{
		JLabel panel = new JLabel("<html>" + change + "</html>");
		panel.setIcon(getIconForChange(change));
		return panel;
	}
}
