package su.jrat.stub.modules.startup;

import java.util.Map;

import su.jrat.common.OperatingSystem;

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
