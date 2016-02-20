package fr.mrcraftcod.osuuserinfo.frames;

import fr.mrcraftcod.osuuserinfo.utils.Utils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Console extends JFrame implements WindowListener
{
	private static final long serialVersionUID = -1929422500948532428L;
	private final static int MAXCHARCOUNT = 100000;
	private static final JTextArea textArea = new JTextArea();

	public Console()
	{
		super("Console");
		setAlwaysOnTop(false);
		setResizable(true);
		setIconImages(Utils.icons);
		setVisible(true);
		setSize(400, 400);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setCaretPosition(textArea.getDocument().getLength());
		JButton button = new JButton("ERASE");
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setAutoscrolls(true);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
			if(!(e.getSource() instanceof JScrollBar))
				return;
			JScrollBar scBar = (JScrollBar) e.getSource();
			int maximum = scBar.getMaximum() - scBar.getHeight();
			if(maximum - e.getValue() < 0.02f * maximum)
				scBar.setValue(maximum);
		});
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(button, BorderLayout.SOUTH);
		setVisible(true);
		addWindowListener(this);
		button.addActionListener(e -> textArea.setText(""));
	}

	public static void addtext(String text)
	{
		if(textArea != null)
		{
			textArea.append("\n" + text);
			textArea.repaint();
			if(textArea.getText().length() > MAXCHARCOUNT)
				textArea.setText(textArea.getText().substring(MAXCHARCOUNT / 10, textArea.getText().length()));
		}
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
	}

	@Override
	public synchronized void windowClosing(WindowEvent evt)
	{
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		setVisible(false);
		dispose();
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
}