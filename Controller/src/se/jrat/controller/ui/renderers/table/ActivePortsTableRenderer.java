package se.jrat.controller.ui.renderers.table;

import iconlib.FileIconUtils;
import iconlib.IconUtils;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;


@SuppressWarnings("serial")
public class ActivePortsTableRenderer extends DefaultJTableCellRenderer {

	public static final Icon EXE_ICON = FileIconUtils.getIconFromExtension(".exe");
	public static final Icon PROCESS_ICON = IconUtils.getIcon("process-go");
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		String status = table.getValueAt(row, 3).toString();
		
		if (status.equalsIgnoreCase("TIME_WAIT")) {
			label.setForeground(Color.blue.brighter());
		} else if (isSelected) {
			label.setForeground(table.getSelectionForeground());
		} else {
			label.setForeground(Color.black);
		}

		return label;
	}
}
