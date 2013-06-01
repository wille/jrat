package com.redpois0n.threads;

import com.redpois0n.Version;

public class ThreadCheckVersion extends Thread {

	public void run() {
		Version.checkVersion();
	}

}
