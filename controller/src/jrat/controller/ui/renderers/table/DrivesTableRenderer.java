package jrat.controller.ui.renderers.table;

import iconlib.IconUtils;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import jrat.api.ui.DefaultJTableCellRenderer;

@SuppressWarnings("serial")
public class DrivesTableRenderer extends DefaultJTableCellRenderer {

	private final Map<String, Icon> drives = new HashMap<String, Icon>();

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (value != null && column == 0) {
			Icon icon = null;
			if (drives.containsKey(value.toString())) {
				icon = drives.get(value.toString());
			} else {
				icon = IconUtils.getIcon("drive");
				drives.put(value.toString(), icon);
			}

			label.setIcon(icon);
		} else {
			label.setIcon(null);
		}

		return label;
	}
}
