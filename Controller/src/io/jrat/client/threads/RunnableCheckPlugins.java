package io.jrat.client.threads;

import io.jrat.client.Main;
import io.jrat.client.addons.OnlinePlugin;
import io.jrat.client.addons.Plugin;
import io.jrat.client.addons.PluginLoader;
import io.jrat.client.ui.panels.PanelMainLog;

import java.util.List;

public class RunnableCheckPlugins implements Runnable {

	public void run() {
		try {
			List<OnlinePlugin> list = OnlinePlugin.getAvailablePlugins();
			
			for (OnlinePlugin oplugin : list) {
				Plugin plugin = PluginLoader.getPlugin(oplugin.getName());
				
				
				if (plugin != null) {
					String onlineVersion = oplugin.getVersion();
					String localVersion = plugin.getVersion();
					
					boolean upToDate = onlineVersion.equals(localVersion);
					
					if (!upToDate) {
						Main.debug(oplugin.getDisplayName() + " is not up to date");
						PanelMainLog.instance.addEntry("Warning", null, oplugin.getDisplayName() + " is not up to date");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
