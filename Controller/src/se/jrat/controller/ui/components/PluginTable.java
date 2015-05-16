package se.jrat.controller.ui.components;

import iconlib.IconUtils;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import jrat.api.Plugin;

@SuppressWarnings("serial")
public class PluginTable extends DefaultJTable {
	
	public static final ImageIcon ICON_PLUGIN = IconUtils.getIcon("plugin");
	
	public static final String COLUMN_NAME = "Name";
	public static final String COLUMN_AUTHOR = "Author";
	public static final String COLUMN_DESCRIPTION = "Description";
	public static final String COLUMN_VERSION = "Version";

	private PluginTableModel model;

	public PluginTable() {
		setDefaultRenderer(Object.class, new PluginsTableRenderer());
		
		model = new PluginTableModel();
		setModel(model);
		
		setRowHeight(25);
	}
	
	public PluginTableModel getPluginModel() {
		return model;
	}
	
	public class PluginTableModel extends DefaultTableModel {
		
		public PluginTableModel() {
			addColumn(COLUMN_NAME);
			addColumn(COLUMN_AUTHOR);
			addColumn(COLUMN_DESCRIPTION);
			addColumn(COLUMN_VERSION);
		}
		
		@Override
		public boolean isCellEditable(int i, int i1) {
			return false;
		}
	}
	
	private class PluginsTableRenderer extends DefaultTableCellRenderer {
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			Plugin plugin = (Plugin) table.getValueAt(row, 0);
			
			label.setIcon(null);
			
			String colname = table.getColumnName(column);

			if (colname.equals(COLUMN_NAME)) {				
				if (plugin.getIcon() != null) {
					label.setIcon(plugin.getIcon());
				} else {
					label.setIcon(ICON_PLUGIN);
				}
				
				label.setText(plugin.getName());
			} else if (colname.equals(COLUMN_AUTHOR)) {
				label.setText(plugin.getAuthor());
			} else if (colname.equals(COLUMN_DESCRIPTION)) {
				label.setText(plugin.getDescription());
			} else if (colname.equals(COLUMN_VERSION)) {
				label.setText(plugin.getVersion());
			}

			return label;
		}
	}
}
