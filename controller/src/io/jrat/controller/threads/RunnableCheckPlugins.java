package io.jrat.controller.threads;

import io.jrat.common.Logger;
import io.jrat.controller.LogAction;
import io.jrat.controller.Main;
import io.jrat.controller.addons.OnlinePlugin;
import io.jrat.controller.addons.Plugins;
import java.util.List;
import jrat.api.Plugin;

public class RunnableCheckPlugins implements Runnable {

	public void run() {
		try {
			List<OnlinePlugin> list = OnlinePlugin.getAvailablePlugins();
			
			for (OnlinePlugin oplugin : list) {
				Plugin plugin = Plugins.getPlugin(oplugin.getName());
				
				
				if (plugin != null) {
					String onlineVersion = oplugin.getVersion();
					String localVersion = plugin.getVersion();
					
					boolean upToDate = onlineVersion.equals(localVersion);
					
					if (!upToDate) {
						Logger.warn(oplugin.getDisplayName() + " is not up to date");
						Main.instance.getPanelLog().addEntry(LogAction.WARNING, null, oplugin.getDisplayName() + " is not up to date");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
