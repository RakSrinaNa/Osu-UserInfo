package fr.mrcraftcod.objects;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * @author Come from Internet.
 */
public class TableColumnAdjuster implements PropertyChangeListener, TableModelListener
{
	class ColumnAction extends AbstractAction
	{
		private static final long serialVersionUID = -7054446543910403730L;
		private boolean isSelectedColumn;
		private boolean isAdjust;

		public ColumnAction(boolean isSelectedColumn, boolean isAdjust)
		{
			this.isSelectedColumn = isSelectedColumn;
			this.isAdjust = isAdjust;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(this.isSelectedColumn)
			{
				int[] columns = TableColumnAdjuster.this.table.getSelectedColumns();
				for(int column : columns)
					if(this.isAdjust)
						adjustColumn(column);
					else
						restoreColumn(column);
			}
			else if(this.isAdjust)
				adjustColumns();
			else
				restoreColumns();
		}
	}

	class ToggleAction extends AbstractAction
	{
		private static final long serialVersionUID = -1908076775236567044L;
		private boolean isToggleDynamic;
		private boolean isToggleLarger;

		public ToggleAction(boolean isToggleDynamic, boolean isToggleLarger)
		{
			this.isToggleDynamic = isToggleDynamic;
			this.isToggleLarger = isToggleLarger;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(this.isToggleDynamic)
			{
				setDynamicAdjustment(!TableColumnAdjuster.this.isDynamicAdjustment);
				return;
			}
			if(this.isToggleLarger)
			{
				setOnlyAdjustLarger(!TableColumnAdjuster.this.isOnlyAdjustLarger);
				return;
			}
		}
	}

	private JTable table;
	private int spacing;
	private boolean isColumnHeaderIncluded;
	private boolean isColumnDataIncluded;
	private boolean isOnlyAdjustLarger;
	private boolean isDynamicAdjustment;
	private Map<TableColumn, Integer> columnSizes = new HashMap<TableColumn, Integer>();

	public TableColumnAdjuster(JTable table)
	{
		this(table, 2);
	}

	public TableColumnAdjuster(JTable table, int spacing)
	{
		this.table = table;
		this.spacing = spacing;
		setColumnHeaderIncluded(true);
		setColumnDataIncluded(true);
		setOnlyAdjustLarger(true);
		setDynamicAdjustment(false);
		installActions();
	}

	public void adjustColumn(final int column)
	{
		TableColumn tableColumn = this.table.getColumnModel().getColumn(column);
		if(!tableColumn.getResizable())
			return;
		int columnHeaderWidth = getColumnHeaderWidth(column);
		int columnDataWidth = getColumnDataWidth(column);
		int preferredWidth = Math.max(columnHeaderWidth, columnDataWidth);
		updateTableColumn(column, preferredWidth);
	}

	public void adjustColumns()
	{
		TableColumnModel tcm = this.table.getColumnModel();
		for(int i = 0; i < tcm.getColumnCount(); i++)
			adjustColumn(i);
	}

	@Override
	public void propertyChange(PropertyChangeEvent e)
	{
		if("model".equals(e.getPropertyName()))
		{
			TableModel model = (TableModel) e.getOldValue();
			model.removeTableModelListener(this);
			model = (TableModel) e.getNewValue();
			model.addTableModelListener(this);
			adjustColumns();
		}
	}

	public void restoreColumns()
	{
		TableColumnModel tcm = this.table.getColumnModel();
		for(int i = 0; i < tcm.getColumnCount(); i++)
			restoreColumn(i);
	}

	public void setColumnDataIncluded(boolean isColumnDataIncluded)
	{
		this.isColumnDataIncluded = isColumnDataIncluded;
	}

	public void setColumnHeaderIncluded(boolean isColumnHeaderIncluded)
	{
		this.isColumnHeaderIncluded = isColumnHeaderIncluded;
	}

	public void setDynamicAdjustment(boolean isDynamicAdjustment)
	{
		if(this.isDynamicAdjustment != isDynamicAdjustment)
			if(isDynamicAdjustment)
			{
				this.table.addPropertyChangeListener(this);
				this.table.getModel().addTableModelListener(this);
			}
			else
			{
				this.table.removePropertyChangeListener(this);
				this.table.getModel().removeTableModelListener(this);
			}
		this.isDynamicAdjustment = isDynamicAdjustment;
	}

