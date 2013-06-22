package pro.jrat.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.Slave;
import pro.jrat.exceptions.CloseException;


public class Packet36Uninstall extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.closeSocket(new CloseException("Uninstalling"));
	}

	@Override
	public byte getPacketId() {
		return 36;
	}

}
