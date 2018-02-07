package io.jrat.controller;

import io.jrat.common.Logger;
import io.jrat.controller.settings.AbstractStorable;
import io.jrat.controller.settings.Settings;

public class ShutdownHook implements Runnable {

	@Override
	public void run() {
		Logger.log("Shutting down...");

		AbstractStorable.saveAllGlobals();

		try {
			Settings.getGlobal().save();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Globals.getLockFile().delete();
	}

}