	public void setOnlyAdjustLarger(boolean isOnlyAdjustLarger)
	{
		this.isOnlyAdjustLarger = isOnlyAdjustLarger;
	}

	@Override
	public void tableChanged(TableModelEvent e)
	{
		if(!this.isColumnDataIncluded)
			return;
		if(e.getType() == TableModelEvent.UPDATE)
		{
			int column = this.table.convertColumnIndexToView(e.getColumn());
			if(this.isOnlyAdjustLarger)
			{
				int row = e.getFirstRow();
				TableColumn tableColumn = this.table.getColumnModel().getColumn(column);
				if(tableColumn.getResizable())
				{
					int width = getCellDataWidth(row, column);
					updateTableColumn(column, width);
				}
			}
			else
				adjustColumn(column);
		}
		else
			adjustColumns();
	}

	private int getCellDataWidth(int row, int column)
	{
		TableCellRenderer cellRenderer = this.table.getCellRenderer(row, column);
		Component c = this.table.prepareRenderer(cellRenderer, row, column);
		int width = c.getPreferredSize().width + this.table.getIntercellSpacing().width;
		return width;
	}

	private int getColumnDataWidth(int column)
	{
		if(!this.isColumnDataIncluded)
			return 0;
		int preferredWidth = 0;
		int maxWidth = this.table.getColumnModel().getColumn(column).getMaxWidth();
		for(int row = 0; row < this.table.getRowCount(); row++)
		{
			preferredWidth = Math.max(preferredWidth, getCellDataWidth(row, column));
			if(preferredWidth >= maxWidth)
				break;
		}
		return preferredWidth;
	}

	private int getColumnHeaderWidth(int column)
	{
		if(!this.isColumnHeaderIncluded)
			return 0;
		TableColumn tableColumn = this.table.getColumnModel().getColumn(column);
		Object value = tableColumn.getHeaderValue();
		TableCellRenderer renderer = tableColumn.getHeaderRenderer();
		if(renderer == null)
			renderer = this.table.getTableHeader().getDefaultRenderer();
		Component c = renderer.getTableCellRendererComponent(this.table, value, false, false, -1, column);
		return c.getPreferredSize().width;
	}

	private void installActions()
	{
		installColumnAction(true, true, "adjustColumn", "control ADD");
		installColumnAction(false, true, "adjustColumns", "control shift ADD");
		installColumnAction(true, false, "restoreColumn", "control SUBTRACT");
		installColumnAction(false, false, "restoreColumns", "control shift SUBTRACT");
		installToggleAction(true, false, "toggleDynamic", "control MULTIPLY");
		installToggleAction(false, true, "toggleLarger", "control DIVIDE");
	}

	private void installColumnAction(boolean isSelectedColumn, boolean isAdjust, String key, String keyStroke)
	{
		Action action = new ColumnAction(isSelectedColumn, isAdjust);
		KeyStroke ks = KeyStroke.getKeyStroke(keyStroke);
		this.table.getInputMap().put(ks, key);
		this.table.getActionMap().put(key, action);
	}

	private void installToggleAction(boolean isToggleDynamic, boolean isToggleLarger, String key, String keyStroke)
	{
		Action action = new ToggleAction(isToggleDynamic, isToggleLarger);
		KeyStroke ks = KeyStroke.getKeyStroke(keyStroke);
		this.table.getInputMap().put(ks, key);
		this.table.getActionMap().put(key, action);
	}

	private void restoreColumn(int column)
	{
		TableColumn tableColumn = this.table.getColumnModel().getColumn(column);
		Integer width = this.columnSizes.get(tableColumn);
		if(width != null)
		{
			this.table.getTableHeader().setResizingColumn(tableColumn);
			tableColumn.setWidth(width.intValue());
		}
	}

	private void updateTableColumn(int column, int width)
	{
		final TableColumn tableColumn = this.table.getColumnModel().getColumn(column);
		if(!tableColumn.getResizable())
			return;
		width += this.spacing;
		if(this.isOnlyAdjustLarger)
			width = Math.max(width, tableColumn.getPreferredWidth());
		this.columnSizes.put(tableColumn, new Integer(tableColumn.getWidth()));
		this.table.getTableHeader().setResizingColumn(tableColumn);
		tableColumn.setWidth(width);
	}
}