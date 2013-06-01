package com.redpois0n;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.redpois0n.io.Files;

public class FileBookmarks {
	
	public static final List<File> paths = new ArrayList<File>();
	
	public static boolean contains(File file) {
		for (File local : paths) {
			if (local.getAbsolutePath().equalsIgnoreCase(file.getAbsolutePath())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean contains(String file) {
		for (File local : paths) {
			if (local.getAbsolutePath().equalsIgnoreCase(file)) {
				return true;
			}
		}
		return false;
	}
	
	public static void remove(File file) {
		for (int i = 0; i < paths.size(); i++) {
			File local = paths.get(i);
			if (local.getAbsolutePath().equalsIgnoreCase(file.getAbsolutePath())) {
				paths.remove(i);
				break;
			}
		}
	}
	
	public static void load() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(Files.getSettings(), "bookmarks.dat"))));
			String line;
			
			while ((line = reader.readLine()) != null) {
				paths.add(new File(line));
			}
			
			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void save() {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(Files.getSettings(), "bookmarks.dat"))));
			for (File file : paths) {
				writer.write(file.getAbsolutePath());
				writer.newLine();
			}
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorDialog.create(ex);
		}
	}

}
