package jrat.controller.ui.frames;

import jrat.api.Resources;
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
import java.util.Map;


@SuppressWarnings("serial")
public class FrameBuildAdvanced extends BaseFrame {

    public enum Category {
        GENERAL("id"),
        NETWORK("computer"),
        STARTUP("update"),
        NOTICE("messagebox"),
        MUTEX("mutex"),
        TIMEOUT("timeout"),
        DELAY("timer"),
        TRAY_ICON("glasses"),
        PERSISTANCE("persistance"),
        OUTPUT("compile"),
        VIRTUALIZATION("virtualization"),
        EXPORT("final");

        private String icon;

        Category(String icon) {
            this.icon = icon;
        }

        public ImageIcon getIcon() {
            return Resources.getIcon(this.icon);
        }
    }

    private final Map<Category, JPanel> components = new HashMap<>();
    private final JTabbedPane tabs;

	public FrameBuildAdvanced() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameBuildAdvanced.class.getResource("/bug-edit.png")));
		setTitle("Build Stub");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 680, 360);

		setLayout(new BorderLayout(0, 0));

		tabs = new JTabbedPane();
		add(tabs, BorderLayout.CENTER);

        addTab(Category.GENERAL, new PanelBuildGeneral());
        addTab(Category.NETWORK, new PanelBuildNetwork());
        addTab(Category.STARTUP, new PanelBuildStartup());
        addTab(Category.NOTICE, new PanelBuildInstallMessage());
        addTab(Category.MUTEX, new PanelBuildMutex());
        addTab(Category.TIMEOUT, new PanelBuildTimeout());
        addTab(Category.DELAY, new PanelBuildDelay());
        addTab(Category.TRAY_ICON, new PanelBuildVisibility());
        addTab(Category.PERSISTANCE, new PanelBuildPersistance());
        addTab(Category.OUTPUT, new PanelBuildOutput());
        addTab(Category.VIRTUALIZATION, new PanelBuildVirtualization());
        addTab(Category.EXPORT, new PanelBuildFinal(this));
    }

    /**
     * Adds a new category/tab
     * @param category
     * @param panel
     */
    private void addTab(Category category, JPanel panel) {
        this.components.put(category, panel);
        this.tabs.addTab(category.toString(), category.getIcon(), panel);
    }

    /**
     * Returns a building panel component
     * @param category
     * @return
     */
    public JPanel getComponent(Category category) {
	    return this.components.get(category);
    }
}
