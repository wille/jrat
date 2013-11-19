package pro.jrat.client.ui.renderers.table;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import pro.jrat.client.utils.IconUtils;

@SuppressWarnings("serial")
public class LogTableRenderer extends DefaultTableCellRenderer {

	public static final HashMap<String, Color> colors = new HashMap<String, Color>();
	public static final HashMap<String, ImageIcon> icons = new HashMap<String, ImageIcon>();

	static {
		colors.clear();
		colors.put("disconnect", Color.red);
		colors.put("error", Color.red);
		colors.put("connect", Color.blue);

		icons.clear();
		icons.put("disconnect", IconUtils.getIcon("log_error"));
		icons.put("error", IconUtils.getIcon("log_error"));
		icons.put("connect", IconUtils.getIcon("log_info"));
		icons.put("warning", IconUtils.getIcon("log_warning"));
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		String str = table.getValueAt(row, 0).toString();

		if (!isSelected) {
			lbl.setForeground(colors.get(str.toLowerCase()));
		}

		if (column == 0) {
			lbl.setIcon(icons.get(str.toLowerCase()));
		} else {
			lbl.setIcon(null);
		}

		return this;
	}
}
