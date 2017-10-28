package jrat.controller.ui.renderers.table;

import jrat.api.Resources;
import jrat.controller.ui.DefaultJTableCellRenderer;

import javax.swing.*;
import java.awt.*;


@SuppressWarnings("serial")
public class SocketsTableRenderer extends DefaultJTableCellRenderer {

	public static final ImageIcon SOCKET = Resources.getIcon("socket-arrow");
	public static final ImageIcon SOCKET_DISABLED = Resources.getIcon("socket-remove");

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (column == 0) {
			if (value.toString().equals("Listening")) {
				label.setIcon(SOCKET);
			} else {
				label.setIcon(SOCKET_DISABLED);
			}
		} else {
			label.setIcon(null);
		}

		return label;
	}
}
