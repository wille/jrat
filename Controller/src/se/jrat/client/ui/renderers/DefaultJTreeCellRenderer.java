package se.jrat.client.ui.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

@SuppressWarnings("serial")
public class DefaultJTreeCellRenderer extends DefaultTreeCellRenderer {

	public static final Color SELECT_GRAY = new Color(191, 205, 219);
	
	public DefaultJTreeCellRenderer() {
		super();
		super.setBackgroundSelectionColor(SELECT_GRAY);
		super.setBorderSelectionColor(Color.gray);
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		
		label.setForeground(Color.black);

		return label;
	}
}
