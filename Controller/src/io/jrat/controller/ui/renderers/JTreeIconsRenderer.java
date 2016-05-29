package io.jrat.controller.ui.renderers;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;

@SuppressWarnings("serial")
public class JTreeIconsRenderer extends DefaultJTreeCellRenderer {

	private Map<String, Icon> icons = new HashMap<String, Icon>();
	private boolean same;
	private Icon icon;

	public JTreeIconsRenderer() {
		this.same = false;
		this.icon = null;
	}

	public JTreeIconsRenderer(boolean same, Icon icon) {
		this.same = same;
		this.icon = icon;
	}
	
	public Map<String, Icon> getIconMap() {
		return this.icons;
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		
		if (same && icon != null && leaf) {
			label.setIcon(icon);
		} else {
			Icon icon = icons.get(value.toString().toLowerCase());
			if (icon != null) {
				label.setIcon(icon);
			}
		}
		
		return label;
	}
}
