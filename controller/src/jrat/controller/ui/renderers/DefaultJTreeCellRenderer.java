package jrat.controller.ui.renderers;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

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
