package io.jrat.controller.listeners;

import io.jrat.controller.AbstractSlave;

public abstract interface NetworkMonitorListener {
	
	public abstract void onUpdate(AbstractSlave slave, int in, int out);

}
