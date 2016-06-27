package io.jrat.stub.modules.startup;

import java.util.Map;
import oslib.OperatingSystem;

public class OSXToolBarStartupModule extends StartupModule {
	
	public OSXToolBarStartupModule(Map<String, String> config) {
		super(config);
	}
	
	public void run() throws Exception {
		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
			System.setProperty("apple.awt.UIElement", "true");
		}
	}

}
