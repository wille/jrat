package su.jrat.stub.modules.startup;

import java.util.Map;

import su.jrat.stub.Configuration;
import su.jrat.stub.Mutex;

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
