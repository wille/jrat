package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;
import se.jrat.stub.Plugin;


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
