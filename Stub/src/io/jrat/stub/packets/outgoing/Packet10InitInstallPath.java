package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;
import io.jrat.stub.Main;

import java.io.DataOutputStream;
import java.io.File;


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
