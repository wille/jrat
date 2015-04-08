package se.jrat.controller.ui.renderers.table;

import iconlib.IconUtils;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;


@SuppressWarnings("serial")
public class LogTableRenderer extends DefaultJTableCellRenderer {

	public static final HashMap<String, Color> COLORS = new HashMap<String, Color>();
	public static final HashMap<String, ImageIcon> ICONS = new HashMap<String, ImageIcon>();

	static {
		COLORS.clear();
		COLORS.put("disconnect", Color.red);
		COLORS.put("error", Color.red);
		COLORS.put("connect", Color.blue);

		ICONS.clear();
		ICONS.put("disconnect", IconUtils.getIcon("log-error"));
		ICONS.put("error", IconUtils.getIcon("log-error"));
		ICONS.put("connect", IconUtils.getIcon("log-info"));
		ICONS.put("warning", IconUtils.getIcon("log-warning"));
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		String str = table.getValueAt(row, 0).toString();

		if (!isSelected) {
			label.setForeground(COLORS.get(str.toLowerCase()));
		}

		if (column == 0) {
			label.setIcon(ICONS.get(str.toLowerCase()));
		} else {
			label.setIcon(null);
		}

		return label;
	}
}
