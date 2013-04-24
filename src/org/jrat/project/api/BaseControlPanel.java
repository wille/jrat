package org.jrat.project.api;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BaseControlPanel extends JPanel {
	
	/**
	 * The server that holds this panel
	 */
	private RATServer server;
	
	/**
	 * 
	 * @param server The server to make this panel hold
	 */
	public void setServer(RATServer server) {
		this.server = server;
	}
	
	/**
	 * 
	 * @return The server this panel holds
	 */
	public RATServer getServer() {
		return server;
	}

}
