package fr.mrcraftcod.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import javax.swing.JDialog;
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
import fr.mrcraftcod.listeners.mouse.ChangelogMouseListener;
import fr.mrcraftcod.listeners.mouse.TraducersMouseListener;
import fr.mrcraftcod.objects.JHoverLabel;
import fr.mrcraftcod.objects.JTableUneditableModel;
import fr.mrcraftcod.objects.TableColumnAdjuster;
import fr.mrcraftcod.utils.Utils;

/**
 * Will show a frame containing a text and the traducers.
 *
 * @author MrCraftCod
 */
public class InterfaceAbout extends JDialog
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
	public InterfaceAbout(JFrame parent)
	{
		super(parent);
		this.traducers = new HashMap<String, String>();
		this.traducers.put(Utils.resourceBundle.getString("italian"), "TheHowl");
		setTitle(Utils.resourceBundle.getString("menu_bar_help_about"));
		setVisible(false);
		setModal(true);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(400, 200));
		setMinimumSize(new Dimension(400, 200));
		setAlwaysOnTop(false);
		setIconImages(Utils.icons);
		getContentPane().setBackground(Utils.backColor);
		JLabel text = new JLabel(Utils.resourceBundle.getString("about_text"));
		text.setBackground(Utils.backColor);
		add(text, BorderLayout.NORTH);
		this.model = new JTableUneditableModel(setValuesTable(getTraducers()), new String[] {Utils.resourceBundle.getString("language"), Utils.resourceBundle.getString("traducer")});
		this.table = new JTable(this.model);
		this.table.setBackground(Color.WHITE);
		this.table.addMouseListener(new TraducersMouseListener());
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		this.table.setDefaultRenderer(String.class, centerRenderer);
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.getTableHeader().setResizingAllowed(true);
		this.table.getTableHeader().setBackground(Utils.backColor);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.table.setRowHeight(0, 40);
		this.table.setShowGrid(true);
		this.table.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		this.table.setGridColor(Color.BLACK);
		this.tca = new TableColumnAdjuster(this.table);
		this.tca.adjustColumns();
		this.scrollPane = new JScrollPane(this.table);
		this.scrollPane.getViewport().setBackground(Utils.backColor);
		this.scrollPane.setBackground(Utils.backColor);
		this.scrollPane.setAutoscrolls(false);
		this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JPanel footer = new JPanel(new BorderLayout());
		footer.setBackground(Utils.backColor);
		JHyperlink linkAuthor = new JHyperlink(String.format(Utils.resourceBundle.getString("about_app_by"), "MrCraftCod"), "https://osu.ppy.sh/u/2313737");
		linkAuthor.setBorder(new EmptyBorder(3, 3, 3, 3));
		JHyperlink linkOsu = new JHyperlink(Utils.resourceBundle.getString("about_game_osu"), "https://osu.ppy.sh/");
		linkOsu.setBorder(new EmptyBorder(3, 3, 3, 3));
		JHoverLabel changelog = new JHoverLabel(Utils.resourceBundle.getString("changelog"));
		changelog.addMouseListener(new ChangelogMouseListener());
		changelog.setBorder(new EmptyBorder(3, 3, 3, 3));
		footer.add(changelog, BorderLayout.PAGE_START);
		footer.add(linkAuthor, BorderLayout.WEST);
		footer.add(linkOsu, BorderLayout.EAST);
		add(this.scrollPane, BorderLayout.CENTER);
		add(footer, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);
		pack();
		setVisible(true);
		toFront();
	}

	/**
	 * Used to get the current values in the Table.
	 *
	 * @return The values in the table.
	 */
	public String[][] getValuesTable()
	{
		return this.valuesTable;
	}

	/**
	 * Used to get all traducers with their language.
	 *
	 * @return A two dimensional array with language at position [i][0] and the name at [i][1].
	 */
	private String[][] getTraducers()
	{
		int i = 0;
		String[][] values = new String[this.traducers.values().size()][2];
		for(String language : this.traducers.keySet())
		{
			values[i][0] = language;
			values[i++][1] = this.traducers.get(language);
		}
		return values;
	}

	/**
	 * Used to set the values in the table.
	 *
	 * @param valuesTable The values to set.
	 * @return The values to set.
	 */
	private String[][] setValuesTable(String[][] valuesTable)
	{
		this.valuesTable = valuesTable;
		return valuesTable;
	}
}
