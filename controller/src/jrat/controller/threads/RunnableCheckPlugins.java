package jrat.controller.threads;

import io.jrat.common.Logger;
import jrat.controller.LogAction;
import jrat.controller.Main;
import jrat.controller.addons.OnlinePlugin;
import jrat.controller.addons.Plugins;
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
