package io.jrat.client.ui.renderers;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

@SuppressWarnings("serial")
public class JTreeIconsRenderer extends DefaultTreeCellRenderer {

	public HashMap<String, Icon> icons = new HashMap<String, Icon>();
	public boolean same;
	public Icon icon;

	public JTreeIconsRenderer() {
		same = false;
		icon = null;
	}

	public JTreeIconsRenderer(boolean same, Icon icon) {
		this.same = same;
		this.icon = icon;
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		if (same && icon != null && leaf) {
			setIcon(icon);
		} else {
			Icon icon = icons.get(value.toString().toLowerCase());
			if (icon != null) {
				setIcon(icon);
			}
		}
		return this;
	}
}
