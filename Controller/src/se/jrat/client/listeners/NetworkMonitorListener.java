package se.jrat.client.listeners;

import se.jrat.client.AbstractSlave;

public abstract interface NetworkMonitorListener {
	
	public abstract void onUpdate(AbstractSlave slave, int in, int out);

}
