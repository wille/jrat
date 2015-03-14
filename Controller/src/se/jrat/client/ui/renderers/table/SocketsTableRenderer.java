package se.jrat.client.ui.renderers.table;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import se.jrat.client.utils.IconUtils;


@SuppressWarnings("serial")
public class SocketsTableRenderer extends DefaultTableCellRenderer {

	public static final ImageIcon SOCKET = IconUtils.getIcon("socket-arrow");
	public static final ImageIcon SOCKET_DISABLED = IconUtils.getIcon("socket-remove");

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (column == 0) {
			if (value.toString().equals("Listening")) {
				lbl.setIcon(SOCKET);
			} else {
				lbl.setIcon(SOCKET_DISABLED);
			}
		} else {
			lbl.setIcon(null);
		}

		return lbl;
	}
}
