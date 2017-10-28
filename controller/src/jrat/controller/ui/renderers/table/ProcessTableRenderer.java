package jrat.controller.ui.renderers.table;

import iconlib.FileIconUtils;
import jrat.api.Resources;
import jrat.common.ProcessData;
import jrat.controller.ui.DefaultJTableCellRenderer;
import oslib.OperatingSystem;

import javax.swing.*;
import java.awt.*;


@SuppressWarnings("serial")
public class ProcessTableRenderer extends DefaultJTableCellRenderer {
	
	public static final Icon ICON_DEFAULT;
	
	static {
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			ICON_DEFAULT = FileIconUtils.getIconFromExtension(".exe");
		} else {
			ICON_DEFAULT = Resources.getIcon("process-go");
		}
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		ProcessData data = (ProcessData) table.getValueAt(row, 0);
		
		if (column == 0 && data.getImage() != null) {
			label.setIcon(data.getIcon());
		} else if (column == 0) {
			label.setIcon(ICON_DEFAULT);
		} else {
			label.setIcon(null);
		}
		
		if (data != null && data.getData() != null) {
			label.setText(data.getData()[column]);
		}
		
		if (column == 3) {
			label.setBackground(new Color(249, 236, 171));
			label.setForeground(Color.black);
		}

		return label;
	}
}
