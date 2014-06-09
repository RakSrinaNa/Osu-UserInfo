package fr.mrcraftcod.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import fr.mrcraftcod.utils.Utils;

public class UserNameFieldDocumentListener implements DocumentListener
{
	public void changedUpdate(DocumentEvent e)
	{
		update(e);
	}

	public void removeUpdate(DocumentEvent e)
	{
		update(e);
	}

	public void insertUpdate(DocumentEvent e)
	{
		update(e);
	}

	public void update(DocumentEvent e)
	{
		try
		{
			String text = e.getDocument().getText(0, e.getDocument().getLength());
			if(text.equalsIgnoreCase(Utils.lastUser.getUsername()) && !text.equalsIgnoreCase(""))
				Utils.mainFrame.setValidButonIcon("R");
			else
				Utils.mainFrame.setValidButonIcon("S");
		}
		catch(Exception ex)
		{}
	}
}
