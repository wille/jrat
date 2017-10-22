package jrat.client.packets.outgoing;

import jrat.common.io.StringWriter;
import jrat.client.Plugin;
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
	public short getPacketId() {
		return 16;
	}

}
