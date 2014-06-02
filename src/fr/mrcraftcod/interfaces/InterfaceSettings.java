package fr.mrcraftcod.interfaces;

import java.awt.Dimension;
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
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
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
		frame.setLayout(new MigLayout());
		frame.setResizable(true);
		frame.setAlwaysOnTop(false);
		frame.setVisible(true);
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
		textNumberKeepStats.setHorizontalAlignment(JLabel.RIGHT);
		textNumberKeepStats.setVerticalAlignment(JLabel.CENTER);
		numberKeepStats = new JTextField();
		numberKeepStats.setDocument(new JTextFieldLimitNumbers(3));
		numberKeepStats.setText(String.valueOf(Main.numberTrackedStatsToKeep));
		int lign = 0;
		// TODO better layout
		frame.add(autoCompletionCheck, new CC().cell(0, lign++, 2, 1).alignX("left").grow());
		frame.add(devModeCheck, new CC().cell(0, lign++, 2, 1).alignX("left").grow());
		frame.add(systemTrayCheck, new CC().cell(0, lign++, 2, 1).alignX("left").grow());
		frame.add(textNumberKeepStats, new CC().cell(0, lign, 1, 1).alignX("left"));
		frame.add(numberKeepStats, new CC().cell(1, lign++, 1, 1).alignX("left").gapLeft("5").grow());
		frame.add(languageText, new CC().cell(0, lign, 1, 1).alignX("left").grow());
		frame.add(languageBox, new CC().cell(1, lign++, 1, 1).alignX("left").grow());
		frame.add(buttonReturn, new CC().cell(0, lign++, 2, 1).alignX("center").grow());
		int frameHeight = lign * 40 + 20;
		frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(Interface.getFrame());
		Interface.hideFrame();
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
		Interface.updateAutoCompletion(autoCompletionCheck.isSelected());
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
		Interface.showFrame();
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
