package io.jrat.stub.modules.startup;

import io.jrat.stub.Plugin;

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

		for (Plugin plugin : Plugin.list) {
			plugin.methods.get("onstart").invoke(plugin.instance, new Object[] {});
		}
	}

}
