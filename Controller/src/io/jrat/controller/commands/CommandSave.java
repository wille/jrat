package io.jrat.controller.commands;

import io.jrat.controller.settings.AbstractStoreable;
import io.jrat.controller.settings.Settings;
import io.jrat.controller.settings.SettingsColumns;
import io.jrat.controller.settings.SettingsTheme;

import java.io.PrintStream;

import jrat.api.commands.AbstractCommand;

public class CommandSave extends AbstractCommand {

	@Override
	public void process(String[] args, PrintStream out) throws Exception {
		System.out.println("Saving settings...");
		
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

	@Override
	public String getDescription() {
		return "Saves all settings";
	}

	@Override
	public String getExample() {
		return "save";
	}

	@Override
	public String getUsage() {
		return "save";
	}

}
