package jrat.client.modules.startup;

import jrat.client.Plugin;
import java.util.Map;

public class PluginStartupModule extends StartupModule {

	public PluginStartupModule(Map<String, String> config) {
		super(config);
	}

	public void run() throws Exception {
		try {
			Plugin.load();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
