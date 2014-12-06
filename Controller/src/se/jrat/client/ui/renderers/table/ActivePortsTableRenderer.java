package se.jrat.client.ui.renderers.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import se.jrat.client.utils.IconUtils;


@SuppressWarnings("serial")
public class ActivePortsTableRenderer extends DefaultTableCellRenderer {

	public static final Icon EXE_ICON = IconUtils.getFileIconFromExtension(".exe", false);
	public static final Icon PROCESS_ICON = IconUtils.getIcon("process");
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		String status = table.getValueAt(row, 3).toString();
		
		if (status.equalsIgnoreCase("TIME_WAIT")) {
			setForeground(Color.blue.brighter());
		} else if (isSelected) {
			setForeground(table.getSelectionForeground());
		} else {
			setForeground(Color.black);
		}

		return this;
	}
}
