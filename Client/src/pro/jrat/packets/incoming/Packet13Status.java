package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;
import pro.jrat.Status;
import pro.jrat.ui.frames.Frame;
import pro.jrat.utils.Util;


public class Packet13Status extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String status = slave.readLine();
		int istatus = Integer.parseInt(status);
		
		try {
			Frame.mainModel.setValueAt(Status.getStatusFromID(istatus), Util.getRow(3, slave.getIP()), 2);
			slave.setStatus(istatus);
		} catch (Exception ex) {
			ex.printStackTrace();
			Frame.mainModel.setValueAt(status, Util.getRow(3, slave.getIP()), 2);
		}				
	}
}
