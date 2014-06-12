package fr.mrcraftcod.objects;

import java.util.Comparator;

public class ComparatorIgnoreCase<T> implements Comparator<T>
{
	@Override
	public int compare(T o1, T o2)
	{
		if(o1 instanceof String && o2 instanceof String)
			return ((String) o1).compareToIgnoreCase((String) o2);
		return 0;
	}
}
