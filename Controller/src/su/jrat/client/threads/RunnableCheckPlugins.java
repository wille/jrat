package su.jrat.client.threads;

import java.util.List;

import su.jrat.client.Main;
import su.jrat.client.addons.OnlinePlugin;
import su.jrat.client.addons.Plugin;
import su.jrat.client.addons.PluginLoader;
import su.jrat.client.ui.panels.PanelMainLog;

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
