package jrat.api.events;

import jrat.api.RATObject;

public class OnConnectEvent extends RATObjectEvent {

	public OnConnectEvent(RATObject server) {
		super(server);
	}

}
