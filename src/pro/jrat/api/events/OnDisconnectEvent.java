package pro.jrat.api.events;

import pro.jrat.api.RATObject;

public class OnDisconnectEvent extends RATObjectEvent {

	public OnDisconnectEvent(RATObject server) {
		super(server);
	}

}
