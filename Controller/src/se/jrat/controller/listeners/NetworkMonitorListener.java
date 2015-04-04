package se.jrat.controller.listeners;

import se.jrat.controller.AbstractSlave;

public abstract interface NetworkMonitorListener {
	
	public abstract void onUpdate(AbstractSlave slave, int in, int out);

}
