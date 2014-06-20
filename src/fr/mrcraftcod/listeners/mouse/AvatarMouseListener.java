package fr.mrcraftcod.listeners.mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import fr.mrcraftcod.listeners.actions.ActionSaveAvatarListener;
import fr.mrcraftcod.utils.Utils;

/**
 * Used to open the user's profile page or save image profile.
 *
 * @author MrCraftCod
 */
public class AvatarMouseListener implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent arg0)
	{}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{}

	@Override
	public void mouseExited(MouseEvent arg0)
	{}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		if(SwingUtilities.isRightMouseButton(arg0))
			openPopup(arg0);
		else if(arg0.getClickCount() > 1 && SwingUtilities.isLeftMouseButton(arg0))
			Utils.openUserProfile(Utils.lastUser);
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{}

	private void openPopup(MouseEvent arg0)
	{
		JPopupMenu popup = new JPopupMenu();
		JMenuItem saveAvatar = new JMenuItem(Utils.resourceBundle.getString("save_avatar"));
		saveAvatar.addActionListener(new ActionSaveAvatarListener());
		popup.add(saveAvatar);
		popup.show(Utils.mainFrame, arg0.getX(), arg0.getY());
	}
}
