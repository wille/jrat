package io.jrat.client.ui.renderers.table;

import io.jrat.client.utils.IconUtils;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


@SuppressWarnings("serial")
public class MonitorsTableRenderer extends DefaultTableCellRenderer {

	public static final ImageIcon monitor = IconUtils.getIcon("monitor");

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (column == 0) {
			setIcon(monitor);
		} else {
			setIcon(null);
		}

		return lbl;
	}
}
