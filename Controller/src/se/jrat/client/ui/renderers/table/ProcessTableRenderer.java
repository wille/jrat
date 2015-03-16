package se.jrat.client.ui.renderers.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;

import se.jrat.client.ui.components.DefaultJTableCellRenderer;
import se.jrat.client.utils.IconUtils;

import com.redpois0n.oslib.OperatingSystem;


@SuppressWarnings("serial")
public class ProcessTableRenderer extends DefaultJTableCellRenderer {

	public static final Icon EXE_ICON = IconUtils.getFileIconFromExtension(".exe", false);
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
