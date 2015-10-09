package io.jrat.controller.ui.renderers.table;

import iconlib.IconUtils;
import io.jrat.common.utils.DataUnits;
import io.jrat.controller.AbstractSlave;
import io.jrat.controller.io.FileObject;
import io.jrat.controller.settings.StoreFileBookmarks;
import io.jrat.controller.utils.Utils;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;


@SuppressWarnings("serial")
public class FileViewTableRenderer extends DefaultJTableCellRenderer {

	public static final ImageIcon ICON_BOOKMARK = IconUtils.getIcon("bookmark");

	public HashMap<String, Icon> icons = new HashMap<String, Icon>();

	private final AbstractSlave slave;

	public FileViewTableRenderer(AbstractSlave slave) {
		this.slave = slave;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		FileObject fo = (FileObject) table.getValueAt(row, 0);

		if (isSelected) {
			label.setForeground(Color.white);
		} else {
			label.setForeground(Color.black);
		}

		if (column == 0) {
			label.setIcon(fo.getIcon());
		} else {
			label.setIcon(null);
		}

		if (fo.isHidden()) {
			label.setForeground(Color.blue);
		} else if (column == 1 && StoreFileBookmarks.getGlobal().contains(fo.getPath())) {
			label.setForeground(Color.red);
			label.setIcon(ICON_BOOKMARK);
		}
	
		String sep = slave == null ? File.separator : slave.getFileSeparator();

		if (column == 0 && fo.getPath().contains(sep) && !fo.getPath().endsWith(sep)) {
			String name = fo.getPath().substring(fo.getPath().lastIndexOf(sep) + 1, fo.getPath().length());
			label.setText(name);
		} else if (column == 1 && !fo.isDirectory()) {
			label.setText(DataUnits.getAsString(fo.getSize()));
		} else if (column == 2 && !fo.isDirectory()) {
			label.setText(Utils.getDate(fo.getDate()));
		} else if (column == 3 && fo.isHidden()) {
			label.setText("Yes");
		}

		return label;
	}
}
