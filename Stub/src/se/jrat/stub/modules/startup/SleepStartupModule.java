package se.jrat.stub.modules.startup;

import java.util.Map;

import se.jrat.stub.Configuration;

import com.redpois0n.oslib.DesktopEnvironment;
import com.redpois0n.oslib.DesktopEnvironment.Family;

public class SleepStartupModule extends StartupModule {

	public SleepStartupModule(Map<String, String> config) {
		super(config);
	}

	public void run() throws Exception {
		try {
			boolean correctenv = DesktopEnvironment.getFromCurrentDesktopString().getFamily() == Family.GNOME || DesktopEnvironment.getFromCurrentDesktopString().getFamily() == Family.UNITY;

			if (Boolean.parseBoolean(Configuration.getConfig().get("delay")) && !correctenv) {
				Thread.sleep(Long.parseLong(Configuration.getConfig().get("delayms")));				
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
