package fr.mrcraftcod.osuuserinfo.frames;

import fr.mrcraftcod.osuuserinfo.Main;
import fr.mrcraftcod.osuuserinfo.frames.component.JChangelogPanel;
import fr.mrcraftcod.osuuserinfo.frames.component.SortedComboBoxModel;
import fr.mrcraftcod.osuuserinfo.utils.Updater;
import fr.mrcraftcod.osuuserinfo.utils.Utils;
import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangelogFrame extends JDialog
{
	private static final long serialVersionUID = -8709993783125141424L;
	private LinkedHashMap<String, JChangelogPanel> panels;
	private JComboBox<String> versionSelection;

	public ChangelogFrame(JFrame parent, LinkedHashMap<String, String> changelog)
	{
		super(parent);
		if(changelog == null)
		{
			dispose();
			return;
		}
		initFrame(parent, changelog);
	}

	public ChangelogFrame(JFrame parent, String version, String changelog)
	{
		super(parent);
		if(changelog == null)
		{
			dispose();
			return;
		}
		LinkedHashMap<String, String> changes = new LinkedHashMap<>();
		changes.put(version, changelog);
		initFrame(parent, changes);
	}

	private JChangelogPanel createChangePanel(ArrayList<String> list)
	{
		JChangelogPanel panel = new JChangelogPanel(list);
		panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		panel.setFocusable(false);
		panel.setFont(Utils.fontMain);
		return panel;
	}

	private String getFormatedChange(String change)
	{
		StringBuilder sb = new StringBuilder("<font color=\"");
		final String typePattern = "[{].+[}]";
		Pattern pattern = Pattern.compile(typePattern);
		Matcher matcher = pattern.matcher(change);
		List<String> matchList = new ArrayList<>();
		while(matcher.find())
			matchList.add(matcher.group(0));
		if(matchList.size() < 1)
			return change;
		if(matchList.get(0).contains("+"))
			sb.append("green");
		else if(matchList.get(0).contains("-"))
			sb.append("red");
		else if(matchList.get(0).contains("*") || matchList.get(0).contains("."))
			sb.append("orange");
		else if(matchList.get(0).contains("/"))
			sb.append("yellow");
		else if(matchList.get(0).contains("$"))
			sb.append("blue");
		else
			sb.append("black");
		return sb.append("\">").append(change.replaceFirst(typePattern, "")).append("</font>").toString();
	}

	private void initFrame(JFrame parent, LinkedHashMap<String, String> changelog)
	{
		this.panels = processTexts(changelog);
		setIconImages(Utils.icons);
		setTitle("Changelog");
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		SortedComboBoxModel<String> model = new SortedComboBoxModel<>(this.panels.keySet().toArray(new String[this.panels.size()]), (o1, o2) -> Updater.isVersionGreater(o1.toString(), o2.toString()));
		this.versionSelection = new JComboBox<>(model);
		this.versionSelection.addActionListener(e -> updateVersion());
		this.versionSelection.setSelectedItem(Main.VERSION);
		setPanelChange(Main.VERSION);
		setLocationRelativeTo(parent);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);
		pack();
		setVisible(true);
		toFront();
	}

	private LinkedHashMap<String, JChangelogPanel> processTexts(LinkedHashMap<String, String> changelog)
	{
		LinkedHashMap<String, JChangelogPanel> changes = new LinkedHashMap<>();
		LinkedHashMap<String, ArrayList<String>> changesTexts = Utils.getChangelogVersion(changelog);
		for(String version : changesTexts.keySet())
		{
			ArrayList<String> modifs = new ArrayList<>();
			for(String change : changesTexts.get(version))
				modifs.add(getFormatedChange(change));
			changes.put(version, createChangePanel(modifs));
		}
		return changes;
	}

	private void setPanelChange(String version)
	{
		getContentPane().removeAll();
		setTitle(MessageFormat.format(Utils.resourceBundle.getString("changelog_for"), version));
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

	private void updateVersion()
	{
		setPanelChange(this.versionSelection.getSelectedItem().toString());
	}
}
