package org.jrat.project.api.events;

import org.jrat.project.api.RATServer;

public class OnDisconnectEvent extends RATServerEvent {

	public OnDisconnectEvent(RATServer server) {
		super(server);
	}

}
