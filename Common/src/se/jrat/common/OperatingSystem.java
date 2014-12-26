package se.jrat.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public enum OperatingSystem {

	WINDOWS,
	OSX,
	LINUX,
	SOLARIS,
	FREEBSD,
	OPENBSD,
	ANDROID,
	UNKNOWN;

	private static String shortName;
	private static String longName;

	public static OperatingSystem getOperatingSystem(String str) {
		str = str.toLowerCase();

		OperatingSystem os;

		if (str.contains("win")) {
			os = OperatingSystem.WINDOWS;
		} else if (str.contains("mac")) {
			os = OperatingSystem.OSX;
		} else if (str.contains("linux")) {
			os = OperatingSystem.LINUX;
		} else if (str.contains("solaris") || str.contains("sunos")) {
			os = OperatingSystem.SOLARIS;
		} else if (str.contains("freebsd")) {
			os = OperatingSystem.FREEBSD;
		} else if (str.contains("openbsd")) {
			os = OperatingSystem.OPENBSD;
		} else if (str.contains("android")) {
			os = OperatingSystem.ANDROID;
		} else {
			os = OperatingSystem.UNKNOWN;
		}

		return os;
	}

	public static OperatingSystem getOperatingSystem() {
		return getOperatingSystem(System.getProperty("os.name"));
	}

	public static String getShortOperatingSystem() {
		if (shortName == null) {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.LINUX) {
				try {
					BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/etc/os-release")));
					String s;
					while ((s = reader.readLine()) != null) {
						if (s.startsWith("NAME=")) {
							shortName = s.substring(5, s.length()).replace("\"", "");
							if (!shortName.toLowerCase().contains("linux")) {
								shortName += " Linux";
							}
							break;
						}
					}
					reader.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
				shortName = System.getProperty("os.name") + " " + System.getProperty("os.version");
			} else {
				shortName = System.getProperty("os.name");
			}
		}

		return shortName;
	}

	public static String getLongOperatingSystem() {
		if (longName == null) {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.LINUX) {
				try {
					Process p = Runtime.getRuntime().exec(new String[] { "uname", "-a"});
					BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
					longName = reader.readLine();
					reader.close();
				} catch (Exception ex) {
					ex.printStackTrace();
					longName = System.getProperty("os.name") + " " + System.getProperty("os.version") + " " + OperatingSystem.getArch(System.getProperty("os.arch"));
				}
			} else {
				longName = System.getProperty("os.name") + " " + System.getProperty("os.version") + " " + OperatingSystem.getArch(System.getProperty("os.arch"));
			}
		}

		return longName;
	}

	public static String getArch(String arch) {
		if (arch.equalsIgnoreCase("x86") || arch.equalsIgnoreCase("i386") || arch.equalsIgnoreCase("i486") || arch.equalsIgnoreCase("i586") || arch.equalsIgnoreCase("i686")) {
			arch = "x86";
		} else if (arch.equalsIgnoreCase("x86_64") || arch.equalsIgnoreCase("amd64") || arch.equalsIgnoreCase("k8")) {
			arch = "64-bit";
		}
		
		return arch;
	}

}
