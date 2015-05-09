package se.jrat.controller.threads;

import java.util.List;

import jrat.api.RATPlugin;
import se.jrat.controller.Main;
import se.jrat.controller.addons.OnlinePlugin;
import se.jrat.controller.addons.Plugins;

public class RunnableCheckPlugins implements Runnable {

	public void run() {
		try {
			List<OnlinePlugin> list = OnlinePlugin.getAvailablePlugins();
			
			for (OnlinePlugin oplugin : list) {
				RATPlugin plugin = Plugins.getPlugin(oplugin.getName());
				
				
				if (plugin != null) {
					String onlineVersion = oplugin.getVersion();
					String localVersion = plugin.getVersion();
					
					boolean upToDate = onlineVersion.equals(localVersion);
					
					if (!upToDate) {
						Main.debug(oplugin.getDisplayName() + " is not up to date");
						Main.instance.getPanelLog().addEntry("Warning", null, oplugin.getDisplayName() + " is not up to date");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
