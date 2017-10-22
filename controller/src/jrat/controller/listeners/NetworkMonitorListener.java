package jrat.controller.listeners;

import jrat.controller.AbstractSlave;

public interface NetworkMonitorListener {
	
	void onUpdate(AbstractSlave slave, int in, int out);

}
