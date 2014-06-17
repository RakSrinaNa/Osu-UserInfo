package fr.mrcraftcod.interfaces;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
		setIconImages(Utils.icons);
		setBackground(Utils.backColor);
		getContentPane().setBackground(Utils.backColor);
		getContentPane().setLayout(new BorderLayout());
		JLabel changelogLabel = new JLabel(processText(changelog));
		changelogLabel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 1));
		changelogLabel.setBackground(Utils.backColor);
		changelogLabel.setFocusable(false);
		changelogLabel.setFont(Utils.fontMain);
		add(changelogLabel, BorderLayout.CENTER);
		Dimension d = changelogLabel.getPreferredSize();
		d.height += 50;
		d.width += 20;
		setPreferredSize(d);
		setSize(d);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);
		setVisible(true);
		toFront();
	}

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

	private String processText(String changelog)
	{
		StringBuilder sb = new StringBuilder("<html><ul>");
		final String stringPattern = "[{]{1}.+[}]{1}.*";
		Pattern pattern = Pattern.compile(stringPattern);
		Matcher matcher = pattern.matcher(changelog);
		List<String> matchList = new ArrayList<String>();
		while(matcher.find())
			matchList.add(matcher.group(0));
		for(String change : matchList)
			if(!change.equals(""))
				sb.append("<li>" + getFormatedChange(change) + "</li>");
		return sb.append("</ul></html>").toString();
	}
}
