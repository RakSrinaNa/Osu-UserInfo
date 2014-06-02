package fr.mrcraftcod.objects;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import fr.mrcraftcod.Main;

@SuppressWarnings("rawtypes")
public class ComboModeRenderer implements ListCellRenderer
{
	private ImageIcon[] icons;

	public ComboModeRenderer(ImageIcon[] icons)
	{
		this.icons = icons;
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
		list.setSelectionBackground(Color.PINK);
		JLabel label = new JLabel(value.toString());
		try
		{
			if(index == -1)
			{
				switch(value.toString())
				{
					case "osu!":
						label = new JLabel(value.toString(), icons[0], JLabel.LEFT);
					break;
					case "Taiko":
						label = new JLabel(value.toString(), icons[1], JLabel.LEFT);
					break;
					case "CTB":
						label = new JLabel(value.toString(), icons[2], JLabel.LEFT);
					break;
					case "Osu!Mania":
						label = new JLabel(value.toString(), icons[3], JLabel.LEFT);
					break;
				}
			}
			else if(icons[index] != null)
				label = new JLabel(value.toString(), icons[index], JLabel.LEFT);
		}
		catch(Exception e)
		{}
		label.setFont(Main.fontMain);
		label.setBorder(new EmptyBorder(2, 2, 2, 0));
		label.setOpaque(true);
		label.setBackground(Main.searchBarColor);
		if(list != null)
			isSelected = list.isSelectedIndex(index);
		if(list.isSelectedIndex(index))
			label.setBackground(Color.PINK);
		return label;
	}
}
