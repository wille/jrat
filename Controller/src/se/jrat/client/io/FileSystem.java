package se.jrat.client.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import se.jrat.client.ui.components.FileTable;
import se.jrat.client.utils.IconUtils;

public class FileSystem {
	
	public static void addDir(String folder, FileTable table) {
		addDir(new File(folder), table);
	}
	
	public static void addDir(File folder, FileTable table) {
		if (folder.exists()) {
			File[] childs = folder.listFiles();
			List<File> files = new ArrayList<File>();
			List<File> dirs = new ArrayList<File>();

			if (childs != null) {
				for (File file : childs) {
					if (file.isDirectory()) {
						dirs.add(file);
					} else {
						files.add(file);
					}
				}
			}

			for (File file : dirs) {
				FileObject fo = new FileObject(file.getAbsolutePath());
				fo.setHidden(file.isHidden());
				fo.setIcon(IconUtils.getFileIcon(file));
				
				table.addFileObject(fo);
			}

			for (File file : files) {
				FileObject fo = new FileObject(file.getAbsolutePath());
				fo.setHidden(file.isHidden());
				fo.setIcon(IconUtils.getFileIcon(file));
				fo.setDate(file.lastModified());
				fo.setSize(file.length());
				
				table.addFileObject(fo);
			}
		}
	}

}
