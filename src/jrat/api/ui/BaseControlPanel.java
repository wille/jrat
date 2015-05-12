package jrat.api.ui;

import javax.swing.JPanel;

import jrat.api.Client;

@SuppressWarnings("serial")
public abstract class BaseControlPanel extends JPanel {

	/**
	 * The server that holds this panel
	 */
	private Client client;

	public BaseControlPanel(Client client) {
		this.client = client;
	}
	
	/**
	 * 
	 * @param client
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * 
	 * @return The server this panel holds
	 */
	public Client getClient() {
		return client;
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
