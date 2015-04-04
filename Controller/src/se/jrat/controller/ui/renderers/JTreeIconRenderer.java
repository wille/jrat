package se.jrat.controller.ui.renderers;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;

@SuppressWarnings("serial")
public class JTreeIconRenderer extends DefaultJTreeCellRenderer {

	public ImageIcon icon;

	public JTreeIconRenderer(ImageIcon icon) {
		this.icon = icon;
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		label.setIcon(icon);
		return label;
	}
}
