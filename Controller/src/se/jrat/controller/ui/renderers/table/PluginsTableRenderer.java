package se.jrat.controller.ui.renderers.table;

import java.awt.Component;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;

import se.jrat.controller.Globals;
import se.jrat.controller.utils.IconUtils;


@SuppressWarnings("serial")
public class PluginsTableRenderer extends DefaultJTableCellRenderer {

	public static final ImageIcon PLUGIN_ICON = IconUtils.getIcon("plugin");

	private int iconColumn = 0;

	public PluginsTableRenderer() {

	}

	public PluginsTableRenderer(int iconColumn) {
		this.iconColumn = iconColumn;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (column == iconColumn) {
			String path = value.toString();
			
			File iconFile = new File(Globals.getPluginDirectory(), path.replace(" ", "").replace(".jar", "").replace("Stub", "") + File.separator + "icon.png");

			if (iconFile.exists()) {
				label.setIcon(new ImageIcon(iconFile.getAbsolutePath()));
			} else {
				label.setIcon(PLUGIN_ICON);
			}

		} else {
			label.setIcon(null);
		}

		return label;
	}
}
