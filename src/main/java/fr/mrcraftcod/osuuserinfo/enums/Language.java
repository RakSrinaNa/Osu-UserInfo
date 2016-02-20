package mrcraftcod.osuuserinfo.enums;

import java.util.ArrayList;
import java.util.Locale;

public enum Language
{
	DEFAULT("system", Locale.getDefault(), ""), ENGLISH("en", Locale.ENGLISH, ""), FRENCH("fr", Locale.FRENCH, ""), ITALIAN("it", Locale.ITALIAN, ""), BULGARIAN("bg", new Locale("bg"), "");
	private final String ID;
	private final Locale locale;
	private String name;

	Language(String ID, Locale locale, String name)
	{
		this.ID = ID;
		this.locale = locale;
		this.name = name;
	}

	/**
	 * Used to get the a language by its ID.
	 *
	 * @param ID The language key (fr, en, it ...).
	 * @return The language.
	 */
	public static Language getLanguageByID(String ID)
	{
		for(Language l : Language.values())
			if(l.getID() != null)
				if(l.getID().equals(ID))
					return l;
		return Language.DEFAULT;
	}

	public static Language getLanguageByName(String name)
	{
		for(Language l : Language.values())
			if(l.getName() != null)
				if(l.getName().equals(name))
					return l;
		return Language.DEFAULT;
	}

	public static String[] getNames()
	{
		ArrayList<String> names = new ArrayList<>();
		for(Language l : Language.values())
			names.add(l.getName());
		return names.toArray(new String[names.size()]);
	}

	public String getID()
	{
		return this.ID;
	}

	public Locale getLocale()
	{
		return this.locale;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
