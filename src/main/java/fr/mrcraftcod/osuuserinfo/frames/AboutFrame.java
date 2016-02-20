package fr.mrcraftcod.osuuserinfo.frames;

import fr.mrcraftcod.osuuserinfo.frames.component.JHoverLabel;
import fr.mrcraftcod.osuuserinfo.frames.component.JTableUneditableModel;
import fr.mrcraftcod.osuuserinfo.listeners.mouse.ChangelogMouseListener;
import fr.mrcraftcod.osuuserinfo.listeners.mouse.TraducersMouseListener;
import fr.mrcraftcod.osuuserinfo.objects.TableColumnAdjuster;
import fr.mrcraftcod.osuuserinfo.utils.Utils;
import org.swingplus.JHyperlink;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.MessageFormat;
import java.util.HashMap;

/**
 * Will show a frame containing a text and the traducers.
 *
 * @author MrCraftCod
 */
public class AboutFrame extends JDialog
{
	private static final long serialVersionUID = 1321865080360423098L;
	private final HashMap<String, String> traducers;
	private String[][] valuesTable;

	/**
	 * Constructor. Will show a frame containing a text and the traducers.
	 *
	 * @param parent The parent component.
	 */
	public AboutFrame(JFrame parent)
	{
		super(parent);
		this.traducers = new HashMap<>();
		this.traducers.put(Utils.resourceBundle.getString("italian"), "TheHowl");
		this.traducers.put(Utils.resourceBundle.getString("bulgarian"), "DanielDimitrov(MCDaniel98)");
		setTitle(Utils.resourceBundle.getString("menu_bar_help_about"));
		setVisible(false);
		setModal(true);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(400, 225));
		setMinimumSize(new Dimension(400, 200));
		setAlwaysOnTop(false);
		setIconImages(Utils.icons);
		getContentPane().setBackground(Utils.backColor);
		JLabel text = new JLabel(Utils.resourceBundle.getString("about_text"));
		text.setBackground(Utils.backColor);
		add(text, BorderLayout.NORTH);
		JTableUneditableModel model = new JTableUneditableModel(setValuesTable(getTraducers()), new String[]{Utils.resourceBundle.getString("language"), Utils.resourceBundle.getString("traducer")});
		JTable table = new JTable(model);
		table.setBackground(Color.WHITE);
		table.addMouseListener(new TraducersMouseListener());
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(String.class, centerRenderer);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(true);
		table.getTableHeader().setBackground(Utils.backColor);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(40);
		table.setShowGrid(true);
		table.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		table.setGridColor(Color.BLACK);
		TableColumnAdjuster tca = new TableColumnAdjuster(table);
		tca.adjustColumns();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Utils.backColor);
		scrollPane.setBackground(Utils.backColor);
		scrollPane.setAutoscrolls(false);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JPanel footer = new JPanel(new BorderLayout());
		footer.setBackground(Utils.backColor);
		JHyperlink linkAuthor = new JHyperlink(MessageFormat.format(Utils.resourceBundle.getString("about_app_by"), "MrCraftCod"), "https://osu.ppy.sh/u/2313737");
		linkAuthor.setBorder(new EmptyBorder(3, 3, 3, 3));
		JHyperlink linkOsu = new JHyperlink(Utils.resourceBundle.getString("about_game_osu"), "https://osu.ppy.sh/");
		linkOsu.setBorder(new EmptyBorder(3, 3, 3, 3));
		JHoverLabel changelog = new JHoverLabel(Utils.resourceBundle.getString("changelog"));
		changelog.addMouseListener(new ChangelogMouseListener());
		changelog.setBorder(new EmptyBorder(3, 3, 3, 3));
		footer.add(changelog, BorderLayout.PAGE_START);
		footer.add(linkAuthor, BorderLayout.WEST);
		footer.add(linkOsu, BorderLayout.EAST);
		add(scrollPane, BorderLayout.CENTER);
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
