package pro.jrat.client.threads;

import pro.jrat.client.Version;

public class ThreadCheckVersion extends Thread {

	public void run() {
		Version.checkVersion();
	}

}
