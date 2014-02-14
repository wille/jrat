package io.jrat.api.events;

import io.jrat.api.RATObject;

public class OnDisconnectEvent extends RATObjectEvent {

	public OnDisconnectEvent(RATObject server) {
		super(server);
	}

}
