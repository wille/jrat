package com.redpois0n.threads;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.redpois0n.net.WebRequest;

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

			URL u = WebRequest.getUrl(url);
			BufferedReader reader = new BufferedReader(new InputStreamReader(u.openStream()));

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
