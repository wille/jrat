package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;
import se.jrat.stub.Main;


public class Packet10InitInstallPath extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		
		sw.writeLine(path);
	}

	@Override
	public byte getPacketId() {
		return (byte) 10;
	}

}
