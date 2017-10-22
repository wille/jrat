package jrat.client.modules.startup;

import jrat.client.Configuration;
import jrat.client.Mutex;

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
