package io.jrat.client.ui.renderers.table;

import io.jrat.client.utils.IconUtils;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


@SuppressWarnings("serial")
public class SocketsTableRenderer extends DefaultTableCellRenderer {

	public static final ImageIcon socket = IconUtils.getIcon("socket_arrow");
	public static final ImageIcon socket_disabled = IconUtils.getIcon("socket_remove");

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (column == 0) {
			if (value.toString().equals("Listening")) {
				lbl.setIcon(socket);
			} else {
				lbl.setIcon(socket_disabled);
			}
		} else {
			lbl.setIcon(null);
		}

		return lbl;
	}
}
