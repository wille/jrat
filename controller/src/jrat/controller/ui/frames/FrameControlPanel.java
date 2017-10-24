package jrat.controller.ui.frames;

import jrat.api.*;
import jrat.api.ui.ControlPanel;
import jrat.api.ui.ControlPanelAction;
import jrat.api.ui.ControlPanelItem;
import jrat.api.ui.ControlPanelTab;
import jrat.controller.Slave;
import jrat.controller.listeners.Performable;
import jrat.controller.ui.components.DisabledDefaultMutableTreeNode;
import jrat.controller.ui.renderers.ControlPanelTreeRenderer;

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
import java.util.Map;

@SuppressWarnings("serial")
public class FrameControlPanel extends BaseFrame implements TreeSelectionListener {

    /**
     * Contains the category treenodes
     */
    private Map<ControlPanel.Category, DefaultMutableTreeNode> categoryNodes = new HashMap<ControlPanel.Category, DefaultMutableTreeNode>();

	private JPanel contentPane;
	private JPanel panel;
	private JTree tree;

	public static final Map<Slave, FrameControlPanel> instances = new HashMap<Slave, FrameControlPanel>();

	public HashMap<String, JPanel> panels = new HashMap<String, JPanel>();
	public HashMap<String, Performable> actions = new HashMap<String, Performable>();
	private JTabbedPane tabbedPane;

	private ControlPanelTreeRenderer treeRenderer;

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public JTree getTree() {
		return tree;
	}

	public FrameControlPanel(Slave s) {
		super(s);

		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameControlPanel.class.getResource("/icons/controlpanel.png")));
		setTitle("Control Panel");
		final Slave sl = slave;
		instances.put(slave, this);

		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 807, 414);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		panel = new JPanel();
		panel.setSize(600, 350);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(panel, GroupLayout.PREFERRED_SIZE, 607, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false).addComponent(scrollPane, Alignment.LEADING).addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)).addContainerGap(12, Short.MAX_VALUE)));

		tree = new JTree();
		tree.setShowsRootHandles(true);
		tree.addTreeSelectionListener(this);
		tree.setRootVisible(false);
		treeRenderer = new ControlPanelTreeRenderer();
		tree.setCellRenderer(treeRenderer);
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Control Panel") {
			{
				addNodes(this);
			}
		}));
		scrollPane.setViewportView(tree);
		contentPane.setLayout(gl_contentPane);

		panel.setLayout(new BorderLayout(0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane, BorderLayout.CENTER);

		addTabs("System");
	}

    /**
     * Adds all tabs under a specific category
     * @param currentLabel category text (current treenode text)
     */
	public void addTabs(String currentLabel) {
        tabbedPane.removeAll();

        for (ControlPanelItem item : ControlPanel.ITEMS) {
            if (item.category.text.equals(currentLabel) && item instanceof ControlPanelTab) {
                tabbedPane.addTab(item.text, item.icon, ((ControlPanelTab) item).createPanel(slave));
            }
        }
	}

    /**
     * Builds the tree view
     * @param n master tree node
     */
	public void addNodes(DefaultMutableTreeNode n) {
	    for (ControlPanelItem item : ControlPanel.ITEMS) {
	        DefaultMutableTreeNode parent;

	        // if not category node has been created, create it and add it to the parent
            if (!categoryNodes.containsKey(item.category)) {
                categoryNodes.put(item.category, getTreeNode(item.category.text));
                n.add(categoryNodes.get(item.category));

                treeRenderer.icons.put(item.category.text, item.category.icon);
            }

            parent = categoryNodes.get(item.category);
            parent.add(getTreeNode(item.text));

            treeRenderer.icons.put(item.text, item.icon);
        }
	}

    public void valueChanged(TreeSelectionEvent e) {
        try {
            addTabs(e.getPath().getPath()[1].toString());
            String what = e.getPath().getPath()[2].toString();

            //boolean disabled = e.getPath().getLastPathComponent() instanceof DisabledDefaultMutableTreeNode;

            for (ControlPanelItem item : ControlPanel.ITEMS) {
                if (item.text.equals(what)) {
                    if (item instanceof ControlPanelTab) {
                        JPanel p = ((ControlPanelTab) item).createPanel(slave);
                        tabbedPane.setSelectedComponent(p);
                    } else if (item instanceof ControlPanelAction) {
                        ClientEventListener listener = (ClientEventListener) item.item;
                        listener.emit(getSlave());
                    }
                }
            }
        } catch (Exception ex) {

        }
    }
	
	private DefaultMutableTreeNode getTreeNode(String s) {
		return getTreeNode(s, true);
	}
	
	private DefaultMutableTreeNode getTreeNode(String s, boolean enabled) {
		if (enabled) {
			return new DefaultMutableTreeNode(s);
		} else {
			return new DisabledDefaultMutableTreeNode(s);
		}
	}
}
