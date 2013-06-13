package com.redpois0n.packets.incoming;

import com.redpois0n.RemoteShell;

public class PacketENDCMD extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (RemoteShell.p != null) {
			RemoteShell.p.destroy();
		}
	}

}
