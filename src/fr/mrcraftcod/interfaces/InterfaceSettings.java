package fr.mrcraftcod.interfaces;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
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
public class InterfaceSettings
{
	public JFrame frame;
	private JCheckBox autoCompletionCheck, devModeCheck, systemTrayCheck, loadingCheck;
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
		languages = new LinkedHashMap<String, String>();
		int frameWidth = 400;
		frame = new JFrame(Utils.resourceBundle.getString("settings"));
		frame.setIconImages(Utils.icons);
		frame.setLayout(new GridBagLayout());
		frame.setResizable(true);
		frame.setAlwaysOnTop(false);
		frame.setVisible(true);
		frame.getContentPane().setBackground(Utils.backColor);
		frame.addWindowListener(new SettingsWindowListener());
		languageBox = new JComboBox<String>(getLanguages());
		languageBox.setSelectedItem(getLang(Utils.config.getString("locale", null)));
		JLabel languageText = new JLabel(Utils.resourceBundle.getString("pref_language"));
		loadingCheck = new JCheckBox();
		loadingCheck.setText(Utils.resourceBundle.getString("settings_loading_screen"));
		loadingCheck.setSelected(Utils.config.getBoolean("loadingScreen", true));
		autoCompletionCheck = new JCheckBox();
		autoCompletionCheck.setText(Utils.resourceBundle.getString("settings_auto_completion"));
		autoCompletionCheck.setSelected(Utils.config.getBoolean("autoCompletion", false));
		devModeCheck = new JCheckBox();
		devModeCheck.setText(Utils.resourceBundle.getString("settings_dev_mode"));
		devModeCheck.setSelected(Utils.config.getBoolean("devMode", false));
		systemTrayCheck = new JCheckBox();
		systemTrayCheck.setText(Utils.resourceBundle.getString("settings_reduce_tray"));
		systemTrayCheck.setSelected(Utils.config.getBoolean("reduceTray", false));
		buttonReturn = new JButton(Utils.resourceBundle.getString("settings_confirm"));
		buttonReturn.addActionListener(new ButtonReturnSettingsActionListener());
		textNumberKeepStats = new JLabel(Utils.resourceBundle.getString("settings_number_stats_to_keep"));
		numberKeepStats = new JTextField();
		numberKeepStats.setDocument(new JTextFieldLimitNumbers(3));
		numberKeepStats.setText(String.valueOf(Utils.numberTrackedStatsToKeep));
		int lign = 0;
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = lign++;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 1;
		frame.add(autoCompletionCheck, c);
		c.gridy = lign++;
		frame.add(devModeCheck, c);
		c.gridy = lign++;
		frame.add(loadingCheck, c);
		c.gridy = lign++;
		frame.add(systemTrayCheck, c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = lign++;
		frame.add(textNumberKeepStats, c);
		c.gridx = 1;
		frame.add(numberKeepStats, c);
		c.gridy = lign++;
		c.gridx = 0;
		frame.add(languageText, c);
		c.gridx = 1;
		frame.add(languageBox, c);
		c.gridwidth = 2;
		c.gridy = lign++;
		c.gridx = 0;
		frame.add(buttonReturn, c);
		int frameHeight = lign * 30 + 20;
		frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
		frame.setMinimumSize(new Dimension(frameWidth, frameHeight - 20));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(Utils.mainFrame.getFrame());
		Utils.mainFrame.hideFrame();
		frame.toFront();
		frame.pack();
	}

	private String getLang(String string)
	{
		for(Entry<String, String> s : languages.entrySet())
			if(s.getValue() != null)
				if(s.getValue().equals(string))
					return s.getKey();
		return "System language";
	}

	private String[] getLanguages()
	{
		languages.put(Utils.resourceBundle.getString("system_language"), null);
		languages.put(Utils.resourceBundle.getString("english"), "en");
		languages.put(Utils.resourceBundle.getString("french"), "fr");
		languages.put(Utils.resourceBundle.getString("italian"), "it");
		return languages.keySet().toArray(new String[languages.keySet().size()]);
	}

	/**
	 * Used to hide the frame.
	 */
	public void hideFrame()
	{
		frame.setFocusable(false);
		frame.setEnabled(false);
	}

	/**
	 * Used to show the frame.
	 */
	public void showFrame()
	{
		frame.setFocusable(true);
		frame.setEnabled(true);
		frame.toFront();
	}

	/**
	 * Used to save the chosen values.
	 */
	public void save()
	{
		Utils.config.writeVar("autoCompletion", String.valueOf(autoCompletionCheck.isSelected()));
		Utils.config.writeVar("devMode", String.valueOf(devModeCheck.isSelected()));
		Utils.config.writeVar("reduceTray", String.valueOf(systemTrayCheck.isSelected()));
		Utils.config.writeVar("loadingScreen", String.valueOf(loadingCheck.isSelected()));
		Utils.config.writeVar("locale", languages.get(languageBox.getSelectedItem()));
		if(!numberKeepStats.getText().equals("") && !numberKeepStats.getText().equals("0"))
		{
			Utils.config.writeVar("statsToKeep", numberKeepStats.getText());
			Utils.numberTrackedStatsToKeep = Integer.valueOf(numberKeepStats.getText());
		}
		Utils.mainFrame.updateAutoCompletion(autoCompletionCheck.isSelected());
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
		frame.dispose();
	}

	/**
	 * Used to know if a settings have been modified.
	 * 
	 * @return A boolean representing the modification or not.
	 */
	public boolean isSettingsModified()
	{
		return !(Utils.config.getBoolean("loadingScreen", true) == loadingCheck.isSelected()) || !(Utils.config.getString("locale", null) != languages.get(languageBox.getSelectedItem())) || !(Utils.config.getBoolean("reduceTray", false) == systemTrayCheck.isSelected()) || !(Utils.config.getBoolean("devMode", false) == devModeCheck.isSelected()) || !(Utils.config.getBoolean("autoCompletion", false) == autoCompletionCheck.isSelected()) || !String.valueOf(Utils.numberTrackedStatsToKeep).equals(numberKeepStats.getText());
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
}
