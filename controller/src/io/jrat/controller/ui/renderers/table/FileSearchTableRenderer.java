package io.jrat.controller.ui.renderers.table;

import java.awt.Component;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import jrat.api.ui.DefaultJTableCellRenderer;

@SuppressWarnings("serial")
public class FileSearchTableRenderer extends DefaultJTableCellRenderer {

	public final HashMap<String, ImageIcon> icons = new HashMap<String, ImageIcon>();

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (value != null && column == 0) {
			label.setIcon(icons.get(value.toString()));
		} else {
			label.setIcon(null);
		}

		return label;
	}
}
