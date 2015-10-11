package io.jrat.common;

import java.io.File;

import com.redpois0n.oslib.AbstractOperatingSystem;
import com.redpois0n.oslib.OperatingSystem;
import com.redpois0n.oslib.windows.WindowsOperatingSystem;
import com.redpois0n.oslib.windows.WindowsVersion;

public class DropLocations {
	
	public static final int ROOT = 0; // C drive on Windows
	public static final int TEMP = 1; // Documents folder on Nix
	public static final int APPDATA = 2; // Appdata, Library on OS X and temp on Linux
	public static final int DESKTOP = 3; // Desktop
	public static final String[] STRINGS = new String[] { "root/C drive", "temp/documents (UNIX)", "appdata", "desktop" };

	public static File getFile(int i, String fileName) throws Exception {
		return getFile(i, fileName, ".jar");
	}
	
	public static File getFile(int i, String fileName, String extension) throws Exception {
		File file = null;

		AbstractOperatingSystem os = OperatingSystem.getOperatingSystem();

		if (i == 0 || os.getType() == OperatingSystem.WINDOWS && ((WindowsOperatingSystem) os).getVersion() == WindowsVersion.WINXP) {
			if (os.getType() == OperatingSystem.WINDOWS) {
				file = new File("C:\\" + fileName + extension);
			} else {
				file = new File("/" + fileName + extension);
			}
		} else if (i == 1) {
			if (os.getType() == OperatingSystem.WINDOWS) {
				file = File.createTempFile(fileName, extension);
			} else {
				file = new File(System.getProperty("user.home") + "/Documents/" + fileName + extension);
			}
		} else if (i == 2) {			
			if (os.getType() == OperatingSystem.WINDOWS) {
				file = new File(System.getenv("APPDATA") + "\\" + fileName + extension);
			} else if (os.getType() == OperatingSystem.OSX) {
				file = new File(System.getProperty("user.home") + "/Library/" + fileName + extension);
			} else {
				file = File.createTempFile(fileName, extension);
			}
		} else if (i == 3) {
			file = new File(System.getProperty("user.home") + "/Desktop/" + fileName + extension);
		}
		
		return file;
	}
}