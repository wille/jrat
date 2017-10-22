package jrat.controller.ui.renderers.table;

import jrat.controller.ui.DefaultJTableCellRenderer;
import jrat.controller.utils.FlagUtils;

import javax.swing.*;
import java.awt.*;


@SuppressWarnings("serial")
public class LocaleTableRenderer extends DefaultJTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (column == 0) {
			label.setIcon(FlagUtils.getFlag(value.toString()));
		} else {
			label.setIcon(null);
		}

		if (label.getText().length() == 0) {
			label.setText("?");
		}

		return label;
	}
}
