package se.jrat.client.ui.renderers;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;

import se.jrat.client.utils.IconUtils;


@SuppressWarnings("serial")
public class HelpTreeRenderer extends DefaultJTreeCellRenderer {

	public static final Icon ICON_FOLDER = IconUtils.getFileIconFromExtension(null, true);
	public static final Icon ICON_FILE = IconUtils.getFileIconFromExtension(".txt", false);

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
