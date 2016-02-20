package fr.mrcraftcod.osuuserinfo.listeners.document;

import fr.mrcraftcod.osuuserinfo.utils.Utils;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Used to set the 'update' button if the user is the same as the previous one.
 *
 * @author MrCraftCod
 */
public class UserNameFieldDocumentListener implements DocumentListener
{
	@Override
	public void insertUpdate(DocumentEvent e)
	{
		update(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e)
	{
		update(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e)
	{
		update(e);
	}

	private void update(DocumentEvent e)
	{
		try
		{
			String text = e.getDocument().getText(0, e.getDocument().getLength());
			if(text.equalsIgnoreCase(Utils.lastUser.getUsername()) && !text.equalsIgnoreCase(""))
				Utils.mainFrame.setValidButonIcon("R");
			else
				Utils.mainFrame.setValidButonIcon("S");
		}
		catch(Exception ignored)
		{
		}
	}
}
