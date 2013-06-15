package org.jrat.project.api.events;

import org.jrat.project.api.RATObject;

public class OnDisconnectEvent extends RATObjectEvent {

	public OnDisconnectEvent(RATObject server) {
		super(server);
	}

}
