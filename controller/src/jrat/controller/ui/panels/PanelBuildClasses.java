package jrat.controller.ui.panels;

import iconlib.IconUtils;
import jrat.controller.Globals;
import jrat.controller.ui.renderers.JTreeIconRenderer;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


@SuppressWarnings("serial")
public class PanelBuildClasses extends JPanel {

	public PanelBuildClasses() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 431, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE).addContainerGap(12, Short.MAX_VALUE)));

		JTree tree = new JTree();
		tree.setShowsRootHandles(true);
		tree.setRootVisible(false);
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Root") {
			{
				addNodes(this);
			}
		}));
		tree.setCellRenderer(new JTreeIconRenderer(IconUtils.getIcon("class")));
		scrollPane.setViewportView(tree);
		setLayout(groupLayout);

	}

	public void addNodes(DefaultMutableTreeNode n) {
		try {
			ZipFile zip = new ZipFile(Globals.getStub());

			Enumeration<? extends ZipEntry> entries = zip.entries();

			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				String name = entry.getName().toLowerCase();

				if (name.endsWith(".class")) {
					n.add(new DefaultMutableTreeNode(entry.getName().replace("/", ".").replace(".class", "")));
				}
			}
			zip.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
