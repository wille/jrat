package io.jrat.updater;

import java.io.File;

public class Main {
	
	public static final String JAVA_HOME;

	static {
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			JAVA_HOME = System.getProperty("java.home") + "\\bin\\javaw.exe";
		} else {
			JAVA_HOME = System.getProperty("java.home") + "/bin/java";
		}
	}

	public static void main(String[] args) {
		try {
			File controller = new File(Utils.getJarFile().getParentFile().getParentFile(), "Controller.jar");
			if (!controller.exists()) {
				Logger.log("Not archive installation");
				System.exit(0);
			}
			
			Updater updater = new Updater();
			updater.deleteFiles();
			Downloader dl = new Downloader();
			dl.update();			
			
			Runtime.getRuntime().exec(new String[] { JAVA_HOME, "-jar", controller.getAbsolutePath() });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
