package fr.mrcraftcod.interfaces;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import fr.mrcraftcod.Main;
import fr.mrcraftcod.objects.JTextFieldLimitNumbers;

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
	private JCheckBox autoCompletionCheck;
	private JCheckBox devModeCheck;
	private JCheckBox systemTrayCheck;
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
		frame = new JFrame(Main.resourceBundle.getString("settings"));
		frame.setIconImages(Main.icons);
		frame.setLayout(new GridBagLayout());
		frame.setResizable(true);
		frame.setAlwaysOnTop(false);
		frame.setVisible(true);
		frame.getContentPane().setBackground(Main.backColor);
		frame.addWindowListener(new WindowListener()
		{
			@Override
			public void windowOpened(WindowEvent e)
			{}

			@Override
			public void windowClosing(final WindowEvent e)
			{
				if(isSettingsModified())
				{
					hideFrame();
					int result = JOptionPane.showConfirmDialog(null, Main.resourceBundle.getString("settings_save_changes"), Main.resourceBundle.getString("settings_save_changes_title"), JOptionPane.YES_NO_OPTION);
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

			@Override
			public void windowClosed(WindowEvent e)
			{}

			@Override
			public void windowIconified(WindowEvent e)
			{}

			@Override
			public void windowDeiconified(WindowEvent e)
			{}

			@Override
			public void windowActivated(WindowEvent e)
			{}

			@Override
			public void windowDeactivated(WindowEvent e)
			{}
		});
		languageBox = new JComboBox<String>(getLanguages());
		languageBox.setSelectedItem(getLang(Main.config.getString("locale", null)));
		JLabel languageText = new JLabel(Main.resourceBundle.getString("pref_language"));
		autoCompletionCheck = new JCheckBox();
		autoCompletionCheck.setText(Main.resourceBundle.getString("settings_auto_completion"));
		autoCompletionCheck.setSelected(Main.config.getBoolean("autoCompletion", false));
		devModeCheck = new JCheckBox();
		devModeCheck.setText(Main.resourceBundle.getString("settings_dev_mode"));
		devModeCheck.setSelected(Main.config.getBoolean("devMode", false));
		systemTrayCheck = new JCheckBox();
		systemTrayCheck.setText(Main.resourceBundle.getString("settings_reduce_tray"));
		systemTrayCheck.setSelected(Main.config.getBoolean("reduceTray", false));
		buttonReturn = new JButton(Main.resourceBundle.getString("settings_confirm"));
		buttonReturn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				returnMain(true);
			}
		});
		textNumberKeepStats = new JLabel(Main.resourceBundle.getString("settings_number_stats_to_keep"));
		numberKeepStats = new JTextField();
		numberKeepStats.setDocument(new JTextFieldLimitNumbers(3));
		numberKeepStats.setText(String.valueOf(Main.numberTrackedStatsToKeep));
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
		frame.setLocationRelativeTo(Main.frame.getFrame());
		Main.frame.hideFrame();
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
		languages.put(Main.resourceBundle.getString("system_language"), null);
		languages.put(Main.resourceBundle.getString("english"), "en");
		languages.put(Main.resourceBundle.getString("french"), "fr");
		languages.put(Main.resourceBundle.getString("italian"), "it");
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
		Main.config.writeVar("autoCompletion", String.valueOf(autoCompletionCheck.isSelected()));
		Main.config.writeVar("devMode", String.valueOf(devModeCheck.isSelected()));
		Main.config.writeVar("reduceTray", String.valueOf(systemTrayCheck.isSelected()));
		Main.config.writeVar("locale", languages.get(languageBox.getSelectedItem()));
		if(!numberKeepStats.getText().equals("") && !numberKeepStats.getText().equals("0"))
		{
			Main.config.writeVar("statsToKeep", numberKeepStats.getText());
			Main.numberTrackedStatsToKeep = Integer.valueOf(numberKeepStats.getText());
		}
		Main.frame.updateAutoCompletion(autoCompletionCheck.isSelected());
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
		Main.frame.showFrame();
		frame.dispose();
	}

	/**
	 * Used to know if a settings have been modified.
	 * 
	 * @return A boolean representing the modification or not.
	 */
	public boolean isSettingsModified()
	{
		return !(Main.config.getString("locale", null) != languages.get(languageBox.getSelectedItem())) || !(Main.config.getBoolean("reduceTray", false) == systemTrayCheck.isSelected()) || !(Main.config.getBoolean("devMode", false) == devModeCheck.isSelected()) || !(Main.config.getBoolean("autoCompletion", false) == autoCompletionCheck.isSelected()) || !String.valueOf(Main.numberTrackedStatsToKeep).equals(numberKeepStats.getText());
	}
}
