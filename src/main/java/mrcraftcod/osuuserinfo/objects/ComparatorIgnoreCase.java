package mrcraftcod.osuuserinfo.objects;

import java.util.Comparator;

/**
 * Compare strings ignoring case.
 *
 * @param <T>
 * @author MrCraftCod
 */
public class ComparatorIgnoreCase<T> implements Comparator<T>
{
	/**
	 * Compare two strings ignoring case.
	 *
	 * @param o1 First string.
	 * @param o2 Second string.
	 * @see Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(T o1, T o2)
	{
		if(o1 instanceof String && o2 instanceof String)
			return ((String) o1).compareToIgnoreCase((String) o2);
		return 0;
	}
}
