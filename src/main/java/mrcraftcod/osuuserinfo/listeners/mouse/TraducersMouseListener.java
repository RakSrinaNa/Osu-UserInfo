package mrcraftcod.osuuserinfo.listeners.mouse;

import mrcraftcod.osuuserinfo.frames.AboutFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Used to open the traducers' profile page.
 *
 * @author MrCraftCod
 */
public class TraducersMouseListener implements MouseListener
{
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e))
			try
			{
				openProfile(e);
			}
			catch(IOException | URISyntaxException e1)
			{
				e1.printStackTrace();
			}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	/**
	 * Used to open the profile of a traducer.
	 *
	 * @throws MalformedURLException If the profile URL isn't correct.
	 * @throws IOException If the browser can't be opened.
	 * @throws URISyntaxException If the profile URL isn't correct.
	 */
	private void openProfile(MouseEvent e) throws IOException, URISyntaxException
	{
		switch(((AboutFrame) SwingUtilities.getRoot((Component) e.getSource())).getValuesTable()[((JTable) e.getSource()).getSelectedRow()][1])
		{
			case "TheHowl":
				Desktop.getDesktop().browse(new URL("http://osu.ppy.sh/u/2751672").toURI());
				break;
			case "DanielDimitrov(MCDaniel98)":
				Desktop.getDesktop().browse(new URL("https://osu.ppy.sh/u/3932131").toURI());
				break;
		}
	}
}