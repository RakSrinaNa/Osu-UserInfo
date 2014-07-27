package fr.mrcraftcod.listeners.change;

import fr.mrcraftcod.interfaces.ImageChangeListener;
import fr.mrcraftcod.objects.ImageEvent;
import fr.mrcraftcod.utils.Utils;

public class AvatarImageChange implements ImageChangeListener
{
	@Override
	public void onImageChanged(ImageEvent e)
	{
		Utils.mainFrame.setAvatarImage(e.getImage());
	}
}
