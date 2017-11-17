package jrat.module.system.packets;

import jrat.client.Connection;
import jrat.client.packets.outgoing.OutgoingPacket;


public class Packet53RegistryStartup implements OutgoingPacket {

	private String[] keys;

	public Packet53RegistryStartup(String[] keys) {
		this.keys = keys;
	}

	public Packet53RegistryStartup(String key) {
		this.keys = new String[] { key };
	}

	@Override
	public void write(Connection con) throws Exception {
        con.writeInt(keys.length);

		for (String key : keys) {
            con.writeLine(key);
		}
	}

	@Override
	public short getPacketId() {
		return 53;
	}

}
