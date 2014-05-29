package fr.mrcraftcod.objects;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import fr.mrcraftcod.Main;
import fr.mrcraftcod.interfaces.Interface;

/**
 * The system tray icon of the app.
 * 
 * @author MrCraftCod
 */
public class SystemTrayOsuStats
{
	public static SystemTray tray = null;
	public static TrayIcon trayIcon = null;

	/**
	 * Called when need to add the system tray icon.
	 */
	public static void add() throws AWTException
	{
		tray.add(trayIcon);
	}

	/**
	 * Initalize the SystemTray object.
	 * 
	 * @throws IOException if the system tray object cannot be initialized.
	 */
	public static void init() throws IOException
	{
		if(SystemTray.isSupported())
			tray = SystemTray.getSystemTray();
		else
			return;
		final PopupMenu popup = new PopupMenu();
		final MenuItem openItem = new MenuItem(Main.resourceBundle.getString("system_tray_open"));
		openItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				showUpFrame();
				remove();
			}
		});
		popup.add(openItem);
		final MenuItem closeItem = new MenuItem(Main.resourceBundle.getString("system_tray_exit"));
		closeItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				Interface.exit();
				remove();
			}
		});
		popup.add(closeItem);
		trayIcon = new TrayIcon(Main.icons.get(0), Main.APPNAME, popup);
		trayIcon.setImageAutoSize(true);
		trayIcon.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(final MouseEvent arg0)
			{}

			@Override
			public void mouseEntered(final MouseEvent arg0)
			{}

			@Override
			public void mouseExited(final MouseEvent arg0)
			{}

			@Override
			public void mousePressed(final MouseEvent e)
			{
				if(e.getClickCount() >= 2)
				{
					showUpFrame();
					final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
					executorService.schedule(new Runnable()
					{
						@Override
						public void run()
						{
							remove();
							executorService.shutdown();
						}
					}, 200, TimeUnit.MILLISECONDS);
				}
			}

			@Override
			public void mouseReleased(final MouseEvent e)
			{}
		});
	}

	/**
	 * Used to show the main app frame.
	 */
	public static void showUpFrame()
	{
		Interface.backFromTray();
	}

	/**
	 * Called when need to notify by the system tray.
	 * 
	 * @param title The title of the notification.
	 * @param message The message of the notification.
	 * @param messageType The type of the notification.
	 */
	public static void notify(final String title, final String message, final TrayIcon.MessageType messageType)
	{
		try
		{
			trayIcon.displayMessage(title, message, messageType);
		}
		catch(final Exception e)
		{}
	}

	/**
	 * Called when need to remove the system tray
	 */
	public static void remove()
	{
		tray.remove(trayIcon);
	}
}
