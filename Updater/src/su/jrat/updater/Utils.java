package su.jrat.updater;

import java.io.File;

public class Utils {
	
	public static File getJarFile() {
		return new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("file:", ""));
	}

}
