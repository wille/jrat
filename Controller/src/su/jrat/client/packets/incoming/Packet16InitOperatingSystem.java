package su.jrat.client.packets.incoming;

import java.io.DataInputStream;

import su.jrat.client.Slave;
import su.jrat.client.ui.frames.Frame;
import su.jrat.client.utils.Utils;


public class Packet16InitOperatingSystem extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.setOperatingSystem(slave.readLine());
		Frame.mainModel.setValueAt(slave.getOperatingSystem(), Utils.getRow(3, slave.getIP()), 6);
	}

}