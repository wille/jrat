package io.jrat.stub.modules.startup;

import io.jrat.stub.Configuration;
import io.jrat.stub.utils.TrayIconUtils;

import java.util.Map;

public class TrayIconStartupModule extends StartupModule {
	
	public TrayIconStartupModule(Map<String, String> config) {
		super(config);
	}
	
	public void run() throws Exception {
		if (Boolean.parseBoolean(Configuration.getConfig().get("ti"))) {
			TrayIconUtils.show();
		}
	}

}
