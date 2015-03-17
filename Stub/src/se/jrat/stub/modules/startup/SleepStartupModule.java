package se.jrat.stub.modules.startup;

import java.util.Map;

import se.jrat.stub.Configuration;

public class SleepStartupModule extends StartupModule {

	public SleepStartupModule(Map<String, String> config) {
		super(config);
	}

	@Override
	public void run() throws Exception {
        if (Boolean.parseBoolean(Configuration.getConfig().get("delay"))) {
            Thread.sleep(Long.parseLong(Configuration.getConfig().get("delayms")));                
        }            

	}

}
