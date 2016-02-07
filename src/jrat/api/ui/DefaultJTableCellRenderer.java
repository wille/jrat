package jrat.api.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class DefaultJTableCellRenderer extends DefaultTableCellRenderer {

	public static final Color SELECT_GRAY = new Color(191, 205, 219);
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (System.getProperty("jrat.theme").equals("true")) {
			if (isSelected) {
				label.setBackground(SELECT_GRAY);
			} else {
				label.setBackground(Color.white);
			}

			label.setForeground(Color.black);
		}

		return label;
	}

}
