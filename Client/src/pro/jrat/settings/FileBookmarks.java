package pro.jrat.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import pro.jrat.io.Files;


public class FileBookmarks extends AbstractSettings {

	private transient final List<File> bookmarks = new ArrayList<File>();

	private static final FileBookmarks instance = new FileBookmarks();

	public static FileBookmarks getGlobal() {
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
		String line;

		while ((line = reader.readLine()) != null) {
			bookmarks.add(new File(line));
		}

		reader.close();

	}

	@Override
	public void save() throws Exception {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getFile())));
		for (File file : bookmarks) {
			writer.write(file.getAbsolutePath());
			writer.newLine();
		}
		writer.close();
	}
	
	@Override
	public File getFile() {
		return new File(Files.getSettings(), ".bookmarks");
	}

}
