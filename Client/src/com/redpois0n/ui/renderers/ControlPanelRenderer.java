package com.redpois0n.ui.renderers;

import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

@SuppressWarnings("serial")
public class ControlPanelRenderer extends DefaultTreeCellRenderer {

	public HashMap<String, ImageIcon> icons = new HashMap<String, ImageIcon>();
	public boolean same;
	public ImageIcon icon;

	public ControlPanelRenderer() {
		same = false;
		icon = null;
	}

	public ControlPanelRenderer(boolean same, ImageIcon icon) {
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
			
		}
		return this;
	}
}
