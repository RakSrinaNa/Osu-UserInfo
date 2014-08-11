package fr.mrcraftcod.frames;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import fr.mrcraftcod.enums.Fonts;
import fr.mrcraftcod.enums.Language;
import fr.mrcraftcod.frames.component.JTextFieldLimitNumbers;
import fr.mrcraftcod.listeners.actions.ButtonReturnSettingsActionListener;
import fr.mrcraftcod.listeners.windows.SettingsWindowListener;
import fr.mrcraftcod.utils.Configuration;
import fr.mrcraftcod.utils.Utils;

/**
 * Show a frame to modify settings.
 *
 * @author MrCraftCod
 *
 * @since 1.4
 */
public class SettingsFrame extends JDialog
{
	private static final long serialVersionUID = -339025516182085233L;
	private JCheckBox anonCheck, notificationCheck, keepDateCheck, autoCompletionCheck, devModeCheck, systemTrayCheck, loadingCheck;
	private JComboBox<String> languageBox, fontsBox;
	private JButton buttonReturn;
	private JLabel textNumberKeepStats;
	private JTextField numberKeepStats;
	private JTextField favouriteUser;

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
		addWindowListener(new SettingsWindowListener());
		this.languageBox = new JComboBox<String>(Language.getNames());
		this.languageBox.setSelectedItem(Language.getLanguageByID(Utils.config.getString(Configuration.LOCALE, Language.DEFAULT.getID())).getName());
		JLabel languageText = new JLabel(Utils.resourceBundle.getString("pref_language") + ":");
		this.fontsBox = new JComboBox<String>(Fonts.getNames());
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
		this.anonCheck = new JCheckBox();
		this.anonCheck.setText(Utils.resourceBundle.getString("settings_anon"));
		this.anonCheck.setSelected(Utils.config.getBoolean(Configuration.ANONINFOS, true));
		this.systemTrayCheck = new JCheckBox();
		this.systemTrayCheck.setText(Utils.resourceBundle.getString("settings_reduce_tray"));
		this.systemTrayCheck.setSelected(Utils.config.getBoolean(Configuration.REDUCETRAY, false));
		this.buttonReturn = new JButton(Utils.resourceBundle.getString("settings_confirm"));
		this.buttonReturn.addActionListener(new ButtonReturnSettingsActionListener());
		this.textNumberKeepStats = new JLabel(Utils.resourceBundle.getString("settings_number_stats_to_keep") + ":");
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
		c.gridy = lign++;
		getContentPane().add(this.anonCheck, c);
		c.insets = new Insets(0, 3, 0, 0);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = lign++;
		getContentPane().add(favouriteUserText, c);
		c.gridx = 1;
		getContentPane().add(this.favouriteUser, c);
		c.gridy = lign++;
		c.gridx = 0;
		getContentPane().add(this.textNumberKeepStats, c);
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
		getContentPane().add(this.buttonReturn, c);
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
	public void closeFrame()
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
	public boolean isFontModified()
	{
		return !((String) this.fontsBox.getSelectedItem()).equals(Utils.config.getString(Configuration.FONT, Fonts.DEFAULT.getName()));
	}

	/**
	 * Used to know if the locale has been modified.
	 *
	 * @return True if modified, false of not.
	 */
	public boolean isLocaleModified()
	{
		return !Language.getLanguageByName((String) this.languageBox.getSelectedItem()).getID().equals(Utils.config.getString(Configuration.LOCALE, Language.DEFAULT.getID()));
	}

	/**
	 * Used to know if a settings have been modified.
	 *
	 * @return A boolean representing the modification or not.
	 */
	public boolean isSettingsModified()
	{
		return !(Utils.config.getBoolean(Configuration.ANONINFOS, true) == this.anonCheck.isSelected()) || !this.favouriteUser.getText().equals(Utils.config.getString(Configuration.FAVOURITEUSER, null)) || !(Utils.config.getBoolean(Configuration.SHOWNOTIFICATION, false) == this.notificationCheck.isSelected()) || !(Utils.config.getBoolean(Configuration.KEEPDATE, false) == this.keepDateCheck.isSelected()) || !(Utils.config.getBoolean(Configuration.LOADINGSCREEN, true) == this.loadingCheck.isSelected()) || isLocaleModified() || isFontModified() || !(Utils.config.getBoolean(Configuration.REDUCETRAY, false) == this.systemTrayCheck.isSelected()) || !(Utils.config.getBoolean(Configuration.DEVMODE, false) == this.devModeCheck.isSelected()) || !(Utils.config.getBoolean(Configuration.AUTOCOMPLETION, false) == this.autoCompletionCheck.isSelected()) || !String.valueOf(Utils.numberTrackedStatsToKeep).equals(this.numberKeepStats.getText());
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
	public void save()
	{
		boolean newInterface = isLocaleModified() || isFontModified();
		boolean newAnon = !(Utils.config.getBoolean(Configuration.ANONINFOS, true) == this.anonCheck.isSelected());
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
		if(newAnon)
			if(this.anonCheck.isSelected())
				try
				{
					Utils.initSQL();
				}
				catch(SQLException e1)
				{
					e1.printStackTrace();
				}
			else
				Utils.sql = null;
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
