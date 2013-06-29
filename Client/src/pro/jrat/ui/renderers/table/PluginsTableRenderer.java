package pro.jrat.ui.renderers.table;

import java.awt.Component;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import pro.jrat.utils.IconUtils;



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
			String icon = "plugins/" + path.replace(" ", "") + "/icon.png";
			if (new File(icon).exists()) {
				lbl.setIcon(new ImageIcon(icon));
			} else {
				lbl.setIcon(PLUGIN_ICON);
			}
			
		} else {
			lbl.setIcon(null);
		}
		
		return lbl;
	}
}
