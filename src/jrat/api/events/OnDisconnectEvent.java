package jrat.api.events;

import jrat.api.Client;

public class OnDisconnectEvent extends RATObjectEvent {

	public OnDisconnectEvent(Client server) {
		super(server);
	}

}
