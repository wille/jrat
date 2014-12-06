package se.jrat.client.threads;

import se.jrat.client.Version;

public class ThreadCheckVersion extends Thread {

	public void run() {
		Version.checkVersion();
	}

}
