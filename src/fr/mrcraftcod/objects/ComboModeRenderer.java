package fr.mrcraftcod.objects;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
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
		JLabel label = new JLabel();
		label.setOpaque(true);
		label.setBackground(Main.searchBarColor);
		if(list != null)
			isSelected = list.isSelectedIndex(index);
		if(list.isSelectedIndex(index))
			label.setBackground(Color.PINK);
		try
		{
			if(index == -1)
			{
				String text = value.toString();
				label.setText(text);
				switch(text)
				{
					case "osu!":
						label.setIcon(icons[0]);
					break;
					case "Taiko":
						label.setIcon(icons[1]);
					break;
					case "CTB":
						label.setIcon(icons[2]);
					break;
					case "Osu!Mania":
						label.setIcon(icons[3]);
					break;
				}
			}
			else if(icons[index] != null)
			{
				label.setText(value.toString());
				label.setIcon(icons[index]);
			}
		}
		catch(Exception e)
		{}
		return label;
	}
}
