package io.jrat.controller;

import io.jrat.controller.settings.AbstractStoreable;
import io.jrat.controller.settings.Settings;
import io.jrat.controller.settings.SettingsColumns;
import io.jrat.controller.settings.SettingsTheme;
import jrat.api.events.Event;
import jrat.api.events.EventType;
import jrat.api.events.OnDisableEvent;

public class ShutdownHook implements Runnable {

	@Override
	public void run() {
		Main.debug("Shutting down...");
		
		OnDisableEvent event = new OnDisableEvent();
		for (Event e : Event.getHandler().getEvents(EventType.EVENT_PLUGIN_DISABLE)) {
			e.perform(event);
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
