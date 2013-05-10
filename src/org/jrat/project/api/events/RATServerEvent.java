package org.jrat.project.api.events;

import org.jrat.project.api.RATServer;

public abstract class RATServerEvent {

	private RATServer server;

	public RATServerEvent(RATServer server) {
		this.server = server;
	}

	public RATServer getServer() {
		return server;
	}

	public void setSlave(RATServer server) {
		this.server = server;
	}

}
