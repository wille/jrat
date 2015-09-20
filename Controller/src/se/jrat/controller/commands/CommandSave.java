package se.jrat.controller.commands;

import java.io.PrintStream;

import jrat.api.commands.AbstractCommand;
import se.jrat.controller.settings.AbstractStoreable;
import se.jrat.controller.settings.Settings;
import se.jrat.controller.settings.SettingsColumns;
import se.jrat.controller.settings.SettingsTheme;

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
