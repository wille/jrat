package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;
import io.jrat.stub.Plugin;

import java.io.DataOutputStream;


public class Packet16LoadedPlugins extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(Plugin.list.size());

		for (Plugin plugin : Plugin.list) {
			sw.writeLine(plugin.name);
		}
	}

	@Override
	public byte getPacketId() {
		return 16;
	}

}
