package jrat.controller.ui.frames;

import iconlib.IconUtils;
import jrat.controller.ui.panels.*;
import jrat.controller.ui.renderers.JTreeIconsRenderer;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.HashMap;


@SuppressWarnings("serial")
public class FrameBuildAdvanced extends BaseFrame {

	public JTree tree;
	public HashMap<String, JPanel> panels = new HashMap<String, JPanel>();
	private JPanel contentPane;
	private JPanel panel;

	public FrameBuildAdvanced() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameBuildAdvanced.class.getResource("/icons/bug-edit.png")));
		setTitle("Build Stub");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 680, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JTreeIconsRenderer renderer = new JTreeIconsRenderer();

		renderer.getIconMap().put("final", IconUtils.getIcon("final", true));
		renderer.getIconMap().put("general", IconUtils.getIcon("id", true));
		renderer.getIconMap().put("network", IconUtils.getIcon("computer", true));
		renderer.getIconMap().put("startup", IconUtils.getIcon("update", true));
		renderer.getIconMap().put("install message", IconUtils.getIcon("messagebox", true));
		renderer.getIconMap().put("binder", IconUtils.getIcon("merge", true));
		renderer.getIconMap().put("mutex", IconUtils.getIcon("mutex", true));
		renderer.getIconMap().put("allowed os", IconUtils.getIcon("toolbox", true));
		renderer.getIconMap().put("timeout", IconUtils.getIcon("timeout", true));
		renderer.getIconMap().put("delay", IconUtils.getIcon("timer", true));
		renderer.getIconMap().put("host file", IconUtils.getIcon("leaf"));
		renderer.getIconMap().put("tray icon", IconUtils.getIcon("glasses"));
		renderer.getIconMap().put("error handling", IconUtils.getIcon("error"));
		renderer.getIconMap().put("persistance", IconUtils.getIcon("persistance"));
		renderer.getIconMap().put("debug messages", IconUtils.getIcon("application-detail"));
		renderer.getIconMap().put("classes", IconUtils.getIcon("class"));
		renderer.getIconMap().put("output", IconUtils.getIcon("compile"));
		renderer.getIconMap().put("virtualization", IconUtils.getIcon("virtualization"));

		panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.gray.brighter()));

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE))
					.addGap(0))
		);

		tree = new JTree();
		scrollPane.setViewportView(tree);
		tree.setShowsRootHandles(true);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				Object[] name = arg0.getPath().getPath();
				final JPanel pan = getPanelFromString(name);
				if (pan != null) {
					Runnable test = new Runnable() {
						public void run() {
							panel.removeAll();
							panel.add(pan);
							panel.revalidate();
							panel.repaint();
						}
					};
					SwingUtilities.invokeLater(test);
				}
			}
		});
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Build Stub\t") {
			{
				addNodes(this);
			}
		}));

		tree.setRootVisible(false);

		tree.setCellRenderer(renderer);
		contentPane.setLayout(gl_contentPane);

		loadPanels();

		panel.add(panels.get("general"));
	}

	public void addNodes(DefaultMutableTreeNode n) {
		n.add(new DefaultMutableTreeNode("General"));
		n.add(new DefaultMutableTreeNode("Network"));
		n.add(new DefaultMutableTreeNode("Startup"));
		n.add(new DefaultMutableTreeNode("Install Message"));
		n.add(new DefaultMutableTreeNode("Binder"));
		n.add(new DefaultMutableTreeNode("Mutex"));
		n.add(new DefaultMutableTreeNode("Allowed OS"));
		n.add(new DefaultMutableTreeNode("Timeout"));
		n.add(new DefaultMutableTreeNode("Delay"));
		n.add(new DefaultMutableTreeNode("Host File"));
		n.add(new DefaultMutableTreeNode("Error Handling"));
		n.add(new DefaultMutableTreeNode("Tray Icon"));
		n.add(new DefaultMutableTreeNode("Persistance"));
		n.add(new DefaultMutableTreeNode("Virtualization"));
		n.add(new DefaultMutableTreeNode("Debug Messages"));
		n.add(new DefaultMutableTreeNode("Classes"));
		n.add(new DefaultMutableTreeNode("Output"));
		n.add(new DefaultMutableTreeNode("Final"));
	}

	public JPanel getPanelFromString(Object[] obj) {
		if (obj.length == 2) {
			String s = obj[1].toString();
			return panels.get(s.toLowerCase());
		} else {
			return null;
		}
	}

	public void loadPanels() {
		panels.clear();
		panels.put("general", new PanelBuildGeneral());
		panels.put("network", new PanelBuildNetwork());
		panels.put("final", new PanelBuildFinal(this));
		panels.put("startup", new PanelBuildStartup());
		panels.put("install message", new PanelBuildInstallMessage());
		panels.put("binder", new PanelBuildBinder());
		panels.put("mutex", new PanelBuildMutex());
		panels.put("allowed os", new PanelBuildOS());
		panels.put("timeout", new PanelBuildTimeout());
		panels.put("delay", new PanelBuildDelay());
		panels.put("host file", new PanelBuildHostFile());
		panels.put("tray icon", new PanelBuildVisibility());
		panels.put("error handling", new PanelBuildError());
		panels.put("persistance", new PanelBuildPersistance());
		panels.put("debug messages", new PanelBuildDebugMessages());
		panels.put("classes", new PanelBuildClasses());
		panels.put("output", new PanelBuildOutput());
		panels.put("virtualization", new PanelBuildVirtualization());
	}
}
