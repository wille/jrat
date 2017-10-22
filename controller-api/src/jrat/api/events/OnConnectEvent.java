package jrat.api.events;

import jrat.api.Client;

public class OnConnectEvent extends RATObjectEvent {

	public OnConnectEvent(Client server) {
		super(server);
	}

}
