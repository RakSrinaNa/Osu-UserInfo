package fr.mrcraftcod.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import fr.mrcraftcod.Main;
import fr.mrcraftcod.objects.JChangelogPanel;
import fr.mrcraftcod.utils.Utils;

/**
 * Interface to show changelogs.
 *
 * @author MrCraftCod
 */
public class InterfaceChangelog extends JFrame
{
	private static final long serialVersionUID = -8709993783125141424L;
	private LinkedHashMap<String, JChangelogPanel> panels;
	private JComboBox<String> versionSelection;

	/**
	 * Constructor.
	 *
	 * @param changelog The changelogs.
	 */
	public InterfaceChangelog(LinkedHashMap<String, String> changelog)
	{
		super("Changelog");
		if(changelog == null)
		{
			dispose();
			return;
		}
		initFrame(changelog);
	}

	/**
	 * Constructor.
	 *
	 * @param version The version of the changelog.
	 * @param changelog The changelog.
	 */
	public InterfaceChangelog(String version, String changelog)
	{
		super("Changelog");
		if(changelog == null)
		{
			dispose();
			return;
		}
		LinkedHashMap<String, String> changes = new LinkedHashMap<String, String>();
		changes.put(version, changelog);
		initFrame(changes);
	}

	/**
	 * Used to create a JChangelogPanel with a list of changes.
	 *
	 * @param list The changes in this panel.
	 * @return The JChangelogPanel with the changes.
	 */
	private JChangelogPanel createChangePanel(ArrayList<String> list)
	{
		JChangelogPanel panel = new JChangelogPanel(list);
		panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		panel.setBackground(Utils.backColor);
		panel.setFocusable(false);
		panel.setFont(Utils.fontMain);
		return panel;
	}

	/**
	 * Used to get a change formatted (with colour and without the {} indicator).
	 *
	 * @param change The change to format.
	 * @return The formatted change.
	 */
	private String getFormatedChange(String change)
	{
		StringBuilder sb = new StringBuilder("<font color=\"");
		final String typePattern = "[{]{1}.+[}]{1}";
		Pattern pattern = Pattern.compile(typePattern);
		Matcher matcher = pattern.matcher(change);
		List<String> matchList = new ArrayList<String>();
		while(matcher.find())
			matchList.add(matcher.group(0));
		if(matchList.size() < 1)
			return change;
		if(matchList.get(0).contains("+"))
			sb.append("green");
		else if(matchList.get(0).contains("-"))
			sb.append("red");
		else if(matchList.get(0).contains("*"))
			sb.append("orange");
		else
			sb.append("black");
		return sb.append("\">").append(change.replaceFirst(typePattern, "")).append("</font>").toString();
	}

	/**
	 * Used to initialise the frame.
	 *
	 * @param changelog The changelogs of the frame.
	 */
	private void initFrame(LinkedHashMap<String, String> changelog)
	{
		this.panels = processTexts(changelog);
		setIconImages(Utils.icons);
		setBackground(Utils.backColor);
		getContentPane().setBackground(Utils.backColor);
		getContentPane().setLayout(new BorderLayout());
		this.versionSelection = new JComboBox<String>(this.panels.keySet().toArray(new String[this.panels.size()]));
		this.versionSelection.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				updateVersion();
			}
		});
		this.versionSelection.setSelectedItem(Main.VERSION);
		setPanelChange(Main.VERSION);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);
		setVisible(true);
		toFront();
	}

	/**
	 * Used to create changelog panels.
	 *
	 * @param changelog The changelogs.
	 * @return The JChangelogPanels
	 */
	private LinkedHashMap<String, JChangelogPanel> processTexts(LinkedHashMap<String, String> changelog)
	{
		LinkedHashMap<String, JChangelogPanel> changes = new LinkedHashMap<String, JChangelogPanel>();
		for(String version : changelog.keySet())
		{
			ArrayList<String> list = new ArrayList<String>();
			final String stringPattern = "[{]{1}.+[}]{1}.*";
			Pattern pattern = Pattern.compile(stringPattern);
			Matcher matcher = pattern.matcher(changelog.get(version));
			List<String> matchList = new ArrayList<String>();
			while(matcher.find())
				matchList.add(matcher.group(0));
			for(String change : matchList)
				if(!change.equals(""))
					list.add(getFormatedChange(change));
			changes.put(version, createChangePanel(list));
		}
		return changes;
	}

	/**
	 * Used to set a new changelog.
	 *
	 * @param version The version of the changelog to show.
	 */
	private void setPanelChange(String version)
	{
		getContentPane().removeAll();
		setTitle(String.format(Utils.resourceBundle.getString("changelog_for"), version));
		JChangelogPanel panel = this.panels.containsKey(version) ? this.panels.get(version) : this.panels.get(this.panels.keySet().iterator().next());
		getContentPane().add(panel, BorderLayout.CENTER);
		if(this.panels.size() > 1)
			getContentPane().add(this.versionSelection, BorderLayout.SOUTH);
		Dimension d = panel.getPreferredSize();
		d.height += 50 + this.versionSelection.getPreferredSize().height;
		d.width += 20;
		setPreferredSize(d);
		setSize(d);
	}

	/**
	 * Used to update the printed changelog.
	 */
	protected void updateVersion()
	{
		setPanelChange(this.versionSelection.getSelectedItem().toString());
	}
}
