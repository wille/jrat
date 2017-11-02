package jrat.controller.ui.frames;

import jrat.api.Resources;
import jrat.controller.ui.panels.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("serial")
public class FrameBuildAdvanced extends BaseFrame {

    public enum Category {
        GENERAL("General", "id"),
        NETWORK("Network", "computer"),
        STARTUP("Installation", "update"),
        NOTICE("Message", "messagebox"),
        MUTEX("Mutex", "mutex"),
        TIMEOUT("Timeout", "timeout"),
        DELAY("Delay", "timer"),
        TRAY_ICON("Tray Icon", "glasses"),
        PERSISTANCE("Persistance", "persistance"),
        OUTPUT("File", "compile"),
        VIRTUALIZATION("Virtualization", "virtualization"),
        EXPORT("Export", "final");

        public final String text;

        private String icon;

        Category(String text, String icon) {
            this.text = text;
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
        this.tabs.addTab(category.text, category.getIcon(), panel);
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
