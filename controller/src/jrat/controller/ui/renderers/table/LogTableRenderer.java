package jrat.controller.ui.renderers.table;

import jrat.controller.LogAction;
import jrat.controller.ui.DefaultJTableCellRenderer;

import javax.swing.*;
import java.awt.*;


@SuppressWarnings("serial")
public class LogTableRenderer extends DefaultJTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		// First column should always be LogAction
		assert table.getValueAt(row, 0) instanceof LogAction;
		
		LogAction action = (LogAction) table.getValueAt(row, 0);

		if (!isSelected) {
			label.setForeground(action.getColor());
		}

		if (column == 0) {
			label.setIcon(action.getIcon());
			label.setText(action.getText());
		} else {
			label.setIcon(null);
		}

		return label;
	}
}
