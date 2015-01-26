package se.jrat.stub.modules.startup;

import java.util.Map;

import se.jrat.stub.Configuration;

import com.redpois0n.oslib.OperatingSystem;

public class OperatingSystemCheckStartupModule extends StartupModule {
	
	public OperatingSystemCheckStartupModule(Map<String, String> config) {
		super(config);
	}
	
	public void run() throws Exception {
		String allowedOperatingSystems = Configuration.getConfig().get("os");

		boolean shutdown = true;

		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS && allowedOperatingSystems.contains("win")) {
			shutdown = false;
		} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX && allowedOperatingSystems.contains("mac")) {
			shutdown = false;
		} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.LINUX && allowedOperatingSystems.contains("linux")) {
			shutdown = false;
		} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.FREEBSD && allowedOperatingSystems.contains("freebsd")) {
			shutdown = false;
		} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.SOLARIS && allowedOperatingSystems.contains("solaris")) {
			shutdown = false;
		} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.OPENBSD && allowedOperatingSystems.contains("openbsd")) {
			shutdown = false;
		}


		if (shutdown) {
			System.exit(0);
		}
	}

}
