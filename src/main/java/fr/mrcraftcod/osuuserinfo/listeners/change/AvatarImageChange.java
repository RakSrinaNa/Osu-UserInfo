package fr.mrcraftcod.osuuserinfo.listeners.change;

import fr.mrcraftcod.osuuserinfo.interfaces.ImageChangeListener;
import fr.mrcraftcod.osuuserinfo.objects.ImageEvent;
import fr.mrcraftcod.osuuserinfo.utils.Utils;

public class AvatarImageChange implements ImageChangeListener
{
	@Override
	public void onImageChanged(ImageEvent e)
	{
		Utils.mainFrame.setAvatarImage(e.getImage());
	}
}
