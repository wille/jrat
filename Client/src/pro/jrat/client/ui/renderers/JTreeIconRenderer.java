package pro.jrat.client.ui.renderers;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

@SuppressWarnings("serial")
public class JTreeIconRenderer extends DefaultTreeCellRenderer {

	public ImageIcon icon;

	public JTreeIconRenderer(ImageIcon icon) {
		this.icon = icon;
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		setIcon(icon);
		return this;
	}
}
