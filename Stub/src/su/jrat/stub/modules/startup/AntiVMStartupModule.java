package su.jrat.stub.modules.startup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import su.jrat.common.OperatingSystem;
import su.jrat.stub.Configuration;

public class AntiVMStartupModule extends StartupModule {

	public AntiVMStartupModule(Map<String, String> config) {
		super(config);
	}

	public void run() throws Exception {
		try {
			if (Boolean.parseBoolean(Configuration.getConfig().get("vm"))) {
				String command;
				if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
					command = "tasklist";
				} else {
					command = "ps -ax";
				}

				Process process = Runtime.getRuntime().exec(command);

				String total = "";

				BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String s;
				while ((s = br.readLine()) != null) {
					total = total + s + "\n";
				}

				br.close();

				total = total.toLowerCase();

				boolean vm = total.contains("vmtoolsd") || (total.contains("vmware") && !total.contains("vmware-tray"));

				if (vm) {
					System.exit(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
