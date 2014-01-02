package pro.jrat.client.ui.renderers.table;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import pro.jrat.client.utils.FlagUtils;

@SuppressWarnings("serial")
public class LocaleTableRenderer extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (column == 0) {
			lbl.setIcon(FlagUtils.getFlag(value.toString()));
		} else {
			lbl.setIcon(null);
		}

		if (lbl.getText().length() == 0) {
			lbl.setText("?");
		}

		return lbl;
	}
}
