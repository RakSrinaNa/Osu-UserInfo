package mrcraftcod.osuuserinfo.listeners.change;

import mrcraftcod.osuuserinfo.interfaces.ImageChangeListener;
import mrcraftcod.osuuserinfo.objects.ImageEvent;
import mrcraftcod.osuuserinfo.utils.Utils;

public class AvatarImageChange implements ImageChangeListener
{
	@Override
	public void onImageChanged(ImageEvent e)
	{
		Utils.mainFrame.setAvatarImage(e.getImage());
	}
}
