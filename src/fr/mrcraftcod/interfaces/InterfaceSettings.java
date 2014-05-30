package fr.mrcraftcod.interfaces;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
public class InterfaceSettings extends JFrame
{
	private static final long serialVersionUID = -5030788972447533004L;
	public JFrame frame;
	private JCheckBox autoCompletionCheck;
	private JCheckBox devModeCheck;
	private JButton buttonReturn;
	private JLabel textNumberKeepStats;
	private JTextField numberKeepStats;

	/**
	 * Constructor.
	 */
	public InterfaceSettings()
	{
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
		autoCompletionCheck = new JCheckBox();
		autoCompletionCheck.setText(Main.resourceBundle.getString("settings_auto_completion"));
		autoCompletionCheck.setSelected(Main.config.getBoolean("autoCompletion", false));
		devModeCheck = new JCheckBox();
		devModeCheck.setText(Main.resourceBundle.getString("settings_dev_mode"));
		devModeCheck.setSelected(Main.config.getBoolean("devMode", false));
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
		frame.add(textNumberKeepStats, new CC().cell(0, lign, 1, 1).alignX("left"));
		frame.add(numberKeepStats, new CC().cell(1, lign++, 1, 1).alignX("left").gapLeft("5").grow());
		frame.add(buttonReturn, new CC().cell(0, lign++, 2, 1).alignX("center").grow());
		int frameHeight = lign * 40 + 20;
		frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(Interface.getFrame());
		Interface.hideFrame();
		frame.toFront();
		frame.pack();
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
		return !(Main.config.getBoolean("devMode", false) == devModeCheck.isSelected()) || !(Main.config.getBoolean("autoCompletion", false) == autoCompletionCheck.isSelected()) || !String.valueOf(Main.numberTrackedStatsToKeep).equals(numberKeepStats.getText());
	}
}
