package pro.jrat.client.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.client.Slave;
import pro.jrat.client.ui.frames.Frame;
import pro.jrat.client.utils.Utils;

public class Packet14InitComputerName extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.setComputerName(slave.readLine());
		Frame.mainModel.setValueAt("Unknown@" + slave.getComputerName(), Utils.getRow(3, slave.getIP()), 5);
	}
}
