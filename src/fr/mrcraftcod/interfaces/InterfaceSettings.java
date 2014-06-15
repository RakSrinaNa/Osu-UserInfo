package fr.mrcraftcod.interfaces;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import fr.mrcraftcod.listeners.actions.ButtonReturnSettingsActionListener;
import fr.mrcraftcod.listeners.windows.SettingsWindowListener;
import fr.mrcraftcod.objects.JTextFieldLimitNumbers;
import fr.mrcraftcod.utils.Utils;

/**
 * Show a frame to modify settings.
 *
 * @author MrCraftCod
 *
 * @since 1.4
 */
public class InterfaceSettings extends JFrame
{
	private static final long serialVersionUID = -339025516182085233L;
	private JCheckBox notificationCheck, keepDateCheck, autoCompletionCheck, devModeCheck, systemTrayCheck, loadingCheck;
	private JComboBox<String> languageBox;
	private LinkedHashMap<String, String> languages;
	private JButton buttonReturn;
	private JLabel textNumberKeepStats;
	private JTextField numberKeepStats;

	/**
	 * Constructor.
	 */
	public InterfaceSettings()
	{
		super(Utils.resourceBundle.getString("settings"));
		this.languages = new LinkedHashMap<String, String>();
		int frameWidth = 400;
		getFrame().setIconImages(Utils.icons);
		getFrame().setLayout(new GridBagLayout());
		getFrame().setResizable(true);
		getFrame().setAlwaysOnTop(false);
		getFrame().setVisible(true);
		getFrame().getContentPane().setBackground(Utils.backColor);
		getFrame().addWindowListener(new SettingsWindowListener());
		this.languageBox = new JComboBox<String>(getLanguages());
		this.languageBox.setSelectedItem(getLang(Utils.config.getString("locale", null)));
		JLabel languageText = new JLabel(Utils.resourceBundle.getString("pref_language"));
		this.loadingCheck = new JCheckBox();
		this.loadingCheck.setText(Utils.resourceBundle.getString("settings_loading_screen"));
		this.loadingCheck.setSelected(Utils.config.getBoolean("loadingScreen", true));
		this.keepDateCheck = new JCheckBox();
		this.keepDateCheck.setText(Utils.resourceBundle.getString("settings_keep_date"));
		this.keepDateCheck.setSelected(Utils.config.getBoolean("keepDate", false));
		this.notificationCheck = new JCheckBox();
		this.notificationCheck.setText(Utils.resourceBundle.getString("settings_notifications"));
		this.notificationCheck.setSelected(Utils.config.getBoolean("showNotification", false));
		this.autoCompletionCheck = new JCheckBox();
		this.autoCompletionCheck.setText(Utils.resourceBundle.getString("settings_auto_completion"));
		this.autoCompletionCheck.setSelected(Utils.config.getBoolean("autoCompletion", false));
		this.devModeCheck = new JCheckBox();
		this.devModeCheck.setText(Utils.resourceBundle.getString("settings_dev_mode"));
		this.devModeCheck.setSelected(Utils.config.getBoolean("devMode", false));
		this.systemTrayCheck = new JCheckBox();
		this.systemTrayCheck.setText(Utils.resourceBundle.getString("settings_reduce_tray"));
		this.systemTrayCheck.setSelected(Utils.config.getBoolean("reduceTray", false));
		this.buttonReturn = new JButton(Utils.resourceBundle.getString("settings_confirm"));
		this.buttonReturn.addActionListener(new ButtonReturnSettingsActionListener());
		this.textNumberKeepStats = new JLabel(Utils.resourceBundle.getString("settings_number_stats_to_keep"));
		this.numberKeepStats = new JTextField();
		this.numberKeepStats.setDocument(new JTextFieldLimitNumbers(3));
		this.numberKeepStats.setText(String.valueOf(Utils.numberTrackedStatsToKeep));
		int lign = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = lign++;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 1;
		getFrame().add(this.autoCompletionCheck, c);
		c.gridy = lign++;
		getFrame().add(this.devModeCheck, c);
		c.gridy = lign++;
		getFrame().add(this.loadingCheck, c);
		c.gridy = lign++;
		getFrame().add(this.keepDateCheck, c);
		c.gridy = lign++;
		getFrame().add(this.notificationCheck, c);
		c.gridy = lign++;
		getFrame().add(this.systemTrayCheck, c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = lign++;
		getFrame().add(this.textNumberKeepStats, c);
		c.gridx = 1;
		getFrame().add(this.numberKeepStats, c);
		c.gridy = lign++;
		c.gridx = 0;
		getFrame().add(languageText, c);
		c.gridx = 1;
		getFrame().add(this.languageBox, c);
		c.gridwidth = 2;
		c.gridy = lign++;
		c.gridx = 0;
		getFrame().add(this.buttonReturn, c);
		int frameHeight = lign * 30 + 20;
		getFrame().setPreferredSize(new Dimension(frameWidth, frameHeight));
		getFrame().setMinimumSize(new Dimension(frameWidth, frameHeight - 20));
		getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getFrame().setLocationRelativeTo(Utils.mainFrame.getFrame());
		Utils.mainFrame.hideFrame();
		getFrame().toFront();
		getFrame().pack();
	}

	public void closeFrame()
	{
		if(isSettingsModified())
		{
			hideFrame();
			int result = JOptionPane.showConfirmDialog(null, Utils.resourceBundle.getString("settings_save_changes"), Utils.resourceBundle.getString("settings_save_changes_title"), JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION)
			{
				returnMain(false);
				return;
			}
			showFrame();
			return;
		}
		returnMain(false);
	}

	private InterfaceSettings getFrame()
	{
		return this;
	}

	private String getLang(String string)
	{
		for(Entry<String, String> s : this.languages.entrySet())
			if(s.getValue() != null)
				if(s.getValue().equals(string))
					return s.getKey();
		return "System language";
	}

	private String[] getLanguages()
	{
		this.languages.put(Utils.resourceBundle.getString("system_language"), null);
		this.languages.put(Utils.resourceBundle.getString("english"), "en");
		this.languages.put(Utils.resourceBundle.getString("french"), "fr");
		this.languages.put(Utils.resourceBundle.getString("italian"), "it");
		return this.languages.keySet().toArray(new String[this.languages.keySet().size()]);
	}

	/**
	 * Used to hide the frame.
	 */
	public void hideFrame()
	{
		getFrame().setFocusable(false);
		getFrame().setEnabled(false);
	}

	public boolean isLocaleModified()
	{
		return !Utils.config.getString("locale", null).equals(this.languages.get(this.languageBox.getSelectedItem()));
	}

	/**
	 * Used to know if a settings have been modified.
	 *
	 * @return A boolean representing the modification or not.
	 */
	public boolean isSettingsModified()
	{
		return !(Utils.config.getBoolean("showNotification", false) == this.notificationCheck.isSelected()) || !(Utils.config.getBoolean("keepDate", false) == this.keepDateCheck.isSelected()) || !(Utils.config.getBoolean("loadingScreen", true) == this.loadingCheck.isSelected()) || !(Utils.config.getString("locale", null) != this.languages.get(this.languageBox.getSelectedItem())) || !(Utils.config.getBoolean("reduceTray", false) == this.systemTrayCheck.isSelected()) || !(Utils.config.getBoolean("devMode", false) == this.devModeCheck.isSelected()) || !(Utils.config.getBoolean("autoCompletion", false) == this.autoCompletionCheck.isSelected()) || !String.valueOf(Utils.numberTrackedStatsToKeep).equals(this.numberKeepStats.getText());
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
		Utils.mainFrame.showFrame();
		getFrame().dispose();
	}

	/**
	 * Used to save the chosen values.
	 */
	public void save()
	{
		boolean newInterface = isLocaleModified();
		Utils.config.writeVar("autoCompletion", String.valueOf(this.autoCompletionCheck.isSelected()));
		Utils.config.writeVar("devMode", String.valueOf(this.devModeCheck.isSelected()));
		Utils.config.writeVar("reduceTray", String.valueOf(this.systemTrayCheck.isSelected()));
		Utils.config.writeVar("loadingScreen", String.valueOf(this.loadingCheck.isSelected()));
		Utils.config.writeVar("keepDate", String.valueOf(this.keepDateCheck.isSelected()));
		Utils.config.writeVar("showNotification", String.valueOf(this.notificationCheck.isSelected()));
		Utils.config.writeVar("locale", this.languages.get(this.languageBox.getSelectedItem()));
		if(!this.numberKeepStats.getText().equals("") && !this.numberKeepStats.getText().equals("0"))
		{
			Utils.config.writeVar("statsToKeep", this.numberKeepStats.getText());
			Utils.numberTrackedStatsToKeep = Integer.valueOf(this.numberKeepStats.getText());
		}
		Utils.mainFrame.updateAutoCompletionStatus(this.autoCompletionCheck.isSelected());
		if(newInterface)
			try
		{
				Utils.reloadResourceBundleWithLocale(this.languages.get(this.languageBox.getSelectedItem()));
				Utils.newFrame(Utils.lastUser.getUsername(), Utils.mainFrame.getLocation());
		}
		catch(IOException e)
		{
			Utils.logger.log(Level.SEVERE, "Error opening new frame!", e);
		}
	}

	/**
	 * Used to show the frame.
	 */
	public void showFrame()
	{
		getFrame().setFocusable(true);
		getFrame().setEnabled(true);
		getFrame().toFront();
	}
}
