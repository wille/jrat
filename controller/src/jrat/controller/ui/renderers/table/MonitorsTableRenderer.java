package jrat.controller.ui.renderers.table;

import iconlib.IconUtils;
import jrat.controller.ui.DefaultJTableCellRenderer;

import javax.swing.*;
import java.awt.*;


@SuppressWarnings("serial")
public class MonitorsTableRenderer extends DefaultJTableCellRenderer {

	public static final ImageIcon ICON_MONITOR = IconUtils.getIcon("monitor");

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (column == 0) {
			label.setIcon(ICON_MONITOR);
		} else {
			label.setIcon(null);
		}

		return label;
	}
}
