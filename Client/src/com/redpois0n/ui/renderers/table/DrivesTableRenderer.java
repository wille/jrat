package com.redpois0n.ui.renderers.table;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.redpois0n.utils.IconUtils;


@SuppressWarnings("serial")
public class DrivesTableRenderer extends DefaultTableCellRenderer {
	
	public final HashMap<String, Icon> drives = new HashMap<String, Icon>();
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if (value != null && column == 0) {
			Icon icon = null;
			if (drives.containsKey(value.toString())) {
				icon = drives.get(value.toString());
			} else {
				icon = IconUtils.getFileIcon(value.toString());
				drives.put(value.toString(), icon);
			}
			
			setIcon(icon);
		} else {
			lbl.setIcon(null);
		}
		
		return lbl;
	}
}
