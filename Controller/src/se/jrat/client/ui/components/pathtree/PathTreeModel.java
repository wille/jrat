package se.jrat.client.ui.components.pathtree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

@SuppressWarnings("serial")
public class PathTreeModel extends DefaultTreeModel {
	
	private TreeNode rootNode;
	
	public PathTreeModel(TreeNode root) {
		super(root);
		this.rootNode = root;
	}

	public void addRoot(DefaultMutableTreeNode root) {
		super.insertNodeInto(root, (DefaultMutableTreeNode) rootNode, 0);
	}
	
	public TreeNode getRootNode() {
		return rootNode;
	}

}
