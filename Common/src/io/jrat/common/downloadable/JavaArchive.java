package io.jrat.common.downloadable;

import io.jrat.common.OperatingSystem;

import java.io.File;


public class JavaArchive extends Downloadable {

	@Override
	public String getExtension() {
		return ".jar";
	}

	@Override
	public void execute(File file) throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec(new String[] { System.getProperty("java.home") + "\\bin\\javaw.exe", "-jar", file.getAbsolutePath() });
		} else {
			Runtime.getRuntime().exec(new String[] { "java", "-jar", file.getAbsolutePath() });
		}
	}

}
