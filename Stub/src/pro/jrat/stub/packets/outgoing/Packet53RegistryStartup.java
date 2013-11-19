package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.common.io.StringWriter;

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
	public byte getPacketId() {
		return 53;
	}

}
