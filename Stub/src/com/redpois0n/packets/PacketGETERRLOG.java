package com.redpois0n.packets;

import java.io.FileInputStream;

import com.redpois0n.Connection;
import com.redpois0n.Main;
import com.redpois0n.Util;
import com.redpois0n.common.crypto.Crypto;


public class PacketGETERRLOG extends Packet {

	@Override
	public void read(String line) throws Exception {
		try {
			FileInputStream in = new FileInputStream("err.dat");
			String str = Util.readString(in);
			in.close();

			Connection.addToSendQueue(new PacketBuilder(Header.ERROR_LOG, Crypto.decrypt(str, Main.getKey())));
		} catch (Exception ex) {
			Connection.addToSendQueue(new PacketBuilder(Header.ERROR_LOG, "Could not load error log file: err.dat: " + ex.getClass().getSimpleName() + ": " + ex.getMessage()));
		}
	}

}
