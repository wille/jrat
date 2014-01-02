package pro.jrat.client.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.client.Slave;
import pro.jrat.client.Status;
import pro.jrat.client.ui.frames.Frame;
import pro.jrat.client.utils.Utils;

public class Packet13Status extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String status = slave.readLine();

		try {
			int istatus = Integer.parseInt(status);

			Frame.mainModel.setValueAt(Status.getStatusFromID(istatus), Utils.getRow(3, slave.getIP()), 2);
			slave.setStatus(istatus);
		} catch (Exception ex) {
			ex.printStackTrace();
			Frame.mainModel.setValueAt(status, Utils.getRow(3, slave.getIP()), 2);
		}
	}
}
