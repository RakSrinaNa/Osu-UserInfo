package fr.mrcraftcod.osuuserinfo.enums;

public enum GameMode
{
	NONE(-1, "ERROR", "ERROR", "ERROR", "ERROR"), STANDARD(0, "osu!", "300", "100", "50"), TAIKO(1, "Taiko", "GREAT", "GOOD", "") /* TAIKO(1, "Taiko", "\uF97C", "\u53EF", "") */, CTB(2, "Catch The Beat", "Fruit", "Juice drop", "Droplet"), MANIA(3, "osu!mania", "300", "100", "50");
	private final String name;
	private final String c1;
	private final String c2;
	private final String c3;
	private final int ID;

	GameMode(int ID, String name, String c1, String c2, String c3)
	{
		this.ID = ID;
		this.name = name;
		this.c1 = c1;
		this.c2 = c2;
		this.c3 = c3;
	}

	public static GameMode getModByID(int ID)
	{
		for(GameMode g : GameMode.values())
			if(g.getID() == ID)
				return g;
		return NONE;
	}

	public String getC1()
	{
		return this.c1;
	}

	public String getC2()
	{
		return this.c2;
	}

	public String getC3()
	{
		return this.c3;
	}

	private int getID()
	{
		return this.ID;
	}

	public String getName()
	{
		return this.name;
	}
}
