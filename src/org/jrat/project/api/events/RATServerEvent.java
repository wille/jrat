package org.jrat.project.api.events;

import org.jrat.project.api.RATObject;

public abstract class RATServerEvent {

	private RATObject server;

	public RATServerEvent(RATObject server) {
		this.server = server;
	}

	public RATObject getServer() {
		return server;
	}

	public void setSlave(RATObject server) {
		this.server = server;
	}

}
