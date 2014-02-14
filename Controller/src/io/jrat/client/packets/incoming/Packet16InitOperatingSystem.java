package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.Frame;
import io.jrat.client.utils.Utils;

import java.io.DataInputStream;


public class Packet16InitOperatingSystem extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.setOperatingSystem(slave.readLine());
		Frame.mainModel.setValueAt(slave.getOperatingSystem(), Utils.getRow(3, slave.getIP()), 6);
	}

}
