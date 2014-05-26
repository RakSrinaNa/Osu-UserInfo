package fr.mrcraftcod;

import javax.swing.table.DefaultTableModel;

/**
 * Used to set the cells not editable in a JTable.
 * 
 * @author MrCraftCod
 */
public class JTableUneditableModel extends DefaultTableModel
{
	private static final long serialVersionUID = 1595933236184593763L;

	/**
	 * Constructor.
	 * 
	 * @see DefaultTableModel#DefaultTableModel(Object[], Object[])
	 */
	public JTableUneditableModel(Object[][] tableData, Object[] colNames)
	{
		super(tableData, colNames);
	}

	/**
	 * Set each cell as not editable.
	 * 
	 * @param row The row of the cell.
	 * @param column The column of the cell.
	 * 
	 * @see DefaultTableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int row, int column)
	{
		return false;
	}
}
