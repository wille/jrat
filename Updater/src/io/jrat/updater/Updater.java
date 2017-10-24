package io.jrat.updater;

import java.io.File;

public class Updater {
	
	public void deleteFiles() {
		File file = Utils.getJarFile().getParentFile().getParentFile();
		
		delete(file);
	}
	
	private void delete(File dir) {
		for (File file : dir.listFiles()) {
			if (file.isDirectory() && !file.getName().contains("plugin")) {
				delete(file);
				Logger.log("Deleting directory " + file.getAbsolutePath());
			} else if (!file.getName().equals("Updater.jar") && !file.getName().equals("jrat.key")) {
				file.delete();
				Logger.log("Deleting file " + file.getAbsolutePath());
			} else {
				Logger.log("Skipping file/directory " + file.getAbsolutePath());
			}
		}
	}

}
