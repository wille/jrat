package se.jrat.client.ui.renderers.table;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;

import se.jrat.client.ui.components.DefaultJTableCellRenderer;
import se.jrat.client.utils.IconUtils;


@SuppressWarnings("serial")
public class DrivesTableRenderer extends DefaultJTableCellRenderer {

	public final HashMap<String, Icon> drives = new HashMap<String, Icon>();

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (value != null && column == 0) {
			Icon icon = null;
			if (drives.containsKey(value.toString())) {
				icon = drives.get(value.toString());
			} else {
				icon = IconUtils.getFileIcon(value.toString());
				drives.put(value.toString(), icon);
			}

			label.setIcon(icon);
		} else {
			label.setIcon(null);
		}

		return label;
	}
}
