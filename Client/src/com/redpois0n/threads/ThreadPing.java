package com.redpois0n.threads;

import com.redpois0n.Main;
import com.redpois0n.Slave;


public class ThreadPing extends Thread {

	public ThreadPing() {
		super("Ping thread");
	}

	public void run() {
		while (true) {
			try {
				for (int i = 0; i < Main.connections.size(); i++) {
					Slave slave = Main.connections.get(i);
					slave.ping();
				}
				Thread.sleep(2500L);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
