package pro.jrat.api.events;

import pro.jrat.api.RATObject;

public class OnConnectEvent extends RATObjectEvent {

	public OnConnectEvent(RATObject server) {
		super(server);
	}

}
