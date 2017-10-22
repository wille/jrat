package io.jrat.stub.modules.startup;

import io.jrat.stub.Configuration;
import java.util.Map;
import oslib.OperatingSystem;

public class OperatingSystemCheckStartupModule extends StartupModule {
	
	public OperatingSystemCheckStartupModule(Map<String, String> config) {
		super(config);
	}
	
	public void run() throws Exception {
		String allowedOperatingSystems = Configuration.getConfig().get("os");

		boolean shutdown = true;

		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS && allowedOperatingSystems.contains("win")) {
			shutdown = false;
		} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS && allowedOperatingSystems.contains("mac")) {
			shutdown = false;
		} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.LINUX && allowedOperatingSystems.contains("linux")) {
			shutdown = false;
		} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.SOLARIS && allowedOperatingSystems.contains("solaris")) {
			shutdown = false;
		} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.BSD && allowedOperatingSystems.contains("bsd")) {
			shutdown = false;
		}


		if (shutdown) {
			System.exit(0);
		}
	}

}
