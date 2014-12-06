package se.jrat.stub.packets.incoming;

import java.io.FileInputStream;

import se.jrat.common.crypto.Crypto;
import se.jrat.stub.Connection;
import se.jrat.stub.Main;
import se.jrat.stub.packets.outgoing.Packet65ErrorLog;
import se.jrat.stub.utils.Utils;


public class Packet86ErrorLog extends AbstractIncomingPacket {

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
