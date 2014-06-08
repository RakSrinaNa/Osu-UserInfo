package fr.mrcraftcod.listeners.mouse;

import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import fr.mrcraftcod.utils.Utils;

public class TraducersMouseListener implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e))
			try
			{
				openProfile(((JTable) e.getSource()).getSelectedRow());
			}
			catch(IOException | URISyntaxException e1)
			{
				e1.printStackTrace();
			}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{}

	@Override
	public void mouseReleased(MouseEvent e)
	{}

	@Override
	public void mouseEntered(MouseEvent e)
	{}

	@Override
	public void mouseExited(MouseEvent e)
	{}

	/**
	 * Used to open the profile of a traducer.
	 * 
	 * @param selectedRow The row selected.
	 * 
	 * @throws MalformedURLException If the profile URL isn't correct.
	 * @throws IOException If the browser can't be opened.
	 * @throws URISyntaxException If the profile URL isn't correct.
	 */
	public void openProfile(int selectedRow) throws MalformedURLException, IOException, URISyntaxException
	{
		String url = "";
		switch(Utils.aboutFrame.getValuesTable()[selectedRow][1])
		{
			case "TheHowl":
				url = "http://osu.ppy.sh/u/2751672";
			break;
		}
		if(url.equals(""))
			return;
		Desktop.getDesktop().browse(new URL(url).toURI());
	}
}