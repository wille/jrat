package jrat.api.events;

import pluginlib.EventHandler;

public abstract class Event {
	
	private static EventHandler<Event> handler = new EventHandler<Event>();
	
	public static EventHandler<Event> getHandler() {
		return handler;
	}

	public abstract void perform(AbstractEvent event);
}
