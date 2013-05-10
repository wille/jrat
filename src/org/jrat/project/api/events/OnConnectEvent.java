package org.jrat.project.api.events;

import org.jrat.project.api.RATServer;

public class OnConnectEvent extends RATServerEvent {

	public OnConnectEvent(RATServer server) {
		super(server);
	}

}
