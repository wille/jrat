package com.redpois0n.stub.packets.incoming;

import java.io.FileInputStream;

import com.redpois0n.Connection;
import com.redpois0n.Main;
import com.redpois0n.common.crypto.Crypto;
import com.redpois0n.stub.packets.outgoing.Packet65ErrorLog;
import com.redpois0n.utils.Utils;


public class PacketGETERRLOG extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		try {
			FileInputStream in = new FileInputStream("err.dat");
			String str = Utils.readString(in);
			in.close();

			Connection.addToSendQueue(new Packet65ErrorLog(Crypto.decrypt(str, Main.getKey())));
		} catch (Exception ex) {
			Connection.addToSendQueue(new Packet65ErrorLog("Could not load error log file: err.dat: " + ex.getClass().getSimpleName() + ": " + ex.getMessage()));
		}
	}

}
