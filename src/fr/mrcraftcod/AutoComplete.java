package fr.mrcraftcod;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;

public class AutoComplete extends JComboBox<String> implements JComboBox.KeySelectionManager
{
	private static final long serialVersionUID = -4970677889779292974L;
	JTextComponent textComponent;

	public AutoComplete(DefaultComboBoxModel<String> model)
	{
		super(model);
		textComponent = (JTextComponent) this.getEditor().getEditorComponent();
	}

	public ArrayList<String> getSortedElements()
	{
		ArrayList<String> list = new ArrayList<String>();
		ComboBoxModel<String> elements = this.getModel();
		for(int i = 0; i < elements.getSize(); i++)
			list.add(elements.getElementAt(i));
		Collections.sort(list);
		return list;
	}

	public String findClosestElement(String match)
	{
		return findClosestElement(getSortedElements(), match);
	}

	public String findClosestElement(ArrayList<String> elements, String match)
	{
		if(match == null || match.equals(""))
			return null;
		for(String element : elements)
			if(element.toUpperCase().startsWith(match.toUpperCase()))
				return element;
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int selectionForKey(char aKey, ComboBoxModel aModel)
	{
		return 0;
	}
}