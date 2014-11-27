package io.jrat.client.threads;

import io.jrat.client.Version;

public class ThreadCheckVersion extends Thread {

	public void run() {
		Version.checkVersion();
	}

}
