package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.Status;
import io.jrat.client.ui.frames.Frame;
import io.jrat.client.utils.Utils;

import java.io.DataInputStream;


public class Packet13Status extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String status = slave.readLine();

		try {
			int istatus = Integer.parseInt(status);

			Frame.mainModel.setValueAt(Status.getStatusFromID(istatus), Utils.getRow(3, slave.getIP()), 2);
			slave.setStatus(istatus);
		} catch (Exception ex) {
			Frame.mainModel.setValueAt(status, Utils.getRow(3, slave.getIP()), 2);
		}
	}
}
