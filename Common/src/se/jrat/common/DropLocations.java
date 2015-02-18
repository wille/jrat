package se.jrat.common;

import java.io.File;

import com.redpois0n.oslib.OperatingSystem;
import com.redpois0n.oslib.WindowsVersion;

public class DropLocations {
	
	public static final int ROOT = 0; // C drive on Windows
	public static final int TEMP = 1; // Documents folder on Nix
	public static final int APPDATA = 2; // Appdata, Library on OS X and temp on Linux
	public static final int DESKTOP = 3; // Desktop
	public static final String[] STRINGS = new String[] { "root/C drive", "temp/documents (UNIX)", "appdata", "desktop" };

	
	public static File getFile(int i, String fileName) throws Exception {
		File file = null;
				
		if (i == 0 || OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS && WindowsVersion.getFromString() == WindowsVersion.WINXP) {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				file = new File("C:\\" + fileName + ".jar");
			} else {
				file = new File("/" + fileName + ".jar");
			}
		} else if (i == 1) {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				file = File.createTempFile(fileName, ".jar");
			} else {
				file = new File(System.getProperty("user.home") + "/Documents/" + fileName + ".jar");
			}
		} else if (i == 2) {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				file = new File(System.getenv("APPDATA") + "\\" + fileName + ".jar");
			} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
				file = new File(System.getProperty("user.home") + "/Library/" + fileName + ".jar");
			} else {
				file = File.createTempFile(fileName, ".jar");
			}
		} else if (i == 3) {
			file = new File(System.getProperty("user.home") + "/Desktop/" + fileName + ".jar");
		}
		
		return file;
	}
}
