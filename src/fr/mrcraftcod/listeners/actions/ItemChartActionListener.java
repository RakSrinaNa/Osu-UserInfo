package fr.mrcraftcod.listeners.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.AbstractAction;
import fr.mrcraftcod.Main;
import fr.mrcraftcod.interfaces.InterfaceChart;
import fr.mrcraftcod.objects.User;

public class ItemChartActionListener extends AbstractAction
{
	private static final long serialVersionUID = -3935741960271142168L;

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		try
		{
			new InterfaceChart("MrCraftCod", "Osu!", User.deserialize(new File(new File(System.getenv("APPDATA"), Main.APPNAME), "MrCraftCod")).getAllStats(0));
		}
		catch(ClassNotFoundException | IOException e)
		{
			e.printStackTrace();
		}
	}
}
