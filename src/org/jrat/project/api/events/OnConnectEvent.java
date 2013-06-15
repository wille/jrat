package org.jrat.project.api.events;

import org.jrat.project.api.RATObject;

public class OnConnectEvent extends RATServerEvent {

	public OnConnectEvent(RATObject server) {
		super(server);
	}

}
