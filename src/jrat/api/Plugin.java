package jrat.api;

import java.awt.event.ActionListener;

public abstract class Plugin {

	private String name;
	private String version;
	private String description;
	private String author;

	public Plugin(String name, String version, String description, String author) {
		this.name = name;
		this.version = version;
		this.description = description;
		this.author = author;
	}

	/**
	 * Note that this is the display name, like "Test Plugin", but the working
	 * name is "TestPlugin"
	 * 
	 * @return Name of the plugin
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @return Plugin version
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * 
	 * @return Plugin description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 
	 * @return Author of plugin
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * Called when the global menu item for this plugin is clicked
	 * 
	 * If returning null, will display information about the plugin instead
	 * @return
	 */
	public ActionListener getGlobalActionListener() {
		return null;
	}

}
