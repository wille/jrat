package pro.jrat.threads;

import pro.jrat.Version;

public class ThreadCheckVersion extends Thread {

	public void run() {
		Version.checkVersion();
	}

}
