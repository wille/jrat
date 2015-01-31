package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet16InitOperatingSystem extends AbstractOutgoingPacket {

	private String sOs;
	private String lOs;
	private String arch;

	public Packet16InitOperatingSystem(String sOs, String lOs, String arch) {
		this.sOs = sOs;
		this.lOs = lOs;
		this.arch = arch;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(sOs);
		sw.writeLine(lOs);
		sw.writeLine(arch);
	}

	@Override
	public byte getPacketId() {
		return (byte) 16;
	}
}
