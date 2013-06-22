package pro.jrat.threads;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import pro.jrat.net.WebRequest;


public class ThreadWhatsNew extends Thread {

	public String url;
	public boolean done = false;
	public List<String> lines;

	public ThreadWhatsNew(String url) {
		this.url = url;
	}

	public void run() {
		try {
			lines = new ArrayList<String>();

			BufferedReader reader = new BufferedReader(new InputStreamReader(WebRequest.getInputStream(url)));

			String s = null;
			while ((s = reader.readLine()) != null) {
				lines.add(lines.size(), s);
			}
		} catch (Exception ex) {
			lines.add("Error downloading list: " + ex.getMessage());
			ex.printStackTrace();
		}
		done = true;
	}

}
