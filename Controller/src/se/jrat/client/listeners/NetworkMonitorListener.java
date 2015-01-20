package se.jrat.client.listeners;

public abstract interface NetworkMonitorListener {
	
	public abstract void onUpdate(int in, int out);

}
