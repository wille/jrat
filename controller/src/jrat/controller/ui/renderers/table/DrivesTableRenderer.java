package jrat.controller.ui.renderers.table;

import jrat.api.Resources;
import jrat.controller.ui.DefaultJTableCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class DrivesTableRenderer extends DefaultJTableCellRenderer {

	private final Map<String, Icon> drives = new HashMap<>();

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (value != null && column == 0) {
			Icon icon = null;
			if (drives.containsKey(value.toString())) {
				icon = drives.get(value.toString());
			} else {
				icon = Resources.getIcon("drive");
				drives.put(value.toString(), icon);
			}

			label.setIcon(icon);
		} else {
			label.setIcon(null);
		}

		return label;
	}
}
