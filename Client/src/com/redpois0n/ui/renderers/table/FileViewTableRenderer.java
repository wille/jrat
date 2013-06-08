package com.redpois0n.ui.renderers.table;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.redpois0n.FileBookmarks;
import com.redpois0n.Slave;
import com.redpois0n.utils.IconUtils;


@SuppressWarnings("serial")
public class FileViewTableRenderer extends DefaultTableCellRenderer {

	public static final ImageIcon bookmark = IconUtils.getIcon("bookmark");
	
	public HashMap<String, Icon> icons = new HashMap<String, Icon>();
	
	private final Slave slave;
	
	public FileViewTableRenderer(Slave slave) {
		setOpaque(true);
		this.slave = slave;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		Object oval = table.getValueAt(row, 3);
		String val = null;
		if (oval != null) {
			val = oval.toString().toLowerCase();
		}
		
		setForeground(Color.black);
		
		if (column == 0) {
			setIcon(icons.get(value.toString()));
		} else {
			setIcon(null);
		}
		
		if (val != null && val.length() > 0) {
			setForeground(Color.blue);
		} else if (column == 1 && FileBookmarks.getGlobal().contains(value.toString())) {
			setForeground(Color.red);
			setIcon(bookmark);
		}
		
		if (value != null) {
			if (column == 0 && value.toString().contains(slave.getFileSeparator()) && !value.toString().endsWith(slave.getFileSeparator())) {
				String name = value.toString().substring(value.toString().lastIndexOf(slave.getFileSeparator()) + 1, value.toString().length());
				setText(name);
			} else {
				setText(value.toString());
	 		}
		}
		
		return this;
	}
}
