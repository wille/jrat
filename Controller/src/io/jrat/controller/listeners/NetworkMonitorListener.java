package io.jrat.controller.listeners;

import io.jrat.controller.AbstractSlave;

public interface NetworkMonitorListener {
	
	void onUpdate(AbstractSlave slave, int in, int out);

}
