package se.jrat.controller.ui.renderers.table;

import iconlib.FileIconUtils;
import iconlib.IconUtils;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;

import com.redpois0n.oslib.OperatingSystem;


@SuppressWarnings("serial")
public class ProcessTableRenderer extends DefaultJTableCellRenderer {

	public static final Icon EXE_ICON = FileIconUtils.getIconFromExtension(".exe");
	public static final Icon PROCESS_ICON = IconUtils.getIcon("process-go");
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (column == 0) {
			if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
				label.setIcon(EXE_ICON);
			} else {
				label.setIcon(PROCESS_ICON);
			}
		} else {
			label.setIcon(null);
		}
		
		if (column == 3) {
			label.setBackground(new Color(249, 236, 171));
			label.setForeground(Color.black);
		}

		return this;
	}
}
