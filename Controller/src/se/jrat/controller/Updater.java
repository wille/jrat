package se.jrat.controller;

import java.io.File;

import javax.swing.JOptionPane;

import se.jrat.controller.utils.Utils;

import com.redpois0n.oslib.OperatingSystem;

public class Updater {

	public static final String JAVA_HOME;

	static {
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			JAVA_HOME = System.getProperty("java.home") + "\\bin\\javaw.exe";
		} else {
			JAVA_HOME = System.getProperty("java.home") + "/bin/java";
		}
	}

	public static void runUpdater() {
		try {
			if (!new File("Controller.jar").exists()) {
				JOptionPane.showMessageDialog(null, "Auto updating is for JAR installation only", "Updater", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (JOptionPane.showConfirmDialog(null, "Updating will close jRAT,  delete all files, and download and extract the new update\nDo you want to proceed?", "Update", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
				Runtime.getRuntime().exec(new String[] { JAVA_HOME, "-jar", Globals.getUpdater().getAbsolutePath() }, null, Utils.getWorkingDir());
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorDialog.create(e);
		}
	}

}
