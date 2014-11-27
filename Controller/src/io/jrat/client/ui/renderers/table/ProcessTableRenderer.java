package io.jrat.client.ui.renderers.table;

import io.jrat.client.utils.IconUtils;
import io.jrat.common.OperatingSystem;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


@SuppressWarnings("serial")
public class ProcessTableRenderer extends DefaultTableCellRenderer {

	public static final Icon EXE_ICON = IconUtils.getFileIconFromExtension(".exe", false);
	public static final Icon PROCESS_ICON = IconUtils.getIcon("process");
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (column == 0) {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				setIcon(EXE_ICON);
			} else {
				setIcon(PROCESS_ICON);
			}
		} else {
			setIcon(null);
		}
		
		if (column == 3) {
			setBackground(new Color(249, 236, 171));
		} else if (isSelected) {
			setBackground(table.getSelectionBackground());
		} else {
			setBackground(Color.white);
		}

		return this;
	}
}
