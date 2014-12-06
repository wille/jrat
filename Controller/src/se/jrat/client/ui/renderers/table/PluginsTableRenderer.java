package se.jrat.client.ui.renderers.table;

import java.awt.Component;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import se.jrat.client.Globals;
import se.jrat.client.utils.IconUtils;


@SuppressWarnings("serial")
public class PluginsTableRenderer extends DefaultTableCellRenderer {

	public static final ImageIcon PLUGIN_ICON = IconUtils.getIcon("plugin");

	private int iconColumn = 0;

	public PluginsTableRenderer() {

	}

	public PluginsTableRenderer(int iconColumn) {
		this.iconColumn = iconColumn;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (column == iconColumn) {
			String path = value.toString();
			
			File iconFile = new File(Globals.getPluginDirectory(), path.replace(" ", "") + "/icon.png");

			if (iconFile.exists()) {
				lbl.setIcon(new ImageIcon(iconFile.getAbsolutePath()));
			} else {
				lbl.setIcon(PLUGIN_ICON);
			}

		} else {
			lbl.setIcon(null);
		}

		return lbl;
	}
}
