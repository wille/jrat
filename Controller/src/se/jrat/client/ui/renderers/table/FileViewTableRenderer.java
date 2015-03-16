package se.jrat.client.ui.renderers.table;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;

import se.jrat.client.Slave;
import se.jrat.client.settings.FileBookmarks;
import se.jrat.client.ui.components.DefaultJTableCellRenderer;
import se.jrat.client.utils.IconUtils;


@SuppressWarnings("serial")
public class FileViewTableRenderer extends DefaultJTableCellRenderer {

	public static final ImageIcon bookmark = IconUtils.getIcon("bookmark");

	public HashMap<String, Icon> icons = new HashMap<String, Icon>();

	private final Slave slave;

	public FileViewTableRenderer(Slave slave) {
		this.slave = slave;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		Object oval = table.getValueAt(row, 3);
		String val = null;
		if (oval != null) {
			val = oval.toString().toLowerCase();
		}

		if (isSelected) {
			label.setForeground(Color.white);
		} else {
			label.setForeground(Color.black);
		}

		if (column == 0) {
			label.setIcon(icons.get(value.toString()));
		} else {
			label.setIcon(null);
		}

		if (val != null && val.length() > 0) {
			label.setForeground(Color.blue);
		} else if (column == 1 && FileBookmarks.getGlobal().contains(value.toString())) {
			label.setForeground(Color.red);
			label.setIcon(bookmark);
		}

		if (value != null) {
			if (column == 0 && value.toString().contains(slave.getFileSeparator()) && !value.toString().endsWith(slave.getFileSeparator())) {
				String name = value.toString().substring(value.toString().lastIndexOf(slave.getFileSeparator()) + 1, value.toString().length());
				label.setText(name);
			} else {
				label.setText(value.toString());
			}
		}

		return this;
	}
}
