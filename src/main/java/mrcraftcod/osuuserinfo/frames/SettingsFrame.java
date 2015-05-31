package mrcraftcod.osuuserinfo.frames;

import mrcraftcod.osuuserinfo.enums.Fonts;
import mrcraftcod.osuuserinfo.enums.Language;
import mrcraftcod.osuuserinfo.frames.component.JTextFieldLimitNumbers;
import mrcraftcod.osuuserinfo.listeners.actions.ButtonReturnSettingsActionListener;
import mrcraftcod.osuuserinfo.utils.Configuration;
import mrcraftcod.osuuserinfo.utils.Utils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Show a frame to modify settings.
 *
 * @author MrCraftCod
 * @since 1.4
 */
public class SettingsFrame extends JDialog
{
	private static final long serialVersionUID = -339025516182085233L;
	private final JCheckBox notificationCheck;
	private final JCheckBox keepDateCheck;
	private final JCheckBox autoCompletionCheck;
	private final JCheckBox devModeCheck;
	private final JCheckBox systemTrayCheck;
	private final JCheckBox loadingCheck;
	private final JComboBox<String> languageBox;
	private final JComboBox<String> fontsBox;
	private final JTextField numberKeepStats;
	private final JTextField favouriteUser;

	/**
	 * Constructor.
	 *
	 * @param parent The parent frame.
	 */
	public SettingsFrame(JFrame parent)
	{
		super(parent);
		int frameWidth = 400;
		setTitle(Utils.resourceBundle.getString("settings"));
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setIconImages(Utils.icons);
		setLayout(new GridBagLayout());
		setResizable(true);
		setAlwaysOnTop(false);
		getContentPane().setBackground(Utils.backColor);
		addWindowListener(new WindowListener()
		{
			@Override
			public void windowOpened(WindowEvent e)
			{
			}

			@Override
			public void windowClosing(final WindowEvent e)
			{
				((SettingsFrame) e.getSource()).closeFrame();
			}

			@Override
			public void windowClosed(WindowEvent e)
			{
			}

			@Override
			public void windowIconified(WindowEvent e)
			{
			}

			@Override
			public void windowDeiconified(WindowEvent e)
			{
			}

			@Override
			public void windowActivated(WindowEvent e)
			{
			}

			@Override
			public void windowDeactivated(WindowEvent e)
			{
			}
		});
		this.languageBox = new JComboBox<>(Language.getNames());
		this.languageBox.setSelectedItem(Language.getLanguageByID(Utils.config.getString(Configuration.LOCALE, Language.DEFAULT.getID())).getName());
		JLabel languageText = new JLabel(Utils.resourceBundle.getString("pref_language") + ":");
		this.fontsBox = new JComboBox<>(Fonts.getNames());
		this.fontsBox.setSelectedItem(Utils.config.getString(Configuration.FONT, Fonts.DEFAULT.getName()));
		JLabel fontsText = new JLabel(Utils.resourceBundle.getString("pref_font") + ":");
		this.loadingCheck = new JCheckBox();
		this.loadingCheck.setText(Utils.resourceBundle.getString("settings_loading_screen"));
		this.loadingCheck.setSelected(Utils.config.getBoolean(Configuration.LOADINGSCREEN, true));
		this.keepDateCheck = new JCheckBox();
		this.keepDateCheck.setText(Utils.resourceBundle.getString("settings_keep_date"));
		this.keepDateCheck.setSelected(Utils.config.getBoolean(Configuration.KEEPDATE, false));
		this.notificationCheck = new JCheckBox();
		this.notificationCheck.setText(Utils.resourceBundle.getString("settings_notifications"));
		this.notificationCheck.setSelected(Utils.config.getBoolean(Configuration.SHOWNOTIFICATION, false));
		this.autoCompletionCheck = new JCheckBox();
		this.autoCompletionCheck.setText(Utils.resourceBundle.getString("settings_auto_completion"));
		this.autoCompletionCheck.setSelected(Utils.config.getBoolean(Configuration.AUTOCOMPLETION, false));
		this.devModeCheck = new JCheckBox();
		this.devModeCheck.setText(Utils.resourceBundle.getString("settings_dev_mode"));
		this.devModeCheck.setSelected(Utils.config.getBoolean(Configuration.DEVMODE, false));
		this.systemTrayCheck = new JCheckBox();
		this.systemTrayCheck.setText(Utils.resourceBundle.getString("settings_reduce_tray"));
		this.systemTrayCheck.setSelected(Utils.config.getBoolean(Configuration.REDUCETRAY, false));
		JButton buttonReturn = new JButton(Utils.resourceBundle.getString("settings_confirm"));
		buttonReturn.addActionListener(new ButtonReturnSettingsActionListener());
		JLabel textNumberKeepStats = new JLabel(Utils.resourceBundle.getString("settings_number_stats_to_keep") + ":");
		this.numberKeepStats = new JTextField();
		this.numberKeepStats.setDocument(new JTextFieldLimitNumbers(5));
		this.numberKeepStats.setText(String.valueOf(Utils.numberTrackedStatsToKeep));
		JLabel favouriteUserText = new JLabel(Utils.resourceBundle.getString("pref_fav_user") + ":");
		this.favouriteUser = new JTextField();
		this.favouriteUser.setText(Utils.config.getString(Configuration.FAVOURITEUSER, ""));
		int lign = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = lign++;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 1;
		getContentPane().add(this.autoCompletionCheck, c);
		c.gridy = lign++;
		getContentPane().add(this.devModeCheck, c);
		c.gridy = lign++;
		getContentPane().add(this.loadingCheck, c);
		c.gridy = lign++;
		getContentPane().add(this.keepDateCheck, c);
		c.gridy = lign++;
		getContentPane().add(this.notificationCheck, c);
		c.gridy = lign++;
		getContentPane().add(this.systemTrayCheck, c);
		c.insets = new Insets(0, 3, 0, 0);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = lign++;
		getContentPane().add(favouriteUserText, c);
		c.gridx = 1;
		getContentPane().add(this.favouriteUser, c);
		c.gridy = lign++;
		c.gridx = 0;
		getContentPane().add(textNumberKeepStats, c);
		c.gridx = 1;
		getContentPane().add(this.numberKeepStats, c);
		c.gridy = lign++;
		c.gridx = 0;
		getContentPane().add(languageText, c);
		c.gridx = 1;
		getContentPane().add(this.languageBox, c);
		c.gridy = lign++;
		c.gridx = 0;
		getContentPane().add(fontsText, c);
		c.gridx = 1;
		getContentPane().add(this.fontsBox, c);
		c.gridwidth = 2;
		c.gridy = lign++;
		c.gridx = 0;
		getContentPane().add(buttonReturn, c);
		int frameHeight = lign * 30 + 20;
		setPreferredSize(new Dimension(frameWidth, frameHeight));
		setMinimumSize(new Dimension(frameWidth, frameHeight - 20));
		setLocationRelativeTo(parent);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);
		toFront();
	}

	/**
	 * Used to close the frame.
	 */
	private void closeFrame()
	{
		if(isSettingsModified())
		{
			int result = JOptionPane.showConfirmDialog(null, Utils.resourceBundle.getString("settings_save_changes"), Utils.resourceBundle.getString("settings_save_changes_title"), JOptionPane.YES_NO_OPTION);
			if(!(result == JOptionPane.YES_OPTION))
				return;
		}
		returnMain(false);
	}

	/**
	 * Used to know if the font has been modified.
	 *
	 * @return True if modified, false of not.
	 */
	private boolean isFontModified()
	{
		return !this.fontsBox.getSelectedItem().equals(Utils.config.getString(Configuration.FONT, Fonts.DEFAULT.getName()));
	}

	/**
	 * Used to know if the locale has been modified.
	 *
	 * @return True if modified, false of not.
	 */
	private boolean isLocaleModified()
	{
		return !Language.getLanguageByName((String) this.languageBox.getSelectedItem()).getID().equals(Utils.config.getString(Configuration.LOCALE, Language.DEFAULT.getID()));
	}

	/**
	 * Used to know if a settings have been modified.
	 *
	 * @return A boolean representing the modification or not.
	 */
	private boolean isSettingsModified()
	{
		return !this.favouriteUser.getText().equals(Utils.config.getString(Configuration.FAVOURITEUSER, null)) || !(Utils.config.getBoolean(Configuration.SHOWNOTIFICATION, false) == this.notificationCheck.isSelected()) || !(Utils.config.getBoolean(Configuration.KEEPDATE, false) == this.keepDateCheck.isSelected()) || !(Utils.config.getBoolean(Configuration.LOADINGSCREEN, true) == this.loadingCheck.isSelected()) || isLocaleModified() || isFontModified() || !(Utils.config.getBoolean(Configuration.REDUCETRAY, false) == this.systemTrayCheck.isSelected()) || !(Utils.config.getBoolean(Configuration.DEVMODE, false) == this.devModeCheck.isSelected()) || !(Utils.config.getBoolean(Configuration.AUTOCOMPLETION, false) == this.autoCompletionCheck.isSelected()) || !String.valueOf(Utils.numberTrackedStatsToKeep).equals(this.numberKeepStats.getText());
	}

	/**
	 * Used to exit the settings.
	 *
	 * @param save Indicate the need to save or not the settings.
	 */
	public void returnMain(boolean save)
	{
		if(save)
			save();
		dispose();
	}

	/**
	 * Used to save the chosen values.
	 */
	private void save()
	{
		boolean newInterface = isLocaleModified() || isFontModified();
		Utils.config.writeVar(Configuration.AUTOCOMPLETION, String.valueOf(this.autoCompletionCheck.isSelected()));
		Utils.config.writeVar(Configuration.DEVMODE, String.valueOf(this.devModeCheck.isSelected()));
		Utils.config.writeVar(Configuration.REDUCETRAY, String.valueOf(this.systemTrayCheck.isSelected()));
		Utils.config.writeVar(Configuration.LOADINGSCREEN, String.valueOf(this.loadingCheck.isSelected()));
		Utils.config.writeVar(Configuration.KEEPDATE, String.valueOf(this.keepDateCheck.isSelected()));
		Utils.config.writeVar(Configuration.SHOWNOTIFICATION, String.valueOf(this.notificationCheck.isSelected()));
		Utils.config.writeVar(Configuration.LOCALE, Language.getLanguageByName((String) this.languageBox.getSelectedItem()).getID());
		Utils.config.writeVar(Configuration.FONT, this.fontsBox.getSelectedItem());
		if(!this.numberKeepStats.getText().equals(""))
		{
			Utils.config.writeVar(Configuration.STATSTOKEEP, this.numberKeepStats.getText());
			Utils.numberTrackedStatsToKeep = Integer.valueOf(this.numberKeepStats.getText());
		}
		if(!this.favouriteUser.getText().equals(""))
			Utils.config.writeVar(Configuration.FAVOURITEUSER, this.favouriteUser.getText());
		Utils.mainFrame.updateAutoCompletionStatus(this.autoCompletionCheck.isSelected());
		if(newInterface)
			try
			{
				Utils.reloadResourceBundleWithLocale(Language.getLanguageByName((String) this.languageBox.getSelectedItem()));
				Utils.newFrame(Utils.lastUser.getUsername(), Utils.mainFrame.getLocation(), Utils.mainFrame.getSelectedMode());
			}
			catch(IOException e)
			{
				Utils.logger.log(Level.SEVERE, "Error opening new frame!", e);
			}
	}
}
