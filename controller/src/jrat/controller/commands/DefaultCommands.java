package jrat.controller.commands;

import jrat.api.commands.Commands;


public class DefaultCommands {

	public static void addDefault() {
		Commands.COMMANDS.put("liststats", CommandListStatistics.class);
		Commands.COMMANDS.put("addstats", CommandAddStatistics.class);
		Commands.COMMANDS.put("help", CommandHelp.class);
		Commands.COMMANDS.put("socket", CommandSocket.class);
		Commands.COMMANDS.put("save", CommandSave.class);
	}

}
