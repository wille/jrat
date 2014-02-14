package io.jrat.api.events;

import io.jrat.api.RATObject;

public class OnConnectEvent extends RATObjectEvent {

	public OnConnectEvent(RATObject server) {
		super(server);
	}

}
