package io.jrat.controller.listeners;


public abstract interface GlobalNetworkMonitorListener {
	
	public abstract void onUpdate(int in, int out);

}
