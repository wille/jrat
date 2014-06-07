package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import su.jrat.client.Slave;
import su.jrat.client.ui.frames.Frame;
import su.jrat.client.utils.Utils;


public class Packet63InitRAM extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int ram = slave.readInt();
		slave.setRam(ram);

		int row = Utils.getRow(slave);

		Frame.mainModel.setValueAt(ram + " mb", row, 7);
	}

}
