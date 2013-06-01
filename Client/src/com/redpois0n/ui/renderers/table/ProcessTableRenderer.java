package com.redpois0n.ui.renderers.table;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.redpois0n.common.os.OperatingSystem;
import com.redpois0n.util.IconUtils;

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
		
		return this;
	}
}
