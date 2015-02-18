package se.jrat.stub.modules.startup;

import java.util.Map;

import se.jrat.stub.Configuration;

public class InstallerStartupModule extends StartupModule {

	public InstallerStartupModule(Map<String, String> config) {
		super(config);
	}

	public void run() throws Exception {
		try {
			if (Boolean.parseBoolean(Configuration.getConfig().get("vm"))) {
				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
