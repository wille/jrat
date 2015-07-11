package se.jrat.controller.ui.renderers.table;

import iconlib.FileIconUtils;
import iconlib.IconUtils;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;

import se.jrat.common.ProcessData;

import com.redpois0n.oslib.OperatingSystem;


@SuppressWarnings("serial")
public class ProcessTableRenderer extends DefaultJTableCellRenderer {
	
	public static final Icon ICON_DEFAULT;
	
	static {
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			ICON_DEFAULT = FileIconUtils.getIconFromExtension(".exe");
		} else {
			ICON_DEFAULT = IconUtils.getIcon("process-go");
		}
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		ProcessData data = (ProcessData) table.getValueAt(row, column);
		
		if (column == 0 && data.getImage() != null) {
			label.setIcon(data.getIcon());
		} else if (column == 0) {
			label.setIcon(ICON_DEFAULT);
		} else {
			label.setIcon(null);
		}
		
		if (data != null && data.getData() != null && column < data.getData().length) {
			label.setText(data.getData()[column]);
		}
		
		if (column == 3) {
			label.setBackground(new Color(249, 236, 171));
			label.setForeground(Color.black);
		}

		return this;
	}
}
