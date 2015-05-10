package jrat.api;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BaseControlPanel extends JPanel {

	/**
	 * The server that holds this panel
	 */
	private Client server;

	/**
	 * 
	 * @param server
	 *            The server to make this panel hold
	 */
	public void setServer(Client server) {
		this.server = server;
	}

	/**
	 * 
	 * @return The server this panel holds
	 */
	public Client getServer() {
		return server;
	}

	/**
	 * Called when a parent frame or control panel gets closed
	 */
	public void onClose() {

	}

	/**
	 * Called when parent frame or control panel has loaded this
	 */
	public void onLoad() {

	}

}
