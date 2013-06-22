package pro.jrat.ui.renderers;

import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import pro.jrat.utils.IconUtils;



@SuppressWarnings("serial")
public class GroupTreeRenderer extends DefaultTreeCellRenderer {

	public HashMap<String, ImageIcon> icons = new HashMap<String, ImageIcon>();
	public static final ImageIcon group = IconUtils.getIcon("group");

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		this.setFont(new Font(this.getFont().getName(), Font.PLAIN, this.getFont().getSize()));

		ImageIcon icon = icons.get(value.toString().toLowerCase());
		if (icon != null && leaf) {
			setIcon(icon);
		} else {
			setIcon(group);
		}

		Object root = tree.getModel().getRoot();
		for (int i = 0; i < tree.getModel().getChildCount(root); i++) {
			if (tree.getModel().getChild(root, i).toString().equals(value.toString())) {
				this.setFont(new Font(this.getFont().getName(), Font.BOLD, this.getFont().getSize()));
				break;
			}
		}

		return this;
	}
}
