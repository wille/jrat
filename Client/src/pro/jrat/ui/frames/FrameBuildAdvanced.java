package pro.jrat.ui.frames;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import pro.jrat.ui.panels.PanelBuildBinder;
import pro.jrat.ui.panels.PanelBuildClasses;
import pro.jrat.ui.panels.PanelBuildDebugMessages;
import pro.jrat.ui.panels.PanelBuildDelay;
import pro.jrat.ui.panels.PanelBuildError;
import pro.jrat.ui.panels.PanelBuildFinal;
import pro.jrat.ui.panels.PanelBuildGeneral;
import pro.jrat.ui.panels.PanelBuildHostFile;
import pro.jrat.ui.panels.PanelBuildInstallMessage;
import pro.jrat.ui.panels.PanelBuildMutex;
import pro.jrat.ui.panels.PanelBuildNetwork;
import pro.jrat.ui.panels.PanelBuildOS;
import pro.jrat.ui.panels.PanelBuildOutput;
import pro.jrat.ui.panels.PanelBuildPersistance;
import pro.jrat.ui.panels.PanelBuildPlugins;
import pro.jrat.ui.panels.PanelBuildStartup;
import pro.jrat.ui.panels.PanelBuildTimeout;
import pro.jrat.ui.panels.PanelBuildVisibility;
import pro.jrat.ui.renderers.JTreeIconsRenderer;
import pro.jrat.utils.IconUtils;



@SuppressWarnings("serial")
public class FrameBuildAdvanced extends BaseFrame {

	public JTree tree;
	public HashMap<String, JPanel> panels = new HashMap<String, JPanel>();
	private JPanel contentPane;
	private JPanel panel;

	public FrameBuildAdvanced() {
		super();

		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameBuildAdvanced.class.getResource("/icons/build.png")));
		setTitle("Build Server");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 630, 362);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JTreeIconsRenderer renderer = new JTreeIconsRenderer();
		
		renderer.icons.put("final", IconUtils.getIcon("final", true));
		renderer.icons.put("general", IconUtils.getIcon("id", true));
		renderer.icons.put("network", IconUtils.getIcon("host", true));
		renderer.icons.put("startup", IconUtils.getIcon("update", true));
		renderer.icons.put("install message", IconUtils.getIcon("messagebox", true));
		renderer.icons.put("binder", IconUtils.getIcon("binder", true));
		renderer.icons.put("mutex", IconUtils.getIcon("mutex", true));
		renderer.icons.put("allowed os", IconUtils.getIcon("toolbox", true));
		renderer.icons.put("plugins", IconUtils.getIcon("plugin", true));
		renderer.icons.put("timeout", IconUtils.getIcon("timeout", true));
		renderer.icons.put("delay", IconUtils.getIcon("timer", true));
		renderer.icons.put("host file", IconUtils.getIcon("leaf"));
		renderer.icons.put("tray icon", IconUtils.getIcon("glasses"));
		renderer.icons.put("error handling", IconUtils.getIcon("error"));
		renderer.icons.put("persistance", IconUtils.getIcon("persistance"));
		renderer.icons.put("debug messages", IconUtils.getIcon("application-detail"));
		renderer.icons.put("classes", IconUtils.getIcon("java_class"));
		renderer.icons.put("output", IconUtils.getIcon("compile"));

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
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 313, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
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
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Build Server\t") {
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
		n.add(new DefaultMutableTreeNode("Plugins"));
		n.add(new DefaultMutableTreeNode("Error Handling"));
		n.add(new DefaultMutableTreeNode("Tray Icon"));
		n.add(new DefaultMutableTreeNode("Persistance"));
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
		panels.put("plugins", new PanelBuildPlugins());
		panels.put("timeout", new PanelBuildTimeout());
		panels.put("delay", new PanelBuildDelay());
		panels.put("host file", new PanelBuildHostFile());
		panels.put("tray icon", new PanelBuildVisibility());
		panels.put("error handling", new PanelBuildError());
		panels.put("persistance", new PanelBuildPersistance());
		panels.put("debug messages", new PanelBuildDebugMessages());
		panels.put("classes", new PanelBuildClasses());
		panels.put("output", new PanelBuildOutput());
	}
}