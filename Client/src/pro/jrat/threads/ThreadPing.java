package pro.jrat.threads;

import pro.jrat.Main;
import pro.jrat.Slave;


public class ThreadPing extends Thread {

	public ThreadPing() {
		super("Ping thread");
	}

	public void run() {
		while (true) {
			try {
				for (int i = 0; i < Main.connections.size(); i++) {
					Slave slave = Main.connections.get(i);
				
					if (!slave.isLocked()) {
						slave.ping();
					}
				}
				Thread.sleep(2500L);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
