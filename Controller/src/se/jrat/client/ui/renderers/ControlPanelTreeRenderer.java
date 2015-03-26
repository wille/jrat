package se.jrat.client.ui.renderers;

import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JTree;

import se.jrat.client.ui.components.DisabledDefaultMutableTreeNode;

@SuppressWarnings("serial")
public class ControlPanelTreeRenderer extends DefaultJTreeCellRenderer {
	
	public final Map<String, ImageIcon> icons = new HashMap<String, ImageIcon>();
	public boolean same;
	public ImageIcon icon;

	public ControlPanelTreeRenderer() {
		same = false;
		icon = null;
	}

	public ControlPanelTreeRenderer(boolean same, ImageIcon icon) {
		this.same = same;
		this.icon = icon;
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		this.setFont(new Font(this.getFont().getName(), Font.PLAIN, this.getFont().getSize()));

		if (same && icon != null && leaf) {
			setIcon(icon);
		} else {
			ImageIcon icon = icons.get(value.toString().toLowerCase());
			if (icon != null) {
				setIcon(icon);
			}

			Object root = tree.getModel().getRoot();
			for (int i = 0; i < tree.getModel().getChildCount(root); i++) {
				if (tree.getModel().getChild(root, i).toString().equals(value.toString())) {
					this.setFont(new Font(this.getFont().getName(), Font.BOLD, this.getFont().getSize()));
					break;
				}
			}
			
			setEnabled(!(value instanceof DisabledDefaultMutableTreeNode));
		}
		
		return this;
	}
}
