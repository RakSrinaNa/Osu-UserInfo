package fr.mrcraftcod.interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import fr.mrcraftcod.Main;
import fr.mrcraftcod.objects.JTableUneditableModel;
import fr.mrcraftcod.objects.TableColumnAdjuster;

/**
 * Will show a frame containing a text and the traducers.
 * 
 * @author MrCraftCod
 */
public class InterfaceAbout extends JFrame
{
	private static final long serialVersionUID = 6055795212223739508L;
	private HashMap<String, String> traducers;
	private String[][] valuesTable;
	private JFrame frame;
	private JScrollPane scrollPane;
	private JTable table;
	private JTableUneditableModel model;
	private TableColumnAdjuster tca;

	/**
	 * Constructor. Will show a frame containing a text and the traducers.
	 * 
	 * @param parent The parent component.
	 */
	public InterfaceAbout(Component parent)
	{
		traducers = new HashMap<String, String>();
		traducers.put("Italian", "TheHowl");
		frame = new JFrame("About");
		frame.setVisible(false);
		frame.setLayout(new BorderLayout());
		frame.setPreferredSize(new Dimension(550, 175));
		frame.setAlwaysOnTop(false);
		frame.setIconImages(Main.icons);
		// frame.setBackground(Main.backColor);
		frame.addWindowListener(new WindowListener()
		{
			@Override
			public void windowActivated(WindowEvent arg0)
			{}

			@Override
			public void windowClosed(WindowEvent arg0)
			{
				Interface.showFrame();
			}

			@Override
			public void windowClosing(WindowEvent arg0)
			{
				Interface.showFrame();
			}

			@Override
			public void windowDeactivated(WindowEvent arg0)
			{}

			@Override
			public void windowDeiconified(WindowEvent arg0)
			{}

			@Override
			public void windowIconified(WindowEvent arg0)
			{}

			@Override
			public void windowOpened(WindowEvent arg0)
			{}
		});
		JLabel text = new JLabel(Main.resourceBundle.getString("about_text"));
		// text.setBackground(Main.backColor);
		frame.add(text, BorderLayout.NORTH);
		model = new JTableUneditableModel(valuesTable = getTraducers(), new String[] {Main.resourceBundle.getString("language"), Main.resourceBundle.getString("traducer")});
		table = new JTable(model);
		// table.setBackground(Main.backColor);
		table.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e))
					try
					{
						openProfile(((JTable) e.getSource()).getSelectedRow());
					}
					catch(IOException | URISyntaxException e1)
					{
						e1.printStackTrace();
					}
			}

			@Override
			public void mousePressed(MouseEvent e)
			{}

			@Override
			public void mouseReleased(MouseEvent e)
			{}

			@Override
			public void mouseEntered(MouseEvent e)
			{}

			@Override
			public void mouseExited(MouseEvent e)
			{}
		});
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(String.class, centerRenderer);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(0, 40);
		table.setShowGrid(true);
		table.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		table.setGridColor(Color.BLACK);
		tca = new TableColumnAdjuster(table);
		tca.adjustColumns();
		scrollPane = new JScrollPane(table);
		// scrollPane.setBackground(Main.backColor);
		scrollPane.setAutoscrolls(false);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.add(scrollPane, BorderLayout.CENTER);
		// frame.getContentPane().setBackground(Main.backColor);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(parent);
		frame.pack();
		frame.setVisible(true);
		frame.toFront();
		Interface.hideFrame();
	}

	/**
	 * Used to open the profile of a traducer.
	 * 
	 * @param selectedRow The row selected.
	 * 
	 * @throws MalformedURLException If the profile URL isn't correct.
	 * @throws IOException If the browser can't be opened.
	 * @throws URISyntaxException If the profile URL isn't correct.
	 */
	protected void openProfile(int selectedRow) throws MalformedURLException, IOException, URISyntaxException
	{
		String url = "";
		switch(valuesTable[selectedRow][1])
		{
			case "TheHowl":
				url = "http://osu.ppy.sh/u/2751672";
			break;
		}
		if(url.equals(""))
			return;
		Desktop.getDesktop().browse(new URL(url).toURI());
	}

	/**
	 * Used to get all traducers with their language.
	 * 
	 * @return A two dimensional array with language at position [i][0] and the name at [i][1].
	 */
	private String[][] getTraducers()
	{
		int i = 0;
		String[][] values = new String[traducers.values().size()][2];
		for(String language : traducers.keySet())
		{
			values[i][0] = language;
			values[i++][1] = traducers.get(language);
		}
		return values;
	}
}
