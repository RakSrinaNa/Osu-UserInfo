package mrcraftcod.osuuserinfo.enums;

import java.util.ArrayList;

public enum Fonts
{
	DEFAULT(null, "Default", 12), COMFORTAA("Comfortaa-Regular.ttf", "Comfortaa", 13);
	private final String name;
	private final String fileName;
	private final int size;

	Fonts(String fileName, String name, int size)
	{
		this.fileName = fileName;
		this.name = name;
		this.size = size;
	}

	public static Fonts getFontsByName(String name)
	{
		for(Fonts f : Fonts.values())
			if(f.getName().equals(name))
				return f;
		return Fonts.DEFAULT;
	}

	public static String[] getNames()
	{
		ArrayList<String> names = new ArrayList<>();
		for(Fonts f : Fonts.values())
			names.add(f.getName());
		return names.toArray(new String[names.size()]);
	}

	public String getFileName()
	{
		return this.fileName;
	}

	public String getName()
	{
		return this.name;
	}

	public int getSize()
	{
		return this.size;
	}
}
