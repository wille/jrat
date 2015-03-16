package se.jrat.client.ui.renderers.table;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;

import se.jrat.client.ui.components.DefaultJTableCellRenderer;
import se.jrat.client.utils.FlagUtils;


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
