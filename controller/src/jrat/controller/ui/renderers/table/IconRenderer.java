package jrat.controller.ui.renderers.table;

import jrat.controller.ui.DefaultJTableCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple table renderer that maps and renders an icon for
 * the first column in every row
 */
public class IconRenderer extends DefaultJTableCellRenderer {

	public final Map<Integer, Icon> icons = new HashMap<>();

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (value != null && column == 0) {
			setIcon(icons.get(row));
		} else {
			setIcon(null);
		}

		return this;
    }
}
