package su.jrat.updater;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		try {
			if (!new File(Utils.getJarFile().getParentFile().getParentFile(), "Controller.jar").exists()) {
				Logger.log("Not archive installation");
				System.exit(0);
			}
			
			Updater updater = new Updater();
			updater.deleteFiles();
			Downloader dl = new Downloader();
			dl.update();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
