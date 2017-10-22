package jrat.common.downloadable;

import java.io.File;

import oslib.OperatingSystem;


public class JavaArchive extends Downloadable {

	@Override
	public String getExtension() {
		return ".jar";
	}

	@Override
	public void execute(File file) throws Exception {
		executeJAR(file);
	}
	
	public static void executeJAR(File file) throws Exception {
		String javaHome = System.getProperty("java.home");
		
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
			Runtime.getRuntime().exec(new String[] { javaHome + "\\bin\\javaw.exe", "-jar", file.getAbsolutePath() });
		} else {
			Runtime.getRuntime().exec(new String[] { "java", "-jar", file.getAbsolutePath() });
		}
	}

}
