package fr.mrcraftcod.osuuserinfo.interfaces;

import fr.mrcraftcod.osuuserinfo.objects.ImageEvent;

public interface ImageChangeListener
{
	/**
	 * Invoked when the image is modified.
	 */
	void onImageChanged(ImageEvent e);
}
