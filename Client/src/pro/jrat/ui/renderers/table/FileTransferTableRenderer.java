package pro.jrat.ui.renderers.table;

import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class FileTransferTableRenderer extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (column == 2) {
			JProgressBar bar = new JProgressBar();
			bar.setValue(Integer.parseInt(value.toString()));
			return bar;
		}

		return this;
	}
}
