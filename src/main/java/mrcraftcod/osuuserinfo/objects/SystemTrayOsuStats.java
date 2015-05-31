package mrcraftcod.osuuserinfo.objects;

import mrcraftcod.osuuserinfo.Main;
import mrcraftcod.osuuserinfo.utils.Utils;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * The system tray icon of the app.
 *
 * @author MrCraftCod
 */
public class SystemTrayOsuStats
{
	private static SystemTray tray = null;
	private static TrayIcon trayIcon = null;

	/**
	 * Called when need to add the system tray icon.
	 */
	public static void add() throws AWTException
	{
		tray.add(trayIcon);
	}

	public static void init()
	{
		Utils.logger.log(Level.INFO, "Initialising system tray...");
		if(SystemTray.isSupported())
			tray = SystemTray.getSystemTray();
		else
		{
			Utils.logger.log(Level.WARNING, "Tray not supported!");
			return;
		}
		final PopupMenu popup = new PopupMenu();
		final MenuItem openItem = new MenuItem(Utils.resourceBundle.getString("system_tray_open"));
		openItem.addActionListener(e -> {
			showUpFrame();
			remove();
		});
		popup.add(openItem);
		final MenuItem closeItem = new MenuItem(Utils.resourceBundle.getString("system_tray_exit"));
		closeItem.addActionListener(e -> {
			Utils.exit(true);
			remove();
		});
		popup.add(closeItem);
		trayIcon = new TrayIcon(Utils.icons.get(0), Main.APPNAME, popup);
		trayIcon.setImageAutoSize(true);
		trayIcon.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(final MouseEvent arg0)
			{
			}

			@Override
			public void mousePressed(final MouseEvent e)
			{
				if(e.getClickCount() >= 2)
				{
					showUpFrame();
					final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
					executorService.schedule(() -> {
						remove();
						executorService.shutdown();
					}, 200, TimeUnit.MILLISECONDS);
				}
			}

			@Override
			public void mouseReleased(final MouseEvent e)
			{
			}

			@Override
			public void mouseEntered(final MouseEvent arg0)
			{
			}

			@Override
			public void mouseExited(final MouseEvent arg0)
			{
			}
		});
	}

	/**
	 * Called when need to remove the system tray
	 */
	private static void remove()
	{
		try
		{
			tray.remove(trayIcon);
		}
		catch(Exception e)
		{
			Utils.logger.log(Level.WARNING, "Error removing tray icon!", e);
		}
	}

	/**
	 * Used to show the main app frame.
	 */
	private static void showUpFrame()
	{
		Utils.mainFrame.backFromTray();
	}
}
