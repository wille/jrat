package jrat.controller.ui.renderers;

import iconlib.FileIconUtils;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class HelpTreeRenderer extends DefaultJTreeCellRenderer {

	public static final Icon ICON_FOLDER = FileIconUtils.getFolderIcon();
	public static final Icon ICON_FILE = FileIconUtils.getIconFromExtension(".txt");

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		if (leaf) {
			label.setIcon(ICON_FILE);
		} else {
			label.setIcon(ICON_FOLDER);
		}

		return label;
	}
}
