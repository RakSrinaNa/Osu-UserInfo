package fr.mrcraftcod.osuuserinfo.frames.component;

import javax.swing.*;
import java.util.Comparator;

/*
 *  Custom model to make sure the items are stored in a sorted order.
 *  The default is to sort in the natural order of the item, but a
 *  Comparator can be used to customize the sort order.
 */
public class SortedComboBoxModel<E> extends DefaultComboBoxModel<E>
{
	private static final long serialVersionUID = -8689907655719039696L;
	private final Comparator comparator;

	public SortedComboBoxModel(E items[], Comparator comparator)
	{
		this.comparator = comparator;
		for(E item : items)
			addElement(item);
	}

	@Override
	public void addElement(E element)
	{
		insertElementAt(element, 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insertElementAt(E element, int index)
	{
		int size = getSize();
		for(index = 0; index < size; index++)
		{
			if(comparator != null)
			{
				E o = getElementAt(index);
				if(comparator.compare(o, element) > 0)
					break;
			}
			else
			{
				Comparable c = (Comparable) getElementAt(index);
				if(c.compareTo(element) > 0)
					break;
			}
		}
		super.insertElementAt(element, index);
		if(index == 0 && element != null)
			setSelectedItem(element);
	}
}