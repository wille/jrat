package io.jrat.controller.settings;

import io.jrat.controller.Globals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class StoreFileBookmarks extends AbstractStorable {

	private transient final List<File> bookmarks = new ArrayList<File>();

	private static final StoreFileBookmarks instance = new StoreFileBookmarks();

	public static StoreFileBookmarks getGlobal() {
		return instance;
	}

	public List<File> getBookmarks() {
		return bookmarks;
	}

	public boolean contains(File file) {
		return contains(file.getAbsolutePath());
	}

	public boolean contains(String file) {
		for (File local : bookmarks) {
			if (local.getAbsolutePath().replace("\\", "/").equalsIgnoreCase(file.replace("\\", "/"))) {
				return true;
			}
		}
		return false;
	}

	public void remove(File file) {
		for (int i = 0; i < bookmarks.size(); i++) {
			File local = bookmarks.get(i);
			if (local.getAbsolutePath().equalsIgnoreCase(file.getAbsolutePath())) {
				bookmarks.remove(i);
				break;
			}
		}
	}

	@Override
	public void load() throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(getFile())));
		
		reader.readLine();
		int len = Integer.parseInt(reader.readLine());
		
		for (int i = 0; i < len; i++) {
			bookmarks.add(new File(reader.readLine()));
		}
		
		
		reader.close();
	}

	@Override
	public void save() throws Exception {
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getFile())));
		
		pw.println("File browser bookmarks");
		pw.println(bookmarks.size());
		
		for (File file : bookmarks) {
			pw.println(file.getAbsolutePath());
		}
		
		pw.close();
	}

	@Override
	public File getFile() {
		return new File(Globals.getSettingsDirectory(), ".bookmarks");
	}

}
