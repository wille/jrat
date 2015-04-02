package se.jrat.client.threads;

import se.jrat.client.Version;

public class ThreadCheckVersion extends Thread {
	
	public ThreadCheckVersion() {
		super("Version checker thread");
	}

	public void run() {
		Version.checkVersion();
	}

}
