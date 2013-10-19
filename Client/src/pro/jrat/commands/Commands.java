package pro.jrat.commands;

import java.util.HashMap;
import java.util.Map;

public class Commands {
	
	private static final Map<String, Class<? extends AbstractCommand>> commands = new HashMap<String, Class<? extends AbstractCommand>>();
	
	static {
		commands.put("liststats", Commandliststats.class);
		commands.put("addstats", Commandaddstats.class);
	}
	
	public static synchronized void execute(String command) {
		try {
			String[] args = command.split(" ");
			String[] argsNoCmd = new String[args.length - 1];
			
			for (int i = 1; i < args.length; i++) {
				argsNoCmd[i] = args[i - 1];
			}
			
			AbstractCommand acommand = commands.get(args[0].toLowerCase()).newInstance();
			
			if (acommand != null) {
				acommand.process(args);
			} else {
				throw new Exception("Command not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
