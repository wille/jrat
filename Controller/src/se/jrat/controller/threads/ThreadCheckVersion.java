package se.jrat.controller.threads;

import se.jrat.controller.Version;

public class ThreadCheckVersion extends Thread {
	
	public ThreadCheckVersion() {
		super("Version checker thread");
	}

	public void run() {
		Version.checkVersion();
	}

}
