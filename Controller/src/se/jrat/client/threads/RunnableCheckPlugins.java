package se.jrat.client.threads;

import java.util.List;

import se.jrat.client.Main;
import se.jrat.client.addons.OnlinePlugin;
import se.jrat.client.addons.Plugin;
import se.jrat.client.addons.PluginLoader;

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
						Main.instance.getPanelLog().addEntry("Warning", null, oplugin.getDisplayName() + " is not up to date");
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
