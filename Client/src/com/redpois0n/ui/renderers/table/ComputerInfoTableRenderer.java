package com.redpois0n.ui.renderers.table;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class ComputerInfoTableRenderer extends DefaultTableCellRenderer {
	
	public final HashMap<String, ImageIcon> icons = new HashMap<String, ImageIcon>();
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if (value != null && column == 0) {
			setIcon(icons.get(value.toString().toLowerCase()));
		} else {
			lbl.setIcon(null);
		}
		
		return lbl;
	}
}
