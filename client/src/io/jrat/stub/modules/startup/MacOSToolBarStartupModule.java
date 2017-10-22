package io.jrat.stub.modules.startup;

import java.util.Map;
import oslib.OperatingSystem;

public class MacOSToolBarStartupModule extends StartupModule {
	
	public MacOSToolBarStartupModule(Map<String, String> config) {
		super(config);
	}
	
	public void run() throws Exception {
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS) {
			System.setProperty("apple.awt.UIElement", "true");
		}
	}

}
