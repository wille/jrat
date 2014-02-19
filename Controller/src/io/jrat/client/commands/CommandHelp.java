package io.jrat.client.commands;

import io.jrat.api.commands.AbstractCommand;
import io.jrat.api.commands.Commands;

import java.io.PrintStream;

public class CommandHelp extends AbstractCommand {

	@Override
	public String getDescription() {
		return "Help command";
	}

	@Override
	public String getExample() {
		return "help";
	}

	@Override
	public String getUsage() {
		return "help";
	}

	@Override
	public void process(String[] arg0, PrintStream out) throws Exception {

		for (String command : Commands.commands.keySet()) {
			Class<? extends AbstractCommand> acommand = Commands.commands.get(command);
			AbstractCommand cmd = acommand.newInstance();

			out.println("--------------------------");
			out.println(command);
			out.println("\tDescription: " + cmd.getDescription());
			out.println("\tExample: " + cmd.getExample());
			out.println("\tUsage: " + cmd.getUsage());
		}
	}

}
