package io.jrat.common;

import io.jrat.common.utils.Utils;
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
		File file = null;

		String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		
		AbstractOperatingSystem os = OperatingSystem.getOperatingSystem();

		if (i == ROOT && Utils.isRoot() || os.getType() == OperatingSystem.WINDOWS && ((WindowsOperatingSystem) os).getVersion() == WindowsVersion.WINXP) {
			if (os.getType() == OperatingSystem.WINDOWS) {
				file = new File("C:\\" + fileName);
			} else {
				file = new File("/" + fileName);
			}
		} else if (i == TEMP) {
			if (os.getType() == OperatingSystem.WINDOWS) {
				file = File.createTempFile(fileName, ext);
			} else {
				file = new File(System.getProperty("user.home") + "/Documents/" + fileName);
			}
		} else if (i == APPDATA) {
			if (os.getType() == OperatingSystem.WINDOWS) {
				file = new File(System.getenv("APPDATA") + "\\" + fileName);
			} else if (os.getType() == OperatingSystem.OSX) {
				file = new File(System.getProperty("user.home") + "/Library/" + fileName);
			} else {
				file = File.createTempFile(fileName, ext);
			}
		} else if (i == DESKTOP) {
			file = new File(System.getProperty("user.home") + "/Desktop/" + fileName);
		}
		
		return file;
	}
}
