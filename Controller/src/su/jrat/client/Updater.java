package su.jrat.client;

import java.io.File;

import javax.swing.JOptionPane;

import su.jrat.client.utils.Utils;
import su.jrat.common.OperatingSystem;

public class Updater {

	public static final String JAVA_HOME;

	static {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
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
			Runtime.getRuntime().exec(new String[] { JAVA_HOME, "-jar", Globals.getUpdater().getAbsolutePath() }, null, Utils.getWorkingDir());
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			ErrorDialog.create(e);
		}
	}

}
