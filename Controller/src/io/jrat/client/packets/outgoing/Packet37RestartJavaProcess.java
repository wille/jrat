package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;
import io.jrat.client.exceptions.CloseException;

import java.io.DataOutputStream;


public class Packet37RestartJavaProcess extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.closeSocket(new CloseException("Restarting process"));
	}

	@Override
	public byte getPacketId() {
		return 37;
	}

}
