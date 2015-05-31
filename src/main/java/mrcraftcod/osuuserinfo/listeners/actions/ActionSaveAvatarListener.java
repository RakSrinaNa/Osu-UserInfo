package mrcraftcod.osuuserinfo.listeners.actions;

import mrcraftcod.osuuserinfo.utils.Utils;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ActionSaveAvatarListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			Utils.saveImage(Utils.mainFrame.getAvatarImage(), new File(Utils.getNewFilePatch(new File("."), JFileChooser.DIRECTORIES_ONLY, null), Utils.lastUser.getUsername() + ".png"));
		}
		catch(IOException e1)
		{
			e1.printStackTrace();
		}
	}
}
