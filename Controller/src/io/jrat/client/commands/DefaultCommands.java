package io.jrat.client.commands;

import io.jrat.api.commands.Commands;

public class DefaultCommands {

	public static void addDefault() {
		Commands.commands.put("liststats", CommandListStatistics.class);
		Commands.commands.put("addstats", CommandAddStatistics.class);
		Commands.commands.put("help", CommandHelp.class);
	}

}
