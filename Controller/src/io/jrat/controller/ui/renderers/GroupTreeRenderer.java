package io.jrat.controller.ui.renderers;

import iconlib.IconUtils;

import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;


@SuppressWarnings("serial")
public class GroupTreeRenderer extends DefaultJTreeCellRenderer {

	public static final ImageIcon ICON_GROUP = IconUtils.getIcon("group");
	
	private Map<String, ImageIcon> icons = new HashMap<String, ImageIcon>();
	
	public Map<String, ImageIcon> getIconMap() {
		return icons;
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		
		label.setFont(new Font(label.getFont().getName(), Font.PLAIN, label.getFont().getSize()));

		ImageIcon icon = icons.get(value.toString().toLowerCase());
		if (icon != null && leaf) {
			label.setIcon(icon);
		} else {
			label.setIcon(ICON_GROUP);
		}

		Object root = tree.getModel().getRoot();
		for (int i = 0; i < tree.getModel().getChildCount(root); i++) {
			if (tree.getModel().getChild(root, i).toString().equals(value.toString())) {
				label.setFont(new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize()));
				break;
			}
		}

		return label;
	}
}
