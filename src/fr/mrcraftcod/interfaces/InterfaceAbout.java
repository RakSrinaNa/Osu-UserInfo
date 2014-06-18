package fr.mrcraftcod.interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import org.swingplus.JHyperlink;
import fr.mrcraftcod.listeners.mouse.TraducersMouseListener;
import fr.mrcraftcod.listeners.windows.AboutWindowListener;
import fr.mrcraftcod.objects.JTableUneditableModel;
import fr.mrcraftcod.objects.TableColumnAdjuster;
import fr.mrcraftcod.utils.Utils;

/**
 * Will show a frame containing a text and the traducers.
 * 
 * @author MrCraftCod
 */
public class InterfaceAbout extends JFrame
{
	private static final long serialVersionUID = 1321865080360423098L;
	private HashMap<String, String> traducers;
	private String[][] valuesTable;
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
		super(Utils.resourceBundle.getString("menu_bar_help_about"));
		traducers = new HashMap<String, String>();
		traducers.put(Utils.resourceBundle.getString("italian"), "TheHowl");
		getFrame().setVisible(false);
		getFrame().setLayout(new BorderLayout());
		getFrame().setPreferredSize(new Dimension(400, 200));
		getFrame().setMinimumSize(new Dimension(400, 200));
		getFrame().setAlwaysOnTop(false);
		getFrame().setIconImages(Utils.icons);
		getFrame().getContentPane().setBackground(Utils.backColor);
		getFrame().addWindowListener(new AboutWindowListener());
		JLabel text = new JLabel(Utils.resourceBundle.getString("about_text"));
		text.setBackground(Utils.backColor);
		getFrame().add(text, BorderLayout.NORTH);
		model = new JTableUneditableModel(setValuesTable(getTraducers()), new String[] {Utils.resourceBundle.getString("language"), Utils.resourceBundle.getString("traducer")});
		table = new JTable(model);
		table.setBackground(Color.WHITE);
		table.addMouseListener(new TraducersMouseListener());
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(String.class, centerRenderer);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(true);
		table.getTableHeader().setBackground(Utils.backColor);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(0, 40);
		table.setShowGrid(true);
		table.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		table.setGridColor(Color.BLACK);
		tca = new TableColumnAdjuster(table);
		tca.adjustColumns();
		scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Utils.backColor);
		scrollPane.setBackground(Utils.backColor);
		scrollPane.setAutoscrolls(false);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JPanel footer = new JPanel(new BorderLayout());
		footer.setBackground(Utils.backColor);
		JHyperlink linkAuthor = new JHyperlink(String.format(Utils.resourceBundle.getString("about_app_by"), "MrCraftCod"), "https://osu.ppy.sh/u/2313737");
		linkAuthor.setBorder(new EmptyBorder(3, 3, 3, 3));
		JHyperlink linkOsu = new JHyperlink(Utils.resourceBundle.getString("about_game_osu"), "https://osu.ppy.sh/");
		linkOsu.setBorder(new EmptyBorder(3, 3, 3, 3));
		footer.add(linkAuthor, BorderLayout.WEST);
		footer.add(linkOsu, BorderLayout.EAST);
		getFrame().add(scrollPane, BorderLayout.CENTER);
		getFrame().add(footer, BorderLayout.SOUTH);
		getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getFrame().setLocationRelativeTo(parent);
		getFrame().pack();
		getFrame().setVisible(true);
		getFrame().toFront();
		Utils.mainFrame.hideFrame();
	}

	public String[][] getValuesTable()
	{
		return valuesTable;
	}

	public String[][] setValuesTable(String[][] valuesTable)
	{
		this.valuesTable = valuesTable;
		return valuesTable;
	}

	private InterfaceAbout getFrame()
	{
		return this;
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
