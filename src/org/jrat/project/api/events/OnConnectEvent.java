package org.jrat.project.api.events;

import org.jrat.project.api.RATObject;

public class OnConnectEvent extends RATObjectEvent {

	public OnConnectEvent(RATObject server) {
		super(server);
	}

}
