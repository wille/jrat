package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;
import pro.jrat.ui.frames.Frame;
import pro.jrat.utils.Util;


public class Packet14InitComputerName extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.setComputerName(slave.readLine());
		Frame.mainModel.setValueAt("Unknown@" + slave.getComputerName(), Util.getRow(3, slave.getIP()), 5);
	}
}
