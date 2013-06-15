package org.jrat.project.api.events;

import org.jrat.project.api.RATObject;

public class OnDisconnectEvent extends RATServerEvent {

	public OnDisconnectEvent(RATObject server) {
		super(server);
	}

}
