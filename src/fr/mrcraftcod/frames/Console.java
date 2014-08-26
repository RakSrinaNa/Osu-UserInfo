package fr.mrcraftcod.frames;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import fr.mrcraftcod.utils.Utils;

public class Console extends JFrame implements WindowListener
{
	private static final long serialVersionUID = -1929422500948532428L;
	private final static int MAXCHARCOUNT = 100000;
	private JScrollPane scrollPane;
	private static JTextArea textArea = new JTextArea();

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
		this.scrollPane = new JScrollPane(textArea);
		this.scrollPane.setAutoscrolls(true);
		this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener()
		{
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e)
			{
				if(!(e.getSource() instanceof JScrollBar))
					return;
				JScrollBar scBar = (JScrollBar) e.getSource();
				int maximum = scBar.getMaximum() - scBar.getHeight();
				if(maximum - e.getValue() < 0.02f * maximum)
					scBar.setValue(maximum);
			}
		});
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(this.scrollPane, BorderLayout.CENTER);
		getContentPane().add(button, BorderLayout.SOUTH);
		setVisible(true);
		addWindowListener(this);
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				textArea.setText("");
			}
		});
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
	public void windowActivated(WindowEvent e)
	{}

	@Override
	public void windowClosed(WindowEvent e)
	{
		setVisible(false);
		dispose();
	}

	@Override
	public synchronized void windowClosing(WindowEvent evt)
	{}

	@Override
	public void windowDeactivated(WindowEvent e)
	{}

	@Override
	public void windowDeiconified(WindowEvent e)
	{}

	@Override
	public void windowIconified(WindowEvent e)
	{}

	@Override
	public void windowOpened(WindowEvent e)
	{}
}