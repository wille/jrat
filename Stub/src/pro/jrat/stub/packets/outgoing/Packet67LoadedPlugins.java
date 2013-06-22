package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.stub.Plugin;

import com.redpois0n.common.io.StringWriter;

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
