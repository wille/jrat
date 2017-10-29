package jrat.module.fs.ui;

import jrat.common.utils.DataUnits;
import jrat.controller.AbstractSlave;
import jrat.controller.io.FileObject;
import jrat.controller.ui.DefaultJTableCellRenderer;
import jrat.controller.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;


@SuppressWarnings("serial")
public class FileViewTableRenderer extends DefaultJTableCellRenderer {

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
