package fr.mrcraftcod;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class InterfaceSettings extends JFrame
{
	private static final long serialVersionUID = -5030788972447533004L;
	public static JFrame frame;
	private static JCheckBox autoCompletionCheck;
	private static JButton buttonReturn;

	public InterfaceSettings()
	{
		int lines = 0;
		int frameWidth = 600;
		frame = new JFrame(Main.resourceBundle.getString("settings"));
		frame.setIconImages(Main.icons);
		frame.setLayout(new GridBagLayout());
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
		autoCompletionCheck.setSelected("true".equals(Main.config.getVar("autoCompletion")));
		buttonReturn = new JButton(Main.resourceBundle.getString("settings_confirm"));
		buttonReturn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				returnMain(true);
			}
		});
		GridBagConstraints constraint = new GridBagConstraints();
		constraint.anchor = GridBagConstraints.PAGE_START;
		constraint.fill = GridBagConstraints.BOTH;
		constraint.gridwidth = 2;
		constraint.gridx = 0;
		constraint.gridy = lines++;
		constraint.ipadx = frameWidth;
		constraint.weightx = 1;
		constraint.weighty = 1;
		constraint.gridy = lines++;
		frame.add(autoCompletionCheck, constraint);
		constraint.gridy = lines++;
		frame.add(buttonReturn, constraint);
		int frameHeight = lines * 30;
		frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(Interface.getFrame());
		Interface.hideFrame();
		frame.toFront();
		frame.pack();
	}

	public void hideFrame()
	{
		frame.setFocusable(false);
		frame.setEnabled(false);
	}

	public void showFrame()
	{
		frame.setFocusable(true);
		frame.setEnabled(true);
		frame.toFront();
	}

	public boolean save()
	{
		Main.config.writeVar("autoCompletion", String.valueOf(autoCompletionCheck.isSelected()));
		Interface.updateAutoCompletion(autoCompletionCheck.isSelected());
		return true;
	}

	public void returnMain(boolean save)
	{
		if(save)
			save();
		Interface.showFrame();
		frame.dispose();
	}

	public boolean isSettingsModified()
	{
		return !(Main.config.getBoolean("autoCompletion", true) == autoCompletionCheck.isSelected());
	}
}
