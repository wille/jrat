package jrat.api.events;

import jrat.api.RATObject;

public class OnDisconnectEvent extends RATObjectEvent {

	public OnDisconnectEvent(RATObject server) {
		super(server);
	}

}
