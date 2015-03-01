package se.jrat.client.ui.components.pathtree;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public class PathTreeNode extends DefaultMutableTreeNode {
	
	private Icon icon;
	
	public PathTreeNode(String s, Icon icon) {
		super(s);
		this.icon = icon;
	}
	
	public Icon getIcon() {
		return this.icon;
	}
}
