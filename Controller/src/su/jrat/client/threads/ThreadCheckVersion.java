package su.jrat.client.threads;

import su.jrat.client.Version;

public class ThreadCheckVersion extends Thread {

	public void run() {
		Version.checkVersion();
	}

}
